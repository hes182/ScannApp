package com.example.rqscanner.function

import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
class BarcodeAnalyser(val callback: (codeScan: String) -> Unit) : ImageAnalysis.Analyzer {
    override fun analyze(imageproxy: ImageProxy) {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        val scanner = BarcodeScanning.getClient(options)
        val mediaImage = imageproxy.image
        mediaImage?.let {
            val image = InputImage.fromMediaImage(mediaImage, imageproxy.imageInfo.rotationDegrees)

            scanner.process(image)
                .addOnSuccessListener {
                    if (it.size > 0) {
                        it.forEach {
                            callback(it.displayValue.toString())
                        }
                    }
                }
                .addOnFailureListener{
                    it.printStackTrace()
                }
        }
        imageproxy.close()
    }
}