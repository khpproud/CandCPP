import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.toFlowable
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * ConnectableFlowable - ConnectableObservable 과 마찬가지로 subscribe 시 배출되지 않고 connect() 시 배출됨
 * Processor - Flowable 의 Subject 에 해당
 */
fun main() {
//    val connectableFlowable = listOf("Str 1", "Str 2", "Str 3", "Str 4", "Str 5").toFlowable().publish()
//    connectableFlowable.subscribe {
//        println("Subscription 1 : $it")
//        runBlocking { delay(500) }
//        println("Subscription 1 delay")
//    }
//    connectableFlowable.subscribe { println("Subscription 2 : $it")}
//    connectableFlowable.connect()

    val flowable = listOf("Str 1", "Str 2", "Str 3", "Str 4", "Str 5").toFlowable()
    val processor = PublishProcessor.create<String>()

    processor.subscribe {
        println("Subscription 1 : $it")
        runBlocking { delay(500) }
        println("Subscription 1 delay")
    }

    processor.subscribe { println("Subscription 2 : $it") }
    flowable.subscribe(processor)
}