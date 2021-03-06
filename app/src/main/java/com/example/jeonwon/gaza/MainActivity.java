package com.example.jeonwon.gaza;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by JeonWon on 2018-03-10.
 */

//구글맵 API 키
//AIzaSyC-PiJhaeOQcu288CgZD8Dt-xo1idhWViQ

public class MainActivity extends AppCompatActivity {
    public class Point implements Serializable{
        public double x;
        public double y;
    }
    public static ArrayList<LatLng> arrayPoint = new ArrayList<>();
    protected static DBHelper dbHelper;
    private static final int REQESTCODE = 832;
    private static final int REQUESTCODE = 711;

    private Button button;
    private ListView listView;
    private ListViewPlanAdapter listViewPlanAdapter;
    private Toolbar toolbar;
    private List<Plan> plan;

    Place place;
    CharSequence name;
    CharSequence address;
    LatLng latlng;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("DB :", "DB생성 전");
        //Div is Creating DB
        dbHelper = new DBHelper(MainActivity.this, "GazaDB", null, 1);
        Log.i("DB :", "DB생성 완료함");

//        dbHelper.testDB();
        //Div is Creating DB
        //if DB is not exist
        if (dbHelper == null) {
            dbHelper = new DBHelper(this, "GazaDB", null, 1);
        }
        //툴바
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);//뒤로가기 버튼
        //actionBar.setHomeAsUpIndicator(R.drawable.round_button);//뒤로가기버튼 모양 커스텀


        //툴바 설정

        button = (Button) findViewById(R.id.buttonMakePlan);

        //My Plan List in Main
        listViewPlanAdapter = new ListViewPlanAdapter();
        listView = (ListView) findViewById(R.id.listViewPlan);

        listView.setAdapter(listViewPlanAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ScheduleDetail.class);
                intent.putExtra("title", plan.get(position).getTitle());
                intent.putExtra("startDate", plan.get(position).getStartDate());
                intent.putExtra("endDate", plan.get(position).getEndDate());
                intent.putExtra("people", plan.get(position).getPeople());
                intent.putExtra("budget", plan.get(position).getBudget());
                intent.putExtra("pid", plan.get(position).getPid());
                startActivityForResult(intent, REQUESTCODE);
                Toast.makeText(getApplicationContext(), ""+plan.get(position).getPid(), Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.i("Dialog :", "롱클릭 다이얼로그");

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);

                alertBuilder.setTitle("삭제");
                alertBuilder.setMessage("일정을 삭제하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("삭제",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dbHelper.deletePlan(plan.get(position));
                                        displayPlan();
                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
                return true;
            }
        });




        displayPlan();

        /*for (int i = 0; i < planName.length; i++) {
            listViewPlanAdapter.addPlanItem(planName[i]);
        }*/


//        buttonMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//                startActivity(intent);
//            }
//        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MakePlan.class);
                startActivityForResult(intent,REQESTCODE);
            }
        });


    }

    public void displayPlan() {
        listViewPlanAdapter.deleteAll();
        plan = dbHelper.getAllPlanData();

        for (int i = 0; i < plan.size(); i++) {
            listViewPlanAdapter.addPlanItem(plan.get(i).getTitle());
            listViewPlanAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQESTCODE){
            if(resultCode == Activity.RESULT_OK){
                displayPlan();
            }
        }else if(requestCode==300 && resultCode == RESULT_OK){
            place = PlacePicker.getPlace(this, data);
            name = place.getName();
            address = place.getAddress();
            latlng = place.getLatLng();
            arrayPoint.add(latlng);

            Toast.makeText(MainActivity.this, " 장소명 : " + name + "\n주소 : " + address + "\n좌표 : " + latlng,Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuProfile:
                //프로필 눌렀을때
                Toast.makeText(this, "메뉴 프로필", Toast.LENGTH_SHORT).show();
                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = intentBuilder.build(MainActivity.this);
                    startActivityForResult(intent, 300);
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
//                    } catch (GooglePlayServicesNotAvailableException e1) {
//                        e1.printStackTrace();
//                    } catch (GooglePlayServicesRepairableException e1) {
//                        e1.printStackTrace();
//                    }
                break;
            case R.id.menuNotice:
                //공지사항 눌렀을때
                Toast.makeText(this, "메뉴 공지사항", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("point",arrayPoint);

                intent.putExtra("x",name);
               // intent.putExtra("y",arrayLng);
                startActivity(intent);
                break;

        }


        return super.onOptionsItemSelected(item);
    }

}
