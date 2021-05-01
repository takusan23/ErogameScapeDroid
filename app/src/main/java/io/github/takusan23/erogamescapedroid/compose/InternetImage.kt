package io.github.takusan23.erogamescapedroid.compose

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

/**
 * Glideを使ってComposeのBitmapを取得する関数
 * @param url URL
 * */
@Composable
fun getInternetImage(url: String): ImageBitmap? {
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    Glide.with(LocalContext.current).asBitmap().load(url).into(object : CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            bitmap.value = resource
        }

        override fun onLoadCleared(placeholder: Drawable?) {

        }
    })
    return bitmap.value?.asImageBitmap()
}