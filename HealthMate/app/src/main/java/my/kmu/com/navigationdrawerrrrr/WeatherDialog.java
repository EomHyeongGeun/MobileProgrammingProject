package my.kmu.com.navigationdrawerrrrr;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by eomhyeong-geun on 2016. 12. 17..
 */

public class WeatherDialog extends Activity{

    TextView text_weather, text_address;
    String xml;
    String itemValue;
    Button btn_weather_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_dialog);

        text_weather = (TextView)findViewById(R.id.text_weather);
        text_address = (TextView)findViewById(R.id.text_address);
        btn_weather_close = (Button)findViewById(R.id.btn_weather_close);

        // 닫기 버튼
        btn_weather_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Activity 실행과 동시에 웹서버에 접속하기 때문에 Thread를 사용해야 한다.
        new Thread(){
            @Override
            public void run() {
                StringBuffer sBuffer = new StringBuffer();
                try{
                    String urlAddr =  "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1129066000";
                    URL url = new URL(urlAddr);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    if(conn != null){
                        conn.setConnectTimeout(20000);
                        conn.setUseCaches(false);

                        if(conn.getResponseCode()==HttpURLConnection.HTTP_OK) {
                            //서버에서 읽어오기 위한 스트림 객체
                            InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                            //줄단위로 읽어오기 위해 BufferReader로 감싼다.
                            BufferedReader br = new BufferedReader(isr);
                            //반복문 돌면서읽어오기
                            while (true) {
                                String line = br.readLine();
                                if (line == null) {
                                    break;
                                }
                                sBuffer.append(line);
                            }
                            br.close();
                            conn.disconnect();
                        }
                    }
                    // 결과값 출력해보기
//                    text_weather.setText(sBuffer.toString());
                    xml = sBuffer.toString();
                }catch (Exception e){
                    e.printStackTrace();
                }
                parse();
            }
        }.start();



    }

    // xml 파싱하는 메소드
    public void parse(){
        try{

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();

            //xml을 InputStream형태로 변환
            InputStream is = new ByteArrayInputStream(xml.getBytes());

            //document와 element는 w3c dom에 있는 것을 임포트한다.
            Document doc = documentBuilder.parse(is);
            Element element = doc.getDocumentElement();


            //읽어올 태그명 정하기
            NodeList items_hour = element.getElementsByTagName("hour");
            NodeList items_temp = element.getElementsByTagName("temp");
            NodeList items_wfKor = element.getElementsByTagName("wfKor");
            //자료를 누적시킬 stringBuffer객체
            StringBuffer sBuffer = new StringBuffer();

            //반복문을 돌면서 모든 데이터 읽어오기(8번만!!   =>  3시간간격 8번 = 오늘 하루)
            for(int i=0; i<8; i++){
                //읽어온 자료에서 알고 싶은 문자열의 인덱스 번호를 전달한다.

                //시간 읽어오기
                Node item = items_hour.item(i);
                Node text = item.getFirstChild();

                //해당 노드에서 문자열 읽어오기
                itemValue = text.getNodeValue();
                sBuffer.append(itemValue+"시\r온도:");

                //온도 읽어오기
                item = items_temp.item(i);
                text = item.getFirstChild();
                itemValue = text.getNodeValue();
                sBuffer.append(itemValue+"도, 날씨:");

                //날씨 읽어오기
                item = items_wfKor.item(i);
                text = item.getFirstChild();
                itemValue = text.getNodeValue();
                sBuffer.append(itemValue+"\n");
            }

            //읽어온 문자열 출력해보기
            text_weather.setText(sBuffer.toString());
        }catch (Exception e){
            Log.e("파싱 중 에러 발생", e.getMessage());
        }
    }
}
