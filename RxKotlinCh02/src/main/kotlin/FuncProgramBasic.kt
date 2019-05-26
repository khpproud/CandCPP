
import java.util.*

/**
 * 함수형 프로그래밍 기초 - 람다, 순수 함수, 고차 함수, 함수 유형, 인라인 함수 예
 */

fun main() {
    // 람다 표현식
    val sum = { x: Int, y: Int -> x + y }
    println("Sum 12 + 14 = ${sum(12, 14)}")
    val anonymousMulti = { x: Int -> (Random().nextInt(10) + 1) * x }
    println("Random ouput rand(1 ~ 10) * 2 = ${anonymousMulti(2)}")

    // 순수 함수
    println("named pure func square(3) = ${square(3)}")
    val qube = { n: Int -> n * n * n }
    println("lambda pure fun qube(3) = ${qube(3)}")

    // 고차 함수
    highOrderFunc(12) { it.isEven() }
    highOrderFunc(9) { it.isEven() }

    // 인라인 함수
    for (i in 1.. 10)
        println("$i output $i + ($i * $i) =  ${doSomeStuff(i)}")
}

fun square(n: Int): Int = n * n

inline fun highOrderFunc(a: Int, validityCheckFunc:(a: Int) -> Boolean) {
    if (validityCheckFunc(a))
        println("a $a is valid!")
    else
        println("a $a is invalid!")
}

inline fun doSomeStuff(a: Int = 0) = a + (a * a)