package com.mio.rxjavademo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Author: Mioyuan
 * Date: 2024/10/25 10:23
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * Mioyuan 2024/10/25 1.0 首次创建
 */
@Composable
fun CustomButton(title: String, click: () -> Unit) {
    Column {
        Button(
            modifier = Modifier
                .height(50.dp)
                .widthIn(min = 100.dp),
            onClick = { click.invoke() }) {
            Text(text = title)
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}