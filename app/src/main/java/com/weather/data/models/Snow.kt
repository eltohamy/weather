package com.weather.data.models

import com.google.gson.annotations.SerializedName

data class Snow(
    @SerializedName("3h") var h: Double
)