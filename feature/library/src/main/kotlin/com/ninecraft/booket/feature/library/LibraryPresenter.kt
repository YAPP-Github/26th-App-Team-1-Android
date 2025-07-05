package com.ninecraft.booket.feature.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.ninecraft.booket.core.common.utils.handleException
import com.ninecraft.booket.core.data.api.repository.AuthRepository
import com.ninecraft.booket.core.data.api.repository.UserRepository
import com.ninecraft.booket.feature.login.LoginScreen
import com.orhanobut.logger.Logger
import com.skydoves.compose.effects.RememberedEffect
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.launch

class LibraryPresenter @AssistedInject constructor(
    @Assisted private val navigator: Navigator,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
) : Presenter<LibraryScreen.State> {

    @Composable
    override fun present(): LibraryScreen.State {
        val scope = rememberCoroutineScope()
        var isLoading by rememberRetained { mutableStateOf(false) }
        var sideEffect by rememberRetained { mutableStateOf<LibraryScreen.SideEffect?>(null) }
        var nickname by rememberRetained { mutableStateOf("") }
        var email by rememberRetained { mutableStateOf("") }

        fun getUserProfile() {
            scope.launch {
                try {
                    isLoading = true
                    userRepository.getUserProfile()
                        .onSuccess { user ->
                            nickname = user.nickname
                            email = user.email
                        }
                        .onFailure { exception ->
                            val handleErrorMessage = { message: String ->
                                Logger.e(message)
                                sideEffect = LibraryScreen.SideEffect.ShowToast(message)
                            }

                            handleException(
                                exception = exception,
                                onServerError = handleErrorMessage,
                                onNetworkError = handleErrorMessage,
                                onLoginRequired = {
                                    navigator.resetRoot(LoginScreen)
                                },
                            )
                        }
                } finally {
                    isLoading = false
                }
            }
        }

        RememberedEffect(Unit) {
            getUserProfile()
        }

        fun handleEvent(event: LibraryScreen.Event) {
            when (event) {
                is LibraryScreen.Event.InitSideEffect -> {
                    sideEffect = null
                }

                is LibraryScreen.Event.OnLogoutButtonClick -> {
                    scope.launch {
                        try {
                            isLoading = true
                            authRepository.logout()
                                .onSuccess {
                                    authRepository.clearTokens()
                                    navigator.resetRoot(LoginScreen)
                                }
                                .onFailure { exception ->
                                    val handleErrorMessage = { message: String ->
                                        Logger.e(message)
                                        sideEffect = LibraryScreen.SideEffect.ShowToast(message)
                                    }

                                    handleException(
                                        exception = exception,
                                        onServerError = handleErrorMessage,
                                        onNetworkError = handleErrorMessage,
                                        onLoginRequired = {
                                            navigator.resetRoot(LoginScreen)
                                        },
                                    )
                                }
                        } finally {
                            isLoading = false
                        }
                    }
                }
            }
        }

        return LibraryScreen.State(
            isLoading = isLoading,
            nickname = nickname,
            email = email,
            sideEffect = sideEffect,
            eventSink = ::handleEvent,
        )
    }

    @CircuitInject(LibraryScreen::class, ActivityRetainedComponent::class)
    @AssistedFactory
    fun interface Factory {
        fun create(navigator: Navigator): LibraryPresenter
    }
}
