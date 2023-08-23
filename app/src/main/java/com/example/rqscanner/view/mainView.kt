package com.example.rqscanner.view

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rqscanner.Include.SQLHelper
import com.example.rqscanner.Object.ObjectScanQR
import com.example.rqscanner.R
import com.example.rqscanner.function.FungsiLain
import com.example.rqscanner.function.PermissionCamera
import com.example.rqscanner.function.SinglePermission

sealed class BootomNavItem(var title: String, var Icon: Int, var screen_route: String) {
    object Home: BootomNavItem("Home", R.drawable.baseline_home_24, "home")
    object QRIS: BootomNavItem("QRIS", R.drawable.baseline_add_a_photo_24, "qris")
    object Riwayat: BootomNavItem("Riwayat", R.drawable.baseline_edit_note_24, "riwayat")
}

@Composable
fun HomeLayout(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        CardCust(context)
    }
}

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@Composable
fun QrisLayout() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
            .wrapContentSize(Alignment.Center)
    ) {
        PreviewScan()
        Text(
            text = "Scan QR Code",
            modifier = Modifier.padding(top = 60.dp)
        )
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, context: Context) {
    NavHost(navController, startDestination = BootomNavItem.Home.screen_route) {
        composable(BootomNavItem.Home.screen_route) {
            HomeLayout(context)
        }
        composable(BootomNavItem.QRIS.screen_route) {
            if (PermissionCamera()) {
                SinglePermission(QrisLayout(), android.Manifest.permission.CAMERA)
            }
            QrisLayout()
        }
        composable(BootomNavItem.Riwayat.screen_route) {
            RiwayatTransaksiLayout()
        }
    }
}

@Composable
fun BottomNavigationLayout(navController: NavController) {
    val items = listOf(
        BootomNavItem.Home,
        BootomNavItem.QRIS,
        BootomNavItem.Riwayat,
    )
    BottomNavigation(backgroundColor = Color.Cyan,
        contentColor = Color.Black) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.Icon), contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 9.sp)
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {
                        navController.graph.startDestinationRoute?.let { routescreen ->
                            popUpTo(routescreen) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun CardCust(context: Context) {
    val dbHelper = SQLHelper(context)
    val paddingModif = Modifier.padding(10.dp)
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = paddingModif
            .fillMaxWidth()) {
        Column() {
            Text(text = "Himawan Edi S",
                modifier = paddingModif)
            Text(text ="Saldo Rp 100.000",
                color = Color.Black,
                modifier = paddingModif)

        }
    }
}

@Composable
fun RiwayatTransaksiLayout() {
    Column (modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Transparent)){
        Text("Riwayat Transaksi", fontWeight = FontWeight.Bold, fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
        ListViewRiwTrans()
    }
}

@Composable
fun ListViewRiwTrans() {
    lateinit var listData: List<ObjectScanQR>
    listData = ArrayList<ObjectScanQR>()

    listData = listData + ObjectScanQR("ID12345678", "", "MERCHANT MOCK TEST", "50000", "950000", "13-08-2023")

    LazyColumn(modifier = Modifier.padding(top = 10.dp, start = 4.dp, end = 4.dp)) {
        itemsIndexed(listData) {index, item ->
            val paddingModif = Modifier.padding(8.dp)
            Card(elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                modifier = paddingModif
                    .fillMaxWidth()) {
                Column(modifier = paddingModif) {
                    Row() {
                        Text(text = "",
                            modifier = Modifier
                                .padding(end = 8.dp, start = 4.dp, top = 4.dp, bottom = 4.dp)
                                .weight(1f))
                        Text(text = listData[index].tanggal,
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
                        Text(text = listData[index].merchaneName,
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
                        Text(text = "Rp " + FungsiLain.setFormatText(listData[index].nominal.toInt(), "#,###"),
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
    }
}


