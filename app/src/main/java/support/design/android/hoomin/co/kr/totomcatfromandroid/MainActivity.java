package support.design.android.hoomin.co.kr.totomcatfromandroid;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //JSON
    ImageButton bloodListReqbtn;
    ArrayList<BloodValueObject> mData;  //?
    ListView list;
    ArrayAdapter adapter;
    String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reqlist);

        bloodListReqbtn = (ImageButton) findViewById(R.id.btnList);
        bloodListReqbtn.setBackgroundResource(R.drawable.listbtnpressed);

        //요청 목록 선택 Spinner
        Spinner sp = (Spinner) findViewById(R.id.listspinner);

        adapter = ArrayAdapter.createFromResource(
                this, R.array.donationtype_array_item, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);
                new AsyncBloodJSONList().execute(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                new AsyncBloodJSONList().execute("전체목록");
            }
        });

        list = (ListView) findViewById(R.id.reqlist);

        bloodListReqbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncBloodJSONList().execute("jsonlist");
            }
        });

    }

    public class AsyncBloodJSONList extends AsyncTask<String,
                                Integer, ArrayList<BloodValueObject>> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(MainActivity.this, "", "잠시만 기다려 주세요 ...", true);
        }

        @Override
        protected ArrayList<BloodValueObject> doInBackground(String... params) {
            String requestQuery = "requestItem=" + params[0];
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            ArrayList<BloodValueObject> bloodList = null;
            try {
                URL target = new URL(NetworkDefineConstant.HOST_URL +
                        NetworkDefineConstant.BLOOD_JSON_REQUEST_SELECT);
                conn = (HttpURLConnection) target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                OutputStream toServer = conn.getOutputStream();
                toServer.write(requestQuery.getBytes("UTF-8"));
                toServer.close();
                int resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    fromServer = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));
                    String line = null;
                    StringBuilder jsonBuf = new StringBuilder();
                    while ((line = fromServer.readLine()) != null) {
                        jsonBuf.append(line);
                    }
                    bloodList = ParseDataParseHandler.getJSONBloodRequestAllList(jsonBuf);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fromServer != null) {
                    try {
                        fromServer.close();
                    } catch (IOException ioe) {

                    }
                }
                conn.disconnect();
            }
            return bloodList;
        }

        @Override
        protected void onPostExecute(ArrayList<BloodValueObject> result) {
            dialog.dismiss();

            if (result != null && result.size() > 0) {
                BloodJSONReqListAdapter bloodListAdapter =
                        new BloodJSONReqListAdapter(
                                MainActivity.this, result);
                bloodListAdapter.notifyDataSetChanged();
                list.setAdapter(bloodListAdapter);
            }
        }
    }

    public class BloodJSONReqListAdapter extends ArrayAdapter<BloodValueObject> {

        public BloodJSONReqListAdapter(Context context, List<BloodValueObject> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_reqlist, null);

                holder = new ViewHolder();
                holder.mTvContent = (TextView) convertView.findViewById(R.id.requestcontent);
                holder.mTvCurrent = (TextView) convertView.findViewById(R.id.requestcurrent);
                holder.mTvWriteTime = (TextView) convertView.findViewById(R.id.writetime);
                holder.mImgDetail = (ImageView) convertView.findViewById(R.id.imgdetail);

                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();
            BloodValueObject bloodData = getItem(position);
            holder.mImgDetail.setBackgroundResource(R.drawable.listbtnred);
            holder.mImgDetail.setImageResource(R.drawable.listbtnred);

            holder.mTvCurrent.setText("현재 상황: " + bloodData.bloodType + "," +
                    bloodData.bloodValue + " 개 필요");
            holder.mTvWriteTime.setText(bloodData.insertDate);
            String sTime = bloodData.insertDate.substring(0, 16);
            holder.mTvWriteTime.setText(sTime);

            return convertView;
        }

        public class ViewHolder {
            TextView mTvContent;
            TextView mTvCurrent;
            TextView mTvWriteTime;
            ImageView mImgDetail;
        }
    }
}