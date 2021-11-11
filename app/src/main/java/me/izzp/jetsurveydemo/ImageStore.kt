package me.izzp.jetsurveydemo

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

class ImageStore(private val context: Context) {

    val needPermission by lazy { Build.VERSION.SDK_INT <= Build.VERSION_CODES.P }

    val uri: Uri?
        get() {
            val contentResolver = context.contentResolver
            val id = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.ImageColumns._ID),
                "${MediaStore.Images.ImageColumns.DISPLAY_NAME} = ?",
                arrayOf("jetsurvey-selfie"),
                null,
            )?.use { cursor ->
                if (cursor.moveToNext()) {
                    cursor.getInt(0)
                } else {
                    null
                }
            }
            return if (id != null) {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon()
                    .appendPath(id.toString()).build()
            } else {
                contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    ContentValues().apply {
                        put(MediaStore.Images.Media.DISPLAY_NAME, "jetsurvey-selfie")
                        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    }
                )
            }
        }

}