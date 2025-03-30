package com.example.resumegenerator.home.presentation.util


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.graphics.createBitmap
import java.io.File
import java.io.FileOutputStream

object FileShareUtil {
    fun shareTemplate(context: Context, imageResId: Int, templateName: String) {
        try {
            val drawable: Drawable = context.resources.getDrawable(imageResId, null)

            // Convert drawable to Bitmap safely
            val bitmap = when (drawable) {
                is BitmapDrawable -> drawable.bitmap
                else -> {
                    val bmp = createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
                    val canvas = Canvas(bmp)
                    drawable.setBounds(0, 0, canvas.width, canvas.height)
                    drawable.draw(canvas)
                    bmp
                }
            }

            // Determine format and extension
            val fileFormat = Bitmap.CompressFormat.PNG
            val fileExtension = "png"

            val file = File(context.cacheDir, "$templateName.$fileExtension")
            val outputStream = FileOutputStream(file)
            bitmap.compress(fileFormat, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            // Get URI using FileProvider
            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )

            // Determine MIME type
            val mimeType = "image/png"

            // Create share intent
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, "Check out this resume template: $templateName")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            // Start share activity
            context.startActivity(Intent.createChooser(shareIntent, "Share Template"))

        } catch (e: Exception) {
            Log.e("ShareTemplate", "Error sharing template: ${e.message}")
        }
    }
}
