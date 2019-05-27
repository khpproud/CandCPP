package filtering

import io.reactivex.Observable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

/**
 *  debounce() - 지정된 타임 동안 입력이 없는 경우에만 마지막 입력을 배출
 */
fun main() {
    createObservable().debounce(200, TimeUnit.MILLISECONDS).subscribe { println(it) }
}

inline fun createObservable(): Observable<String> =
        Observable.create<String> {
            it.onNext("R")
            runBlocking { delay(100) }
            it.onNext("Re")
            it.onNext("Reac")
            runBlocking { delay(130) }
            it.onNext("Reactiv")
            runBlocking { delay(140) }
            it.onNext("Reactive")
            runBlocking { delay(250) }
            it.onNext("Reactive P")
            runBlocking { delay(130) }
            it.onNext("Reactive Pro")
            runBlocking { delay(100) }
            it.onNext("Reactive Progra")
            runBlocking { delay(100) }
            it.onNext("Reactive Programming")
            runBlocking { delay(300) }
            it.onNext("Reactive Programming in")
            runBlocking { delay(100) }
            it.onNext("Reactive Programming in Kotl")
            runBlocking { delay(150) }
            it.onNext("Reactive Programming in Kotlin")
            runBlocking { delay(300) }
            it.onComplete()
        }