import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * Flowable.generate() 생성
 */
fun main() {
    val flowable = Flowable.generate<Int> {
        it.onNext(GenerateFlowableItem.item)
    }

    flowable.map { MyItemFlowable(it) }
        .observeOn(Schedulers.io())
        .subscribe {
            runBlocking { delay(100) }
            println("Next $it")
        }
    runBlocking { delay(60000) }
}

data class MyItemFlowable(val id: Int) {
    init {
        println("[MyItemFlowable Created $id]")
    }
}

object GenerateFlowableItem {
    var item: Int = 0
    get() {
        field += 1
        return field
    }
}