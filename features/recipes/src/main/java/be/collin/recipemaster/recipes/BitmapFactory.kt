package be.collin.recipemaster.recipes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

object BitmapFactory {

    fun fromBase64Bytes(content: ByteArray): Bitmap {
        val decodedBase64String = Base64.decode(content, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(
            decodedBase64String,
            0,
            decodedBase64String.size
        )
    }
}