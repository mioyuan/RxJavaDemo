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
import com.mio.rxjavademo.RxJavaCreate
import com.mio.rxjavademo.ui.theme.RxJavaDemoTheme

class CreateActivity : ComponentActivity() {
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
                        CustomButton("empty") { RxJavaCreate.empty() }
                        CustomButton("never") { RxJavaCreate.never() }
                        CustomButton("error") { RxJavaCreate.error() }
                        CustomButton("defer") { RxJavaCreate.defer() }
                        CustomButton("timer") { RxJavaCreate.timer() }
                        CustomButton("interval") { RxJavaCreate.interval() }
                        CustomButton("intervalRange") { RxJavaCreate.intervalRange() }
                        CustomButton("range") { RxJavaCreate.range() }
                    }
                }
            }
        }
    }

    companion object {
        fun start(context: Context) =
            context.startActivity(Intent(context, CreateActivity::class.java).apply { })
    }
}