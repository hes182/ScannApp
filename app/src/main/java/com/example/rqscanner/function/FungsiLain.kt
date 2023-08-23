package com.example.rqscanner.function

import java.text.DecimalFormat

object FungsiLain {

    fun setFormatText(obyek: Number?, pattern: String?): String {
        var result = ""
        val dfNom = DecimalFormat(pattern)
        val dfResult = dfNom.format(obyek)
        if (!dfResult.equals("0")) {
            result = dfResult
        }
        return result
    }

}