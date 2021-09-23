package com.mrright.map.models


import com.google.gson.annotations.SerializedName

data class OfficeDetail(
    @SerializedName("office_name")
    val officeName: String? = null,
    @SerializedName("office_address_1")
    val officeAddress1: String? = null,
    @SerializedName("office_address_2")
    val officeAddress2: String? = null,
    @SerializedName("office_phone")
    val officePhone: String? = null,
    @SerializedName("office_fax")
    val officeFax: String? = null,
    @SerializedName("office_email")
    val officeEmail: String? = null,
    @SerializedName("office_website")
    val officeWebsite: String? = null,
    @SerializedName("office_facebook")
    val officeFacebook: String? = null,
    @SerializedName("Latitute")
    val latitute: String? = null,
    @SerializedName("Longitute")
    val longitute: String? = null,
    var isExpanded : Boolean = false,
)