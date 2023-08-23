package com.example.rqscanner.Include

import android.content.ContentValues
import android.content.Context
import com.example.rqscanner.Object.ObjectScanQR

class SQLKontrol(cntx: Context) {

    val sqlHelper = SQLHelper(cntx)

    fun savePayment(obj: ObjectScanQR) {
        val db = sqlHelper.writableDatabase
        val valuins = ContentValues()
        valuins.put("idtransaksi", obj.id)
        valuins.put("merchant", obj.merchaneName)
        valuins.put("nominal", obj.nominal)
    }
}