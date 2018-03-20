package com.example.jeonwon.gaza;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;
    private Context context;
<<<<<<< HEAD
    private ListViewSchedule mActivity;

    private TextView spentMoney;

    public int totalSpentMoney=0;
    public int beforeSpentMoney=0;

    // ListViewAdapter의 생성자
    public ListViewAdapter(ListViewSchedule activity) {
        mActivity = activity;
=======
    private TextView spentMoney;
    public int totalSpentMoney=0;
    public int beforeSpentMoney=0;

    // ListViewAdapter의 생성자
    public ListViewAdapter(MainActivity activity) {
        MainActivity mActivity = activity;
>>>>>>> e44c25e6835dee9ff47cf71cd1b8c965ddbdc0d9
        spentMoney =mActivity.spentMoney;
    }
    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
         context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }
        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) convertView.findViewById(R.id.title) ;
        TextView timeTextView = (TextView) convertView.findViewById(R.id.time) ;
        TextView budgetTextView = (TextView) convertView.findViewById(R.id.budget) ;
        final TextView spentMoenyEditView = (EditText) convertView.findViewById(R.id.spentmoney) ;


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.getTitle());
        timeTextView.setText(listViewItem.gettime());
        budgetTextView.setText(listViewItem.getBudget());
//        spentMoenyEditView.setText(listViewItem.getSpentMoney());

        titleTextView.setOnClickListener(new detail_form());
        timeTextView.setOnClickListener(new detail_form());
        budgetTextView.setOnClickListener(new detail_form());
<<<<<<< HEAD

       /*  spentMoenyEditView.addTextChangedListener(new TextWatcher() {
=======
        spentMoenyEditView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText a =(EditText)v;
                if (hasFocus == false){
                    Toast.makeText(context, Integer.toString(a.getId()), Toast.LENGTH_SHORT).show();
/*
                    if(!a.getText().toString().equals("")){
                        Toast.makeText(context, a.getText().toString(), Toast.LENGTH_SHORT).show();

                        totalSpentMoney += Integer.parseInt(a.getText().toString());
                       //Toast.makeText(context,Integer.toString( totalSpentMoney), Toast.LENGTH_SHORT).show();

                        spentMoney.setText(Integer.toString( totalSpentMoney));
                    }*/
                    try {

                        Toast.makeText(context, "잃음", Toast.LENGTH_SHORT).show();
                        totalSpentMoney = totalSpentMoney + Integer.parseInt(a.getText().toString());
                        spentMoney.setText(Integer.toString(totalSpentMoney));
                        Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                        v.clearFocus();
                    }
                    catch (Exception e){Toast.makeText(context, "에러", Toast.LENGTH_SHORT).show();}
                }
                else{
                    Toast.makeText(context, "얻음", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();}
            }
        });
        /* spentMoenyEditView.addTextChangedListener(new TextWatcher() {
>>>>>>> e44c25e6835dee9ff47cf71cd1b8c965ddbdc0d9
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                totalSpentMoney += Integer.parseInt(spentMoenyEditView.getText().toString());
                spentMoney.setText(Integer.toString( totalSpentMoney));
                Toast.makeText(context,"dds",Toast.LENGTH_SHORT).show();
<<<<<<< HEAD
=======


>>>>>>> e44c25e6835dee9ff47cf71cd1b8c965ddbdc0d9
            }
        });*/


        return convertView;
    }
    class detail_form implements View.OnClickListener{
        public void onClick(View v){
            Toast.makeText(context, "세부창", Toast.LENGTH_SHORT).show();
<<<<<<< HEAD
            Intent adintent =  mActivity.intent;
            context.startActivity(adintent);
=======
>>>>>>> e44c25e6835dee9ff47cf71cd1b8c965ddbdc0d9
        }
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String title, String desc, String budget, String spentMoney) {
        ListViewItem item = new ListViewItem();

        item.setTitle(title);
        item.settime(desc);
        item.setBudget(budget);
        item.setSpentMoney(spentMoney);

        listViewItemList.add(item);
    }
}
