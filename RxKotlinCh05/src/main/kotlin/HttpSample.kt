import org.apache.http.impl.nio.client.HttpAsyncClients
import rx.apache.http.ObservableHttp

/**
 * 연산자를 사용한 HTTP 연결 예
 */
fun main() {
    // CloseableHttpAsyncClient 인스턴스를 가져옴
    val httpClient = HttpAsyncClients.createDefault()
    // HTTP 요청을 전달하기전 클라이언트를 시작
    httpClient.start()
    // GET 요청을 생성하고 Observer 타입의 ObserverHttpResponse 로 변경
    ObservableHttp.createGet("http://rivuchk.com/feed/json", httpClient).toObservable()
        .flatMap {
            // 응답 byte 를 String 으로 변경
            response -> response.content.map { bytes -> String(bytes) }
        }.onErrorReturn { "Error Parsing Data" }
        .subscribe {
            println(it)
            httpClient.close()
        }
}