package com.ninecraft.booket.core.data.api.repository

import com.ninecraft.booket.core.model.UserProfileModel

interface UserRepository {
    suspend fun getUserProfile(): Result<UserProfileModel>
}
