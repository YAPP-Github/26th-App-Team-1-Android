package com.ninecraft.booket.core.data.impl.repository

import com.ninecraft.booket.core.common.utils.runSuspendCatching
import com.ninecraft.booket.core.data.api.repository.UserRepository
import com.ninecraft.booket.core.data.impl.mapper.toModel
import com.ninecraft.booket.core.network.service.AuthService
import javax.inject.Inject

internal class DefaultUserRepository @Inject constructor(
    private val authService: AuthService,
) : UserRepository {
    override suspend fun getUserProfile() = runSuspendCatching {
        authService.getUserProfile().toModel()
    }
}
