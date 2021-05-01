package io.github.takusan23.erogamescapedroid.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


/**
 * 二段のText。上の段のほうが文字が大きい
 * */
@Composable
fun RankText(modifier: Modifier, title: String, value: String) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center,
        )
        Text(text = title)
    }
}
