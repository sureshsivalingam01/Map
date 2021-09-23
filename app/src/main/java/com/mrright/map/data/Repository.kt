package com.mrright.map.data

import android.content.res.Resources
import com.google.gson.Gson
import com.mrright.map.R
import com.mrright.map.models.OfficeDetail
import com.mrright.map.models.OfficeDetails
import javax.inject.Inject

class Repository @Inject constructor(
    private val resources: Resources,
) {

    fun getOfficeList(): List<OfficeDetail> {
        val gson = Gson()
        val officeString = resources.openRawResource(R.raw.officedetails).reader().readText()
        val officeDetails = gson.fromJson(officeString, OfficeDetails::class.java)
        return officeDetails.officeDetails ?: listOf()
    }

}