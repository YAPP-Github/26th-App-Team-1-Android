package com.ninecraft.booket.core.data.impl.mapper

import com.ninecraft.booket.core.model.LoginModel
import com.ninecraft.booket.core.model.UserProfileModel
import com.ninecraft.booket.core.network.response.LoginResponse
import com.ninecraft.booket.core.network.response.UserProfileResponse

internal fun LoginResponse.toModel(): LoginModel {
    return LoginModel(
        accessToken = accessToken,
        refreshToken = refreshToken,
    )
}

internal fun UserProfileResponse.toModel(): UserProfileModel {
    return UserProfileModel(
        id = id,
        email = email,
        nickname = nickname,
        provider = provider,
    )
}
