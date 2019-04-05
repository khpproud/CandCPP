package reactivejava2.chap05;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import reactivejava2.common.CommonUtils;
import reactivejava2.common.Log;
import reactivejava2.common.OkHttpHelper;

/**
 * 
 * REST API 호출을 한 번으로 할 수 있는 방법 
 *
 */
public class OpenWeatherMapV2 {
    private static String URL_ROOT = 
            "https://api.openweathermap.org/data/2.5/weather?q=Seoul&units=metric&appid=";
    // hidden...
    private static String API_KEY = "";
    
    public void run() {
        CommonUtils.exampleStart();
        
        Observable<String> source = Observable.just(URL_ROOT + API_KEY)
                .map(OkHttpHelper::get)
                .subscribeOn(Schedulers.io())
                .share()
                .observeOn(Schedulers.newThread());
        
        source.map(this::parseTemp).subscribe(Log::it);
        source.map(this::parseCity).subscribe(Log::it);
        source.map(this::parseCountry).subscribe(Log::it);
        
        CommonUtils.sleep(2000);
    }
    
 // json에서 온도 추출
    private String parseTemp(String json) {
        return parse(json, "\"temp\":[0-9]*.[0-9]*");
    }
    
    // json에서 도시 추출
    private String parseCity(String json) {
        return parse(json, "\"name\":\"[a-zA-Z]*\"");
    }
    
    // json에서 국가 코드 추출
    private String parseCountry(String json) {
        return parse(json, "\"country\":\"[a-zA-Z]*\"");
    }
    
    // 온도, 도시 이름, 국가 코드 추출
    private String parse(String json, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(json);
        // 패턴 매칭 성공
        if (match.find())
            return match.group();
        // 패턴 매칭 실패
        return "N/A";
    }
    
    public static void main(String[] args) {
        OpenWeatherMapV2 demo = new OpenWeatherMapV2();
        demo.run();
    }
}
