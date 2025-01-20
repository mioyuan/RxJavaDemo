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
import com.mio.rxjavademo.RxJavaFilter
import com.mio.rxjavademo.ui.theme.RxJavaDemoTheme

class FilterActivity : ComponentActivity() {
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
                        CustomButton("filter") { RxJavaFilter.filter() }
                        CustomButton("ofType") { RxJavaFilter.ofType() }
                        CustomButton("skip") { RxJavaFilter.skip() }
                        CustomButton("distinct") { RxJavaFilter.distinct() }
                        CustomButton("distinctUntilChanged") { RxJavaFilter.distinctUntilChanged() }
                        CustomButton("take") { RxJavaFilter.take() }
                    }
                }
            }
        }
    }

    companion object {
        fun start(context: Context) =
            context.startActivity(Intent(context, FilterActivity::class.java).apply { })
    }
}