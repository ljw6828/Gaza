package com.example.jeonwon.gaza;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by 조영태 on 2018-03-20.
 */

public class ScheduleDetail extends AppCompatActivity {

    Intent intent;

    Button button;

    EditText editLocation;
    EditText editTime;
    EditText editBudget;
    EditText editSpentMoney;
    EditText editMemo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_detail);
        intent = getIntent();//new Intent(getApplicationContext(), ListViewSchedule.class);


        editLocation = findViewById(R.id.editLocation);
        editTime = findViewById(R.id.editTime);
        editBudget = findViewById(R.id.editBudget);
        editSpentMoney = findViewById(R.id.editSpentmoney);
        editMemo = findViewById(R.id.editMemo);
        button = findViewById(R.id.btnOkdetail);

        editLocation.setText(intent.getStringExtra("title"));
        editTime.setText(intent.getStringExtra("startDate"));
        editBudget.setText(intent.getStringExtra("budget"));
        editSpentMoney.setText(intent.getStringExtra("spentmoney"));
        editMemo.setText(intent.getStringExtra("memo"));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
               /* try {
                    intent.putExtra("place", editLocation.getText().toString());
=======
                try {
                    intent.putExtra("title", editLocation.getText().toString());
>>>>>>> 86e343cf0ec35de61beaf55c948f310d267e7261
                } catch (NumberFormatException e) {
                    intent.putExtra("title", "");
                }

                //  try {
                Log.i("d", editTime.getText().toString());
                intent.putExtra("startDate", editTime.getText().toString());
                //     }catch (NumberFormatException e){
                //        Log.i("에러ㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓ","ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ");
                //        intent.putExtra("time", "");
                //    }

                try {
                    intent.putExtra("budget", editBudget.getText().toString());
                } catch (NumberFormatException e) {
                    intent.putExtra("budget", "");
                }

                try {
                    intent.putExtra("spentmoney", editSpentMoney.getText().toString());
                } catch (NumberFormatException e) {
                    intent.putExtra("spentmoney", "");
                }

                try {
                    intent.putExtra("memo", editMemo.getText().toString());
                } catch (Exception e) {
                    intent.putExtra("memo", "");
                }*/
                intent.putExtra("place",editLocation.getText().toString());
                intent.putExtra("time",editTime.getText().toString());
                intent.putExtra("budget",editBudget.getText().toString());
                intent.putExtra("spentmoney",editSpentMoney.getText().toString());


                setResult(RESULT_OK, intent);
                finish();


            }
        });
    }
}