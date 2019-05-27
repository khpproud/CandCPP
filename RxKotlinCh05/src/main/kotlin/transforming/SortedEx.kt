package transforming

import io.reactivex.rxkotlin.toObservable

/**
 * sorted() - 정렬 연산자 - Comparable을 인자로 정렬을 수행
 */
fun main() {
    println("default with Integer")
    listOf(2, 6, 7, 3, 1, 5, 4, 8, 10, 9).toObservable().sorted().subscribe { print("$it ")}
    println()

    println("default with String")
    listOf("alpha", "gamma", "beta", "theta").toObservable().sorted().subscribe { print("$it ")}
    println()

    println("custom sortFunction with Integer")
    listOf(2, 6, 7, 3, 1, 5, 4, 8, 10, 9).toObservable().sorted { o1, o2 -> if (o1 > o2) -1 else 1 }
        .subscribe { print("$it ")}
    println()

    println("customsortFunction with custom class-object")
    listOf(MyItem1(2), MyItem1(4), MyItem1(5), MyItem1(3), MyItem1(1))
        .toObservable().sorted { o1, o2 -> if (o1.item < o2.item) -1 else 1 }.subscribe { println("Received $it") }
}

data class MyItem1(val item: Int)