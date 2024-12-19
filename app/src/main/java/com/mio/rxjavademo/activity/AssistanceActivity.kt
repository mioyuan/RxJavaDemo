package com.mio.rxjavademo.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mio.rxjavademo.CustomButton
import com.mio.rxjavademo.ListItem
import com.mio.rxjavademo.RxJavaAssistance
import com.mio.rxjavademo.ui.theme.RxJavaDemoTheme

class AssistanceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RxJavaDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val list = listOf(
                        ListItem("返回上一页") { finish() },
                        ListItem("线程调度") { RxJavaAssistance.schedule() },
                        ListItem("do操作符") { RxJavaAssistance.doOperation() },
                        ListItem("onErrorReturn") { RxJavaAssistance.onErrorReturn() },
                        ListItem("onErrorResumeNext") { RxJavaAssistance.onErrorResumeNext() },
                        ListItem("onExceptionResumeNext") { RxJavaAssistance.onExceptionResumeNext() },
                        ListItem("retryUntil") { RxJavaAssistance.retryUntil() },
                        ListItem("retryWhen") { RxJavaAssistance.retryWhen() },
                        ListItem("delay") { RxJavaAssistance.delay() },
                        ListItem("delayByObservable") { RxJavaAssistance.delayByObservable() },
                        ListItem("delaySubscription") { RxJavaAssistance.delaySubscription() },
                        ListItem("timeout1") { RxJavaAssistance.timeout1() },
                        ListItem("timeout2") { RxJavaAssistance.timeout2() },
                        ListItem("timeInterval") { RxJavaAssistance.timeInterval() },
                        ListItem("timestamp") { RxJavaAssistance.timestamp() },
                        ListItem("repeatWhen") { RxJavaAssistance.repeatWhen() },
                        ListItem("serialize") { RxJavaAssistance.serialize() },
                        ListItem("toList") { RxJavaAssistance.toList() },
                        ListItem("using") { RxJavaAssistance.using() },
                        ListItem("materialize") { RxJavaAssistance.materialize() },
                    )
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(list) {
                            CustomButton(title = it.title) {
                                it.click.invoke()
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun start(context: Context) =
            context.startActivity(Intent(context, AssistanceActivity::class.java).apply { })
    }
}