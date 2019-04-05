package reactivejava2.chap05;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import reactivejava2.common.Log;
import reactivejava2.common.OkHttpHelper;

/**
 * 
 * 성공 콜백이 왔을 때 한번더 Url 요청
 *
 */
public class CallbackHell {
    private final OkHttpClient client = new OkHttpClient();
    
    // 첫 번쩨 호출이 성공 했을 때의 두 번째 콜백
    private Callback onSuccess = new Callback() {
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.i(response.body().string());
        }
        
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
        }
    };
    
    public void run() {
        Request request = new Request.Builder()
                .url(OkHttpHelper.GITHUB_ZEN_URL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(response.body().string());
                
                // 콜백을 다시 추가
                Request request = new Request.Builder()
                        .url(OkHttpHelper.GITHUB_ZEN_URL)
                        .build();
                client.newCall(request).enqueue(onSuccess);
            }
            
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    public static void main(String[] args) {
        CallbackHell demo = new CallbackHell();
        demo.run();
    }
}
