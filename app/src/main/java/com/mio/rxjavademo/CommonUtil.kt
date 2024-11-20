package com.mio.rxjavademo

import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Author: Mioyuan
 * Date: 2024/10/25 10:45
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * Mioyuan 2024/10/25 1.0 首次创建
 */
fun <T> getObserver(): Observer<T> {
    val TAG = "RXJava Test"

    return object : Observer<T> {
        override fun onSubscribe(p0: Disposable) {
            Log.d(TAG, "开始采用subscribe连接")
        }


        override fun onError(p0: Throwable) {
            Log.d(TAG, "对Error事件作出响应:$p0")
        }

        override fun onComplete() {
            Log.d(TAG, "对Complete事件作出响应")
        }

        override fun onNext(p0: T) {
            Log.d(TAG, "接收到了事件:$p0")
        }

    }
}

data class ListItem(
    val title: String,
    val click: () -> Unit
)