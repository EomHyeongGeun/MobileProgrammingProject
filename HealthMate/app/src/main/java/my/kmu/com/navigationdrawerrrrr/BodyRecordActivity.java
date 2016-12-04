package my.kmu.com.navigationdrawerrrrr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by eomhyeong-geun on 2016. 11. 20..
 */

public class BodyRecordActivity extends Activity {
    Button resetbody_btn, upload_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.body_record);
        ListView listView = (ListView)findViewById(R.id.listView);
        ListViewAdapter adapter = new ListViewAdapter();

        listView.setAdapter(adapter);

        upload_btn = (Button)findViewById(R.id.upload_btn);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UploadActivity.class);
                startActivity(intent);
            }
        });

        adapter.addItem(1, "차차야! 좀 더 열심히좀 하자!" , "2016년 12월 3일",ContextCompat.getDrawable(this, R.drawable.healthmate_four));
        adapter.addItem(2, "분발 하자구 !" , "2016년 12월 4일",ContextCompat.getDrawable(this, R.drawable.healthmate_six));
        adapter.addItem(3, "으휴 뚱뚱한 것 좀 봐!" , "2016년 12월 5일",ContextCompat.getDrawable(this, R.drawable.healthmate_eight));

    }
}
