package support.design.android.hoomin.co.kr.totomcatfromandroid;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by ccei on 2016-01-22.
 */
public class NetworkDefineConstant {
    public static final String HOST_URL = "http:// 192.168.11.52:5678";

    //저장관련 URL주소
    public static final String BLOOD_JSON_REQUEST_SELECT =
            "/androidNetwork/jSONbloodAllSelect.pyo";

    public static HttpURLConnection getURLConnection(String target, String httpMethod){
        HttpURLConnection conn = null;
        try{
            URL url = new URL(target);
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod(httpMethod);
            conn.setDoInput(true);
            conn.setDoOutput(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
