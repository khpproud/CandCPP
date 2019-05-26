import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * 반응형 계산 프로그램 예
 */
fun main() {
    println("Initial Output with a = 15, b = 10")
    var calculator: ReactiveCalculator = ReactiveCalculator(15, 10)
    println("Enter a = <number> or b = <number> is separate lines\nexit to exit the program")
    var line: String?
    do {
        line = readLine()
        calculator.handleInput(line)
    } while (line != null && !line.toLowerCase().contains("exit"))
}

class ReactiveCalculator(a: Int, b: Int) {
    internal val subjectAdd: Subject<Pair<Int, Int>> = PublishSubject.create()
    internal val subjectSub: Subject<Pair<Int, Int>> = PublishSubject.create()
    internal val subjectMulti: Subject<Pair<Int, Int>> = PublishSubject.create()
    internal val subjectDiv: Subject<Pair<Int, Int>> = PublishSubject.create()

    internal val subjectCalc: Subject<ReactiveCalculator> = PublishSubject.create()

    private var nums:Pair<Int, Int> = Pair(0, 0)

    init {
        nums = Pair(a, b)

        subjectAdd.map { it.first + it.second }.subscribe { println("Add = $it") }
        subjectSub.map { it.first - it.second }.subscribe { println("Subtract = $it") }
        subjectMulti.map { it.first * it.second }.subscribe { println("Multiply = $it") }
        subjectDiv.map { it.first / it.second.toDouble() }.subscribe { println("Divide = $it") }

        subjectCalc.subscribe {
            it.run {
                calculateAddition()
                calculateSutraction()
                calculateMultiplication()
                calculateDivision()
            }
        }
        subjectCalc.onNext(this)
    }

    fun calculateAddition() {
        subjectAdd.onNext(nums)
    }

    fun calculateSutraction() {
        subjectSub.onNext(nums)
    }

    fun calculateMultiplication() {
        subjectMulti.onNext(nums)
    }

    fun calculateDivision() {
        subjectDiv.onNext(nums)
    }

    fun modifyNumbers(a: Int = nums.first, b: Int = nums.second) {
        nums = Pair(a, b)
        subjectCalc.onNext(this)
    }

    fun handleInput(input: String?) {
        if (!input.equals("exit")) {
            val pattern: Pattern = Pattern.compile("([a|b])(?:\\s)?=(?:\\s)?(\\d*)")
            var a: Int? = null
            var b: Int? = null

            val matcher: Matcher = pattern.matcher(input)

            if (matcher.matches() && matcher.group(1) != null && matcher.group(2) != null) {
                if (matcher.group(1).toLowerCase() == "a") {
                    a = matcher.group(2).toInt()
                } else if (matcher.group(1).toLowerCase() == "b") {
                    b = matcher.group(2).toInt()
                }
            }

            when {
                a != null && b!= null -> modifyNumbers(a, b)
                a != null -> modifyNumbers(a = a)
                b != null -> modifyNumbers(b = b)
                else -> println("Invalid Input")
            }
        }
    }
}