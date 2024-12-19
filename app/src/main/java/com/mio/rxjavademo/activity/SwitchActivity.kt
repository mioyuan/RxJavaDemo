package com.mio.rxjavademo.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mio.rxjavademo.CustomButton
import com.mio.rxjavademo.RxJavaSwitch
import com.mio.rxjavademo.ui.theme.RxJavaDemoTheme

class SwitchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RxJavaDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        CustomButton("返回上一页") { finish() }
                        CustomButton("map") { RxJavaSwitch.map() }
                        CustomButton("flatMap") { RxJavaSwitch.flatMap() }
                        CustomButton("concatMap") { RxJavaSwitch.concatMap() }
                        CustomButton("switchMap") { RxJavaSwitch.switchMap() }
                        CustomButton("cast") { RxJavaSwitch.cast() }
                        CustomButton("buffer - count") { RxJavaSwitch.bufferCount() }
                        CustomButton("buffer - time") { RxJavaSwitch.bufferTimeSpan() }
                        CustomButton("buffer - boundary") { RxJavaSwitch.bufferBoundary() }
                        CustomButton("buffer - indicator") { RxJavaSwitch.bufferIndicator() }
                        CustomButton("scan") { RxJavaSwitch.scan() }
                        CustomButton("groupBy") { RxJavaSwitch.groupBy() }
                        CustomButton("window") { RxJavaSwitch.window() }
                    }
                }
            }
        }
    }

    companion object {
        fun start(context: Context) =
            context.startActivity(Intent(context, SwitchActivity::class.java).apply { })
    }
}