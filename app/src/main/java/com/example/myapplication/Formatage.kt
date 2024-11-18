package com.example.myapplication

import java.text.DecimalFormat

object Formatage {

    fun formatResultat(value: Double): String {
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(value).replace(",",".")
    }
}