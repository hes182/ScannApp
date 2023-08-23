package com.example.rqscanner.Object

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ObjectScanQR (
    var id: String = "",
    var baknName: String = "",
    var merchaneName: String = "",
    var nominal: String = "",
    var saldo: String = "",
    var tanggal: String = "",

) : Parcelable