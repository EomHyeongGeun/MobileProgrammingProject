package my.kmu.com.samplewebview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends Activity {

    WebView webView;
    Intent i;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadWeb("http://192.168.27.4:8080/qqq.jsp");
        i = new Intent(this, NewIntent.class);
    }

    private void loadWeb(String url){
        // Context myApp = this;
        webView = (WebView)findViewById(R.id.webView);
        webView.clearCache(true);   // 한번 로드되고 나서 남은 찌꺼기들 삭제 >> 빠르게 진행되게 함.
        webView.getSettings().setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new AndroidHandler(), "sbsac"); // sbsac는 jsp에서 설정해준 키
        webView.getSettings().setDomStorageEnabled(true);

        webView.loadUrl(url);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
    }

    public void confirmMsg(){
        // alert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("종료하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    public class AndroidHandler{
        @JavascriptInterface    // 우리가 쓸 Interface라는것을 명시해줘야함.
        public void setMsg(final String args){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d("SBSWEB", "setMsg(" + args + ")");
                    String msg = args;
                    if(args.equalsIgnoreCase("exit")){
                        //todo
                        confirmMsg();
                    }
                }
            });
        }
        @JavascriptInterface    // Method 기준으로 각각 있어야함.
        public void toastShort(String msg){
            Log.d("SBSWEB", msg);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public void newIntent(){
            Log.d("SBSWEB", "new Intent");
            startActivity(i);
        }
    }
}
