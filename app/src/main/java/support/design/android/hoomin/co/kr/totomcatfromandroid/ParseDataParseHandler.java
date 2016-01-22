package support.design.android.hoomin.co.kr.totomcatfromandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ccei on 2016-01-22.
 */
public class ParseDataParseHandler {
    public static ArrayList<BloodValueObject> getJSONBloodRequestAllList(
           StringBuilder buf){

        ArrayList<BloodValueObject> jsonAllList = null;

        JSONArray jsonArray = null;
        try{
            jsonAllList = new ArrayList<BloodValueObject>();
            jsonArray = new JSONArray(buf.toString());
            int jsonObjSize = jsonArray.length();

            for(int i = 0; i < jsonObjSize; i++){
                BloodValueObject entity = new BloodValueObject();
                JSONObject jData = jsonArray.getJSONObject(i);
                entity.bloodId = jData.getInt("bloodID");
                entity.bloodType = jData.getString("bloodType");

                entity.statusText = jData.getString("statusText");
                entity.donationType = jData.getString("donationType");
                entity.bloodValue = jData.getString("bloodValue");
                entity.hospital = jData.getString("hospital");
                entity.hospitalPhone = jData.getString("hospitalPhone");
                entity.relationText = jData.getString("relationText");
                entity.careName = jData.getString("careName");
                entity.carePhone = jData.getString("carePhone");
                entity.insertDate = jData.getString("insertDate");

                jsonAllList.add(entity);
            }
        } catch (JSONException je) {
            Log.e("getJSONBloodReques", "JSON파싱 중 에러발생", je);
        }
        return jsonAllList;

    }
}
