package my.kmu.com.sampledb;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {
    EditText ed_userId, ed_userPw;
    Button btnSave;
    String uId, uPw;
    LoadJsp task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 객체 얻기
        ed_userId = (EditText)findViewById(R.id.ed_userId);
        ed_userPw = (EditText)findViewById(R.id.ed_userPw);
        btnSave   = (Button)findViewById(R.id.btnSave);

        // btn 클릭 시
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uId = ed_userId.getText().toString();
                uPw = ed_userPw.getText().toString();
                task = new LoadJsp();
                task.execute();
            }
        });
    } // onCreate End

    class LoadJsp extends AsyncTask<Void, String, Void>{    // 비동기식 처리
        @Override
        protected Void doInBackground(Void... param) {
            try{
                HttpClient client = new DefaultHttpClient();
                String loadUrl = "http://192.168.1.70:8080/getData.jsp"; // 본인의 jsp 정보

                HttpPost post = new HttpPost(loadUrl);
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

                //params에 값 담기
                params.add(new BasicNameValuePair("userId", uId));
                params.add(new BasicNameValuePair("userPw", uPw));

                //인코더 시키기.
                UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                post.setEntity(ent);

                HttpResponse responsePost = client.execute(post);
                HttpEntity resEntity = responsePost.getEntity();    // 응답했을때
                if(resEntity != null){
                    Log.d("Response", EntityUtils.toString(resEntity));
                }

            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
} // MainActivity End
