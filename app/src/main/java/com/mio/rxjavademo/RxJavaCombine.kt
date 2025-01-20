package com.mio.rxjavademo

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiConsumer
import io.reactivex.functions.BiFunction
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

/**
 * Author: Mioyuan
 * Date: 2024/12/19 9:49
 * Description: 合并操作符
 * History:
 * <author> <time> <version> <desc>
 * Mioyuan 2024/11/7 1.0 首次创建
 */
object RxJavaCombine {
    private val TAG = "RXJava Test Combine"

    fun concatDelayError() {
        Observable.concatDelayError(
            arrayListOf(
                Observable.intervalRange(1, 2, 1, 1, TimeUnit.SECONDS),
                Observable.error(Throwable("发生错误啦")),
                Observable.just(3, 4),
                Observable.just(5),
            )
        ).subscribe(getObserver())
    }

    fun merge() {
        Observable.merge(
            // 从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
            Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS),
            Observable.intervalRange(2, 3, 0, 1, TimeUnit.SECONDS),
            Observable.just("666")
        ).subscribe(getObserver())
    }

    fun zip() {
        Observable.zip(
            Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS),
            Observable.intervalRange(2, 4, 0, 1, TimeUnit.SECONDS)
        ) { ob1, ob2 ->
            "$ob1 $ob2"
        }.subscribe(getObserver())
    }

    fun combineLatest() {
        Observable.combineLatest(
            // 延迟3s
            Observable.intervalRange(0, 3, 3, 1, TimeUnit.SECONDS),
            // 延迟1s
            Observable.intervalRange(0, 1, 1, 1, TimeUnit.SECONDS),
            Observable.just("666")
        ) { interval1, interval2, just ->
            "$interval1 $interval2 $just"
        }.subscribe(getObserver())
    }

    @SuppressLint("CheckResult")
    fun reduce() {
        Observable.just(1, 2, 3, 4)
            .reduce(object : BiFunction<Int, Int, Int> {
                override fun apply(p0: Int, p1: Int): Int {
                    Log.d(TAG, "本次合并结果：$p0 + $p1")
                    return p0 + p1
                }
            }).subscribe({
                Log.d(TAG, "合并结果：$it")
            }, {
                Log.d(TAG, "${it.message}")
            }, {
                Log.d(TAG, "对Complete事件作出响应")
            })
    }

    fun collect() {
        Observable.just(1, 2, 3, 4)
            .collect(
                object : Callable<MutableList<Int>> {
                    override fun call(): MutableList<Int> {
                        return mutableListOf()
                    }
                },
                object : BiConsumer<MutableList<Int>, Int> {
                    override fun accept(t: MutableList<Int>, u: Int) {
                        t.add(u)
                    }
                }
            ).subscribe(object : SingleObserver<MutableList<Int>> {
                override fun onSubscribe(p0: Disposable) {
                    Log.d(TAG, "开始采用subscribe连接")
                }

                override fun onError(p0: Throwable) {
                    Log.d(TAG, "对Error事件作出响应:$p0")
                }

                override fun onSuccess(p0: MutableList<Int>) {
                    Log.d(TAG, "接收到了事件:$p0")
                }
            })
    }

    fun startWithArray() {
        Observable.just(1, 2, 3, 4)
            .startWithArray(-1, 0)
            .subscribe(getObserver())
    }
}