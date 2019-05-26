import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Chap.02 Reactive Calculation : 불필요한 subject 제거 및 inline 함수로 대체
 */
class ReactiveCalculation(a: Int, b: Int) {
    val subjectCalc: Subject<ReactiveCalculation> = PublishSubject.create()

    var nums: Pair<Int, Int> = Pair(0, 0)
    init {
        nums = Pair(a, b)

        subjectCalc.subscribe {
            it.run {
                calculateAddition()
                calculateSubtraction()
                calculateMultiplication()
                calculateDivision()
            }
        }
        subjectCalc.onNext(this)
    }

    inline fun calculateAddition(): Int {
        val result = nums.first + nums.second
        println("Add = $result")
        return result
    }

    inline fun calculateSubtraction(): Int {
        val result = nums.first - nums.second
        println("Subtract = $result")
        return result
    }

    inline fun calculateMultiplication(): Int {
        val result = nums.first * nums.second
        println("Multiply = $result")
        return result
    }

    inline fun calculateDivision(): Double {
        val result = nums.first / nums.second.toDouble()
        println("Divide = $result")
        return result
    }

    inline fun modifyNumbers(a: Int = nums.first, b: Int = nums.second) {
        nums = Pair(a, b)
        subjectCalc.onNext(this)
    }

    fun handleInput(input: String?) {
        if (input != "exit") {
            val pattern: Pattern = Pattern.compile("([a|b])(?:\\s)?=(?:\\s)?(\\d*)")
            var a: Int? = null
            var b: Int? = null

            val matcher: Matcher = pattern.matcher(input)

            if (matcher.matches() && matcher.group(1) != null && matcher.group(2) != null) {
                if (matcher.group(1).toLowerCase() == "a")
                    a = matcher.group(2).toInt()
                else if (matcher.group(1).toLowerCase() == "b")
                    b = matcher.group(2).toInt()
            }

            when {
                a != null && b != null -> modifyNumbers(a, b)
                a != null -> modifyNumbers(a = a)
                b != null -> modifyNumbers(b = b)
                else -> println("Invalid input!")
            }
        }
    }
}

fun main() {
    var calculator: ReactiveCalculation = ReactiveCalculation(15, 10)
    println("Enter a = <number> or b = <number> in separate lines\nexit to exit the program")
    var line: String?
    do {
        line = readLine()
        calculator.handleInput(line)
    } while (line != null && !line.toLowerCase().contains("exit"))
}