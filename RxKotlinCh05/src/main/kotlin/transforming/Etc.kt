package transforming

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable

/**
 * 기타 변환 연산자
 */
fun main() {
    // defaultIfEmpty() - empty 이면 default 값 배출
    Observable.range(1, 10).filter { it > 15 }.defaultIfEmpty(20).subscribe { println(it) }

    // switchIfEmpty() - empty 이면 대체 생산자의 값 배출
    Observable.range(1, 10).filter { it > 15 }
        .switchIfEmpty(Observable.range(16, 5))
        .subscribe { println("Received $it") }

    // startWith() - 기존 아이템의 맨 앞에 추가
    Observable.range(0, 5)
        .startWith(-1).subscribe { println("Received $it") }

    listOf("C", "C++", "Java", "Kotlin", "Scala", "Groovy")
        .toObservable().startWith("Programming Languages").subscribe { println("Received $it") }
}