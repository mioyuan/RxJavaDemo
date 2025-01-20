package com.mio.rxjavademo

import io.reactivex.Observable

/**
 * Author: Mioyuan
 * Date: 2024/12/26 16:32
 * Description: 过滤操作符
 * History:
 * <author> <time> <version> <desc>
 * Mioyuan 2024/12/26 1.0 首次创建
 */
object RxJavaFilter {
    private val TAG = "RXJava Test Filter"

    fun filter() {
        Observable.just(1, 2, 3, 4, 5)
            .filter {
                // 筛选出2的倍数
                it % 2 == 0
            }
            .subscribe(getObserver())
    }

    fun ofType() {
        Observable.just(1, "Hello", 3, "World", 5)
            // 筛选出字符串数据
            .ofType(String::class.java)
            .subscribe(getObserver())
    }

    fun skip() {
        Observable.just(1, 2, 3, 4, 5)
            // 跳过前两项
            .skip(2)
            // 跳过后两项
            .skipLast(2)
            .subscribe(getObserver())
    }

    /**
     * 过滤重复数据
     */
    fun distinct() {
        Observable.just(1, 2, 1, 3, 3, 4)
            .distinct {
                // 为每一项数据生成一个key，比较key是否相同(这里对数据取余，余数相同则会被过滤)
                it % 2
            }
            .subscribe(getObserver())
    }

    /**
     * 过滤连续重复的事件
     */
    fun distinctUntilChanged() {
        Observable.just(1, 1, 2, 1, 3, 3, 4)
            .distinctUntilChanged()
            .subscribe(getObserver())
    }

    /**
     * 接收指定数量的数据
     */
    fun take() {
        Observable.just(1, 2, 3, 4, 5, 6)
            .take(2)
//            .takeLast(2)
            .subscribe(getObserver())
    }
}