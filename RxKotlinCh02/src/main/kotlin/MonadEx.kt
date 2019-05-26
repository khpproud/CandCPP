import io.reactivex.Maybe
import io.reactivex.rxkotlin.subscribeBy

/**
 * 함수형 프로그래밍의 한 예인 모나드 - 값을 캡술화하고 추가 기능을 더해 새로운 타입을 생성하는 구조체
 */
fun main() {
    val maybeValue: Maybe<Int> = Maybe.just(14)
    maybeValue.subscribeBy(
        onComplete = { println("Completed Empty") },
        onError = { println("Error $it") },
        onSuccess = { println("Completed with value $it") }
    )

    val maybeEmpty: Maybe<Int> = Maybe.empty()
    maybeEmpty.subscribeBy(
        onComplete = { println("Completed empty") },
        onError = { println("Error $it") },
        onSuccess = { println("Completed with valu $it") }
    )
}