

package com.example.rqscanner.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.rqscanner.Object.ObjectScanQR
import com.example.rqscanner.function.FungsiLain

class PaymentActivity : ComponentActivity() {
    private var bund = Bundle()
    var obj = ObjectScanQR()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (intent.extras != null) {
                bund = intent.extras!!
                obj = bund.getParcelable("obj")!!
            }
            AppBarLayout(obj)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarLayout(obj:ObjectScanQR) {
    Scaffold(Modifier.fillMaxSize()) {
        it
        Column {
            paymentTrans(obj)
            ButtonTransaksi(obj)
        }
    }
}

@Composable
fun paymentTrans(obj: ObjectScanQR) {
    val paddingModif = Modifier.padding(8.dp)
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = paddingModif
            .fillMaxWidth()) {
        Column(modifier = paddingModif) {
            Row {
                Text(text = "ID Transaksi",
                    modifier = Modifier
                        .padding(end = 8.dp, start = 4.dp, top = 4.dp, bottom = 4.dp)
                        .weight(1f))
                Text(text = obj.id,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(end = 8.dp, start = 4.dp, top = 4.dp, bottom = 4.dp)
                        .weight(1f),
                    textAlign = TextAlign.End,
                    )
            }
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Text(text = "Nama Merchant",
                    modifier = Modifier
                        .padding(end = 8.dp, start = 4.dp, top = 4.dp, bottom = 4.dp)
                        .weight(1f))
                Text(text = obj.merchaneName,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(end = 8.dp, start = 4.dp, top = 4.dp, bottom = 4.dp)
                        .weight(1f),
                    textAlign = TextAlign.End,
                )
            }
            Row(modifier = Modifier.padding(top = 8.dp)) {
                Text(text = "Nominal Pembayaran",
                    modifier = Modifier
                        .padding(end = 8.dp, start = 4.dp, top = 4.dp, bottom = 4.dp)
                        .weight(1f))
                Text(text = "Rp " + FungsiLain.setFormatText(obj.nominal.toInt(), "#,###"),
                    color = Color.Black,
                    modifier = Modifier
                        .padding(end = 8.dp, start = 4.dp, top = 4.dp, bottom = 4.dp)
                        .weight(1f),
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}

@Composable
fun ButtonTransaksi(obj: ObjectScanQR) {
    var showDialog by remember{ mutableStateOf(false) }
    Button(
        modifier = Modifier
            .padding(top = 20.dp, start = 75.dp, end = 75.dp)
            .fillMaxWidth(),
        onClick = { showDialog = true},
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.primary
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Cyan
        )
    ) {
        Text(text = "Bayar", color = Color.White, fontWeight = FontWeight.Bold)
    }

    val activity = LocalContext.current as Activity

    val nominal = obj.nominal.toInt()
    val saldoAwal = 1000000
    val sisaSaldo = saldoAwal - nominal

    if (showDialog) {
        AlertDialog(onDismissRequest = {},
            title = { Text(text = "Apakah Anda Akan Melakukan Transaksi")},
            text = { Text("Dengan Total Transaksi Rp ${FungsiLain.setFormatText(nominal, "#,###")}," +
                    " Sisa Saldo Anda Sebesar Rp ${FungsiLain.setFormatText(sisaSaldo, "#,###")}")},
            confirmButton = {
                TextButton(onClick = {
                    activity.finish()
                }) {
                    Text("Bayar".uppercase())
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Batal".uppercase())
                }
            })
    }
}

