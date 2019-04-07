package reactivejava2.common;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// OkHttp 연결 헬퍼 클래스
public class OkHttpHelper {
    public static final String GITHUB_ZEN_URL = "https://api.github.com/zen";
    
    private static OkHttpClient client = new OkHttpClient();
    
    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        
        try {
            Response res = client.newCall(request).execute();
            return res.body().string();
        } catch (IOException e) {
            Log.e(e.getMessage());
            //e.printStackTrace();
            throw e;
        }
    }
    
    public static String getT(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        
        try {
            Response res = client.newCall(request).execute();
            return res.body().string();
        } catch (IOException e) {
            Log.et(e.getMessage());
            //e.printStackTrace();
            throw e;
        }
    }
}
