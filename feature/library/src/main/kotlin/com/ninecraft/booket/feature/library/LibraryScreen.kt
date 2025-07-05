package com.ninecraft.booket.feature.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ninecraft.booket.core.designsystem.DevicePreview
import com.ninecraft.booket.core.designsystem.component.BooketButton
import com.ninecraft.booket.core.designsystem.theme.BooketTheme
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.parcelize.Parcelize

@Parcelize
data object LibraryScreen : Screen {
    data class State(
        val isLoading: Boolean = false,
        val nickname: String = "",
        val email: String = "",
        val sideEffect: SideEffect? = null,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface SideEffect {
        data class ShowToast(val message: String) : SideEffect
    }

    sealed interface Event : CircuitUiEvent {
        data object InitSideEffect : Event
        data object OnLogoutButtonClick : Event
    }
}

@CircuitInject(LibraryScreen::class, ActivityRetainedComponent::class)
@Composable
internal fun Library(
    state: LibraryScreen.State,
    modifier: Modifier = Modifier,
) {
    HandleLibrarySideEffects(
        state = state,
        eventSink = state.eventSink,
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LibraryContent(
            state = state,
            modifier = modifier,
        )
    }
}

@Composable
internal fun LibraryContent(
    state: LibraryScreen.State,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "내 서재")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = state.nickname)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = state.email)
            }
            BooketButton(
                onClick = {
                    state.eventSink(LibraryScreen.Event.OnLogoutButtonClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, bottom = 32.dp)
                    .height(56.dp)
                    .align(Alignment.BottomCenter),
                text = {
                    Text(
                        text = stringResource(id = R.string.logout),
                        fontSize = 18.sp,
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            lineHeight = 25.sp,
                        ),
                    )
                },
            )

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@DevicePreview
@Composable
private fun LibraryPreview() {
    BooketTheme {
        Library(
            state = LibraryScreen.State(
                nickname = "홍길동",
                email = "test@test.com",
                eventSink = {},
            ),
        )
    }
}
