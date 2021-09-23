package com.mrright.map.models


import com.google.gson.annotations.SerializedName

data class OfficeDetails(
    @SerializedName("OfficeDetails")
    val officeDetails: List<OfficeDetail>? = null
)