package com.example.rqscanner.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.camera.core.CameraProvider
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.rqscanner.Object.ObjectScanQR
import com.example.rqscanner.function.BarcodeAnalyser
import java.util.concurrent.Executors

@androidx.camera.core.ExperimentalGetImage
@Composable
fun PreviewScan() {
    AndroidView(factory = {context ->
        val cameraExecutor = Executors.newSingleThreadExecutor()
        val previewView = PreviewView(context).also {
            it.scaleType = PreviewView.ScaleType.FILL_CENTER
        }
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            val imageCapture = ImageCapture.Builder().build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarcodeAnalyser{
                        var  objList = ArrayList<ObjectScanQR>()
                        val nameSplited = it.split(".")
                        var objData = ObjectScanQR()
                        objData.id = nameSplited[1]
                        objData.baknName = nameSplited[0]
                        objData.merchaneName = nameSplited[2]
                        objData.nominal = nameSplited[3]
                        objList.add(objData)

                        val inten = Intent(context, PaymentActivity::class.java)
                        val bund = Bundle()
                        bund.putParcelable("obj", objData)
                        inten.putExtras(bund)
                        context.startActivity(inten)
                    })
                }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    context as ComponentActivity, cameraSelector, preview, imageCapture, imageAnalyzer
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }, ContextCompat.getMainExecutor(context))
        previewView
    },
        modifier = Modifier.size(width = 250.dp, height = 250.dp))
}