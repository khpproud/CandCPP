import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Coroutine을 적용한 ReactiveCalculator
 */
class ReactiveCalculatorV2(a: Int, b: Int) {
    val subjectCalc: Subject<ReactiveCalculatorV2> = PublishSubject.create()

    var nums: Pair<Int,Int> = Pair(0, 0)

    init {
        nums = Pair(a, b)

        subjectCalc.subscribe {
            it.run {
                calcAddtion()
                calcSubtraction()
                calcMultiplication()
                calcDivision()
            }
        }

        subjectCalc.onNext(this)
    }

    inline fun calcAddtion(): Int {
        val result = nums.first + nums.second
        println("Add = $result")
        return result
    }

    inline fun calcSubtraction(): Int {
        val result = nums.first - nums.second
        println("Subtract = $result")
        return result
    }

    inline fun calcMultiplication(): Int {
        val result = nums.first * nums.second
        println("Multiply = $result")
        return result
    }

    inline fun calcDivision(): Double {
        val result = nums.first / nums.second.toDouble()
        println("Divide = $result")
        return result
    }

    inline fun modifyNumbers(a: Int = nums.first, b: Int = nums.second) {
        nums = Pair(a, b)
        subjectCalc.onNext(this)
    }

    suspend fun handleInput(input: String?) {
        if (input != "exit") {
            val pattern: Pattern = Pattern.compile("([a|b])(?:\\s)?=(?:\\s)?(\\d*)")

            var a: Int? = null
            var b: Int? = null

            val matcher: Matcher = pattern.matcher(input)

            if (matcher.matches() && matcher.group(1) != null && matcher.group(2) != null) {
                if (matcher.group(1).toLowerCase() == "a")
                    a = matcher.group(2).toInt()
                else if(matcher.group(1).toLowerCase() == "b")
                    b = matcher.group(2).toInt()
            }

            when {
                a != null && b != null -> modifyNumbers(a, b)
                a != null -> modifyNumbers(a = a)
                b != null -> modifyNumbers(b = b)
                else -> println("Invalid Input")
            }
        }
    }
}

fun main() {
    println("Initial Output with a = 15, b = 10")
    var calculator: ReactiveCalculatorV2 = ReactiveCalculatorV2(15, 10)

    println("Enter a = <number> or b = <number> in separate lines\nexit to exit the program")
    var line: String?
    do {
        line = readLine()
        GlobalScope.async {
            calculator.handleInput(line)
        }
    } while (line != null && !line.toLowerCase().contains("exit"))
}