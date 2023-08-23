package com.example.rqscanner.Include

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.rqscanner.Object.ObjectScanQR
import java.sql.SQLException

class SQLHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(SQL_CREATE_SCAN)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        try {
            db?.execSQL(SQL_CREATE_PAYMEN)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(SQL_DELETE_SCAN)
        db?.execSQL(SQL_DELETE_PAYMENT)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    companion object {
        val DATABASE_NAME = "SacnQR.db"
        val DATABASE_VERSION = 1

        val TABLE_SCAN = "payment"
        val COL_IDTRANS = "idtransaksi"
        val COL_MERCHANT = "merchant"
        val COL_NOMINAL = "nominal"

        private val SQL_CREATE_SCAN =  "CREATE TABLE " + TABLE_SCAN + " (" +
                COL_IDTRANS + " TEXT PRIMARY KEY," +
                COL_MERCHANT + " TEXT," +
                COL_NOMINAL + " TEXT)"

        private val SQL_DELETE_SCAN = "DROP TABLE IF EXISTS " + TABLE_SCAN

        val TABLE_PAYMENT = "transaksi"
        val COL_IDTRANS_PAY = "idtransaksi_paymen"
        val COL_NOMINAL_PAY = "nominam_paymen"
        val COL_SALDO = "saldo_akhir"

        private val SQL_CREATE_PAYMEN =  "CREATE TABLE " + TABLE_PAYMENT + " (" +
                COL_IDTRANS_PAY + " TEXT PRIMARY KEY," +
                COL_NOMINAL_PAY + " TEXT," +
                COL_SALDO + " TEXT)"

        private val SQL_DELETE_PAYMENT = "DROP TABLE IF EXISTS " + TABLE_PAYMENT
    }

    fun savePayment(obj: ObjectScanQR): Boolean {
        val db = writableDatabase
        val valuins = ContentValues()
        valuins.put(COL_IDTRANS, obj.id)
        valuins.put(COL_MERCHANT, obj.merchaneName)
        valuins.put(COL_NOMINAL, obj.nominal)
        db.insert(TABLE_SCAN, null, valuins)
        return true
    }

    @SuppressLint("Range")
    fun dataPayment() : ObjectScanQR{
        val obj = ObjectScanQR()
        val db = readableDatabase
        val cursor = db.rawQuery("select * from " + TABLE_SCAN, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            obj.id = cursor.getString(cursor.getColumnIndex(COL_IDTRANS))
            obj.merchaneName = cursor.getString(cursor.getColumnIndex(COL_MERCHANT))
            obj.nominal = cursor.getString(cursor.getColumnIndex(COL_NOMINAL))
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return obj
    }

    fun checkPayment() : Boolean {
        val db = readableDatabase
        val Query = "Select * from " + TABLE_SCAN
        val cursor = db.rawQuery(Query, null)
        if (cursor.count <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        db.close()
        return true
    }

    fun saveTransaksi(obj: ObjectScanQR): Boolean {
        val db = writableDatabase
        val valuins = ContentValues()
        valuins.put(COL_IDTRANS_PAY, obj.id)
        valuins.put(COL_NOMINAL_PAY, obj.nominal)
        valuins.put(COL_SALDO, obj.saldo)
        db.insert(TABLE_PAYMENT, null, valuins)
        return true
    }

    @SuppressLint("Range")
    fun dataTransaksi() : ObjectScanQR{
        val obj = ObjectScanQR()
        val db = readableDatabase
        val cursor = db.rawQuery("select * from " + TABLE_PAYMENT, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            obj.id = cursor.getString(cursor.getColumnIndex(COL_IDTRANS_PAY))
            obj.nominal = cursor.getString(cursor.getColumnIndex(COL_NOMINAL_PAY))
            obj.saldo = cursor.getString(cursor.getColumnIndex(COL_SALDO))
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return obj
    }

    fun checkTransaksi() : Boolean {
        val db = readableDatabase
        val Query = "Select * from " + TABLE_PAYMENT
        val cursor = db.rawQuery(Query, null)
        if (cursor.count <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        db.close()
        return true
    }
}