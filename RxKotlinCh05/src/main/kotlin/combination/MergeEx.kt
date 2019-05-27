package combination

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

/**
 * merge() - 프로듀서들으르 병합(지정된 순서를 유지 하지 않음) - 배출의 원자성은 유지됨
 */
fun main() {
    val observable1 = listOf("Kotlin", "Scala", "Groovy").toObservable()
    val observable2 = listOf("Python", "Java", "C++", "C").toObservable()
//
//    Observable.merge(observable1, observable2).subscribe { println("Received $it") }

//    val observable1= Observable.interval(500, TimeUnit.MILLISECONDS).map { "Observable 1: $it" }
//    val observable2= Observable.interval(200, TimeUnit.MILLISECONDS).map { "Observable 2: $it" }
//
//    startTime
//    Observable.merge(observable1, observable2).subscribe { println("Received $it - ${elapsedTime()}ms") }
//    runBlocking { delay(1500) }

    // mergeArray()
    val obs1 = (0.. 3).map {i: Int ->  Character.valueOf('A' + i) }.toObservable()
    val obs2 = (4.. 6).map {i: Int ->  Character.valueOf('A' + i) }.toObservable()
    val obs3 = (7.. 10).map {i: Int ->  Character.valueOf('A' + i) }.toObservable()
    val obs4 = (11.. 15).map {i: Int ->  Character.valueOf('A' + i) }.toObservable()
    val obs5 = (16.. 20).map {i: Int ->  Character.valueOf('A' + i) }.toObservable()
    val obs6 = (21.. 25).map {i: Int ->  Character.valueOf('A' + i) }.toObservable()

    Observable.mergeArray(obs1, obs2, obs3, obs4, obs5, obs6).subscribe { print("$it ") }
    println()

    // mergeWith() - Observable 인스턴스에서 다른 프로듀서 결합
    observable1.mergeWith(observable2).subscribe { println("Received $it") }
}