package com.ninecraft.booket.core.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    @SerialName("id")
    val id: String,
    @SerialName("email")
    val email: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("provider")
    val provider: String,
)
