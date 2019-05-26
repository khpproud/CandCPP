/**
 * 시퀀스 생성하기 예
 */
fun main() {
    // 간단한 피보나치 수열 - 반복문 사용
    var a = 0
    var b = 1
    print("$a, ")
    print("$b, ")

    for (i in 2.. 9) {
        val c = a + b
        print("$c, ")
        a = b
        b = c
    }
    println()

    // 시퀀스를 사용한 방법
    val fibonacciSeries = sequence {
        var a = 0
        var b = 1

        yield(a)
        yield(b)

        while (true) {
            val c = a + b
            yield(c)
            a = b
            b = c
        }
    }

    println(fibonacciSeries.take(10).toList())

    // or
    val fibonacci2 = sequence {
        var terms = Pair(0, 1)

        while (true) {
            yield(terms.first)
            terms = Pair(terms.second, terms.second + terms.first)
        }
    }

    println(fibonacci2.take(10).toList())
}