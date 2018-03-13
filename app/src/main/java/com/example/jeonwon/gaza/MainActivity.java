package com.example.jeonwon.gaza;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText ID, PW;
    private TextView Find;
    private Button btnLogin, btnJoin, btnFind;

    private String sID, sPW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        ID = (EditText) findViewById(R.id.edtID);
        PW = (EditText) findViewById(R.id.edtPW);

        btnLogin = (Button) findViewById(R.id.login);
        btnJoin = (Button) findViewById(R.id.signUp);
        Find = (TextView) findViewById(R.id.findIDorPW);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.login:
                        Login(view);
                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        startActivity(intent);
                        ID.setText("");
                        PW.setText("");
                        break;
                    case R.id.findIDorPW:

                        break;
                    case R.id.signUp:
                        intent = new Intent(MainActivity.this, SubActivity.class);
                        startActivity(intent);
                        break;

                }

            }
        };

        btnJoin.setOnClickListener(listener);
        //btnFind.setOnClickListener(listener);
        btnLogin.setOnClickListener(listener);


    }

    public void Login(View v) {

        try {
            sID = ID.getText().toString();
            sPW = PW.getText().toString();
        } catch (NullPointerException e) {
            Log.d("Error", e.getMessage());
        }

        LoginDB loinDB = new LoginDB();
        loinDB.execute();
    }

    public class LoginDB extends AsyncTask<Void, Integer, Void> {

        String data = "";


        @Override
        protected Void doInBackground(Void... voids) {

            String connURL = "http://192.168.0.128/loginGaza.v2.0.php";
            String param = "id=" + sID + "&pw=" + sPW + "";

            Log.e("POST", param);

            try {
                //서버 연결
                //서버 연결
                URL url = new URL(connURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                //OutputStream은 안드로이드->서버
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(param.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                InputStream is = null;
                BufferedReader in = null;

                is = httpURLConnection.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff = new StringBuffer();
                while ((line = in.readLine()) != null) {
                    buff.append(line + "\n");
                }
                data = buff.toString().trim();

                /* 서버에서 응답 */
                Log.e("RECV DATA", data);

                if (data.equals("0")) {
                    Log.e("RESULT", "성공적으로 처리되었습니다!");
                } else {
                    Log.e("RESULT", "에러 발생! ERRCODE = " + data);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);

            if (data.equals("0")) {
                Log.e("RESULT", "성공적으로 처리되었습니다!");
                Toast.makeText(MainActivity.this, sID + "님 환영합니다!", Toast.LENGTH_LONG).show();

            } else if (data.equals("1")) {
                Log.e("RESULT", "비밀번호가 일치하지 않습니다.");
                Toast.makeText(MainActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
            } else {
                Log.e("RESULT", "에러 발생! ERRCODE = " + data);
                Toast.makeText(MainActivity.this, data + "등록중 에러", Toast.LENGTH_LONG).show();

            }


        }

    }

}
