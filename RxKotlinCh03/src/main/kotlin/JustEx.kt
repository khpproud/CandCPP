import io.reactivex.Observable

/**
 * Observable.just() 예
 */
fun main() {
    Observable.just("A String").subscribe(observer)
    Observable.just(54).subscribe(observer)
    Observable.just(
        listOf("String 1", "String 2", "String 3", "String 4")
    ).subscribe(observer)
    Observable.just(
        mapOf(Pair("key 1", "value 1"), Pair("key 2", "value 2"), Pair("key 3", "value 3"))
    ).subscribe(observer)
    Observable.just(arrayListOf(1, 2, 3, 4, 5)).subscribe(observer)
    Observable.just("String 1", "String 2", "String 3").subscribe(observer)
}