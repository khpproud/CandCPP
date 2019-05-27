package transforming

import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable

/**
 * map() - 주어진 람다를 수행하고 이를 다운스트림으로 내보냄
 */
fun main() {
    Observable.range(1, 5).map { it * it }.subscribe { println("doubled $it") }

    // cast 연산자
    val list = listOf(
        MyItemInherit(1),
        MyItemInherit(2),
        MyItemInherit(3),
        MyItemInherit(4),
        MyItemInherit(5)
    )

    list.toObservable().map { it as MyItem }.subscribe { println(it) }

    println("cast")
    list.toObservable().cast(MyItem::class.java).subscribe { println(it) }

}

open class MyItem(val id: Int) {
    override fun toString(): String {
        return "[MyItem $id]"
    }
}

class MyItemInherit(id: Int): MyItem(id) {
    override fun toString(): String {
        return "[MyItemInherit $id]"
    }
}