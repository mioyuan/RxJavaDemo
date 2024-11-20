package com.mio.rxjavademo

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Author: Mioyuan
 * Date: 2024/10/25 10:15
 * Description: 转换操作符
 * History:
 * <author> <time> <version> <desc>
 * Mioyuan 2024/10/25 1.0 首次创建
 */
object RxJavaSwitch {
    private val TAG = "RXJava Test Switch"

    fun map() {
        Observable.just<Int>(1, 2).map<String>(object : Function<Int, String> {
            override fun apply(p0: Int): String {
                return "转换事件$p0"
            }
        }).subscribe(getObserver<String>())
    }

    fun flatMap() {
        Observable.just(1, 2, 3).flatMap(object : Function<Int, Observable<String>> {
            override fun apply(p0: Int): Observable<String> {
                val list = mutableListOf<String>()
                for (i in 0..2) {
                    list.add("flatMap拆分事件$p0=>$i")
                }
                // 为验证无序性，特别指定了IO线程，异步执行
                return Observable.fromIterable(list).subscribeOn(Schedulers.io())
            }
        }).subscribe(getObserver<String>())
        // 等待异步结果返回
        Thread.sleep(1000)
    }

    fun concatMap() {
        Observable.just(1, 2, 3).concatMap(object : Function<Int, Observable<String>> {
            override fun apply(p0: Int): Observable<String> {
                val list = mutableListOf<String>()
                for (i in 0..2) {
                    list.add("concatMap拆分事件$p0=>$i")
                }
                // 为验证有序性，特别指定了IO线程，异步执行
                return Observable.fromIterable(list).subscribeOn(Schedulers.io())
            }
        }).subscribe(getObserver<String>())
        // 等待异步结果返回
        Thread.sleep(1000)

    }

    fun switchMap() {
//        Observable.just(1, 2, 3).flatMap(object : Function<Int, Observable<String>> {
//            override fun apply(p0: Int): Observable<String> {
//                return Observable.interval(1, TimeUnit.SECONDS).map {
//                    "第${it}秒flatMap将${p0}转换为字符串"
//                }
//            }
//        }).subscribe(getObserver<String>())
        Observable.just(1, 2, 3).switchMap(object : Function<Int, Observable<String>> {
            override fun apply(p0: Int): Observable<String> {
                return Observable.interval(1, TimeUnit.SECONDS).map {
                    "第${it}秒switchMap将${p0}转换为字符串"
                }
            }
        }).subscribe(getObserver<String>())
    }

    fun cast() {
        Observable.just(1, 2, "Hello").cast(Number::class.java).subscribe(getObserver<Number>())
    }

    fun bufferCount() {
        Observable.just(1, 2, 3, 4, 5).buffer(3, 2)
            .subscribe(getObserver<MutableList<Int>>())
    }

    fun bufferTimeSpan() {
        Observable.interval(2, TimeUnit.SECONDS).buffer(5, TimeUnit.SECONDS)
            .subscribe(getObserver<MutableList<Long>>())
    }

    fun bufferIndicator() {
        Observable.intervalRange(1, 10, 0, 1, TimeUnit.SECONDS)
            .doOnNext {
                Log.d(TAG, "初始Observable发射事件$it")
            }
            .buffer(Observable.intervalRange(1, 5, 2, 3, TimeUnit.SECONDS).doOnNext {
                Log.d(TAG, "新建缓冲区$it")
            },
                object : Function<Long, Observable<Long>> {
                    override fun apply(p0: Long): Observable<Long> {
                        return Observable.timer(2, TimeUnit.SECONDS).doOnNext {
                            Log.d(TAG, "关闭缓冲区$p0")
                        }
                    }
                }).subscribe(getObserver<MutableList<Long>>())
    }

    fun bufferBoundary() {
        Observable.interval(1, TimeUnit.SECONDS)
            .doOnNext {
                Log.d(TAG, "初始Observable发射事件$it")
            }
            .buffer(Observable.interval(2500, TimeUnit.MILLISECONDS).doOnNext {
                Log.d(TAG, "缓冲区$it")
            })
            .subscribe(getObserver<MutableList<Long>>())
    }

    fun scan() {
        Observable.just(1, 2, 3, 4).scan(5, object : BiFunction<Int, Int, Int> {
            override fun apply(p0: Int, p1: Int): Int {
                return p0 + p1
            }
        }).subscribe(getObserver<Int>())
    }

    @SuppressLint("CheckResult")
    fun groupBy() {
        Observable.just(1, 2, 3, 4, 5)
            .groupBy(
                object : Function<Int, String> {
                    override fun apply(p0: Int): String {
                        return if (p0 % 2 == 0) "Even" else "Odd"
                    }
                },
                object : Function<Int, String> {
                    override fun apply(p0: Int): String {
                        return p0.toString()
                    }
                }
            )
            .subscribe { groupedObservable ->
                Log.d(TAG, "groupedObservable 接受事件${groupedObservable.key}")
                groupedObservable.subscribe {
                    Log.d(TAG, "Group 接受事件：key-${groupedObservable.key} value-$it")
                }
            }
    }

    @SuppressLint("CheckResult")
    fun window() {
        Observable.just(1, 2, 3, 4, 5)
            .window(3)
            .subscribe { observable ->
                Log.d(TAG, "window拆分了子Observable：$observable")
                observable.subscribe {
                    Log.d(TAG, "子Observable$observable 接受事件：$it")
                }
            }
    }
}