import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * Coroutine 사용 예
 */
fun main() = runBlocking {
//    val executeTime = longRunningTask()
//    println("Execution time is $executeTime")

    val time = async { longRunningTask() }
    println("Print after async")
    runBlocking { println("printing time ${time.await()}") }
}

suspend fun longRunningTask(): Long {
    val time = measureTimeMillis {
        println("Please Wait...")
        delay(2000)
        println("Delay over")
    }
    return time
}