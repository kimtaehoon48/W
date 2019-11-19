package com.example.djwrk.myword;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.PopupWindow;
import java.util.ArrayList;
import java.util.List;

public class MyWord extends Activity {
    //MyWord 버튼

    Button mf;
    Button plus;
    Button rm;


    //popup
    private PopupWindow mPopupWindow;
    private EditText menuEdit;
    private EditText wordEdit;
    private EditText meanEdit;
    private Button btnAdd;
    private Button btnClose;
    private Button menuAdd;
    private Button wordAdd;
    private Button addClose;
    private CustomAdapter adapter;
    private Button addMenu;

    private ListView list;
    private PopupWindow PopupWindow;
    ArrayList<MyDataList> engData;
    View popupView;
    boolean manuNameFlag = false;
    private Button engSave;

    //종료 popup
    private PopupWindow pop;

    //data
    String data = "";

    //viewPager
    private ViewPager pager;
    private PagerItem mPagerAdapter;
    ArrayList<String> word;
    ArrayList<String> mean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);



        mf = (Button) findViewById(R.id.mf);
        plus = (Button) findViewById(R.id.plus);
        rm = (Button) findViewById(R.id.rm);
        list = (ListView) findViewById(R.id.list);
        engSave = (Button) findViewById(R.id.engSave);

        engData = new ArrayList<>();
        engData = MyValue.getEng(getApplicationContext());
        adapter = new CustomAdapter(getApplicationContext(),engData,list);

        list.setAdapter(adapter);

        final List<String> data = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //화면 계속 켜짐
        viewPagerInit(); // ViewPager 할당
        addMenu = (Button)findViewById(R.id.addMenu);

        addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupInit();
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupInit();
            }
        });

        engSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyValue.saveEng(getApplicationContext(),engData);
                Toast.makeText(getApplicationContext(),"저장완료",Toast.LENGTH_SHORT).show();
            }
        });


           // back.setOnClickListener(new View.OnClickListener() {
           //     @Override
            //    public void onClick(View view) {
           //         data = MyValue.value;
           //         viewPagerInit();
             //       list.setAdapter(adapter);
            //    }
          //  });

}
    @Override
    public void onBackPressed() {

        if(pop!=null && pop.isShowing())
        {
            pop.dismiss();
        }
        View popView = getLayoutInflater().inflate(R.layout.popup_finish, null);
        pop = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
        pop.showAtLocation(popView, Gravity.CENTER, 0, 0);

        Button cancel = (Button)popView.findViewById(R.id.popupCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });

        final Button fini = (Button)popView.findViewById(R.id.popupFinish);
        fini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


    //문자열 분해
    private void getWordText(String temp) {
        String t = "";
        for (int i = 0; i < temp.length(); i++) {
            if (!String.valueOf('|').equals(String.valueOf(temp.charAt(i)))
                    && !String.valueOf('/').equals(String.valueOf(temp.charAt(i))))
                t += temp.charAt(i);

            else if (String.valueOf('|').equals(String.valueOf(temp.charAt(i)))) {
                word.add(t);
                t = "";
            } else if (String.valueOf('/').equals(String.valueOf(temp.charAt(i)))) {
                mean.add(t);
                t = "";
            }
        }
    }

    void popupInit()
    {
        manuNameFlag = false;
        popupView = getLayoutInflater().inflate(R.layout.activity_add, null);
        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //popupView 에서 (LinearLayout 을 사용) 레이아웃이 둘러싸고 있는 컨텐츠의 크기 만큼 팝업 크기를 지정
        mPopupWindow.setFocusable(true);

        mPopupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);



        menuAdd = (Button)popupView.findViewById(R.id.addMenuBtn);
        menuEdit = (EditText)popupView.findViewById(R.id.addMenuName);
        wordEdit = (EditText)popupView.findViewById(R.id.addWord);
        meanEdit = (EditText)popupView.findViewById(R.id.addMean);
        wordAdd = (Button)popupView.findViewById(R.id.addBtn);
        addClose = (Button)popupView.findViewById(R.id.addClose);




        //메뉴 이름 지정하기
        menuAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!menuEdit.getText().toString().equals(""))
                {
                    MyValue.saveTempMenu=menuEdit.getText().toString();
                    manuNameFlag = true;
                    Toast.makeText(getApplicationContext(),MyValue.saveTempMenu,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"값을 입력하세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //추가하기 버튼
        wordAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!wordEdit.getText().toString().equals("") && !meanEdit.getText().toString().equals(""))
                {
                    MyValue.saveTempContent+=wordEdit.getText().toString()+"|"+
                            meanEdit.getText().toString()+"/";
                    wordEdit.setText("");
                    meanEdit.setText("");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"값을 모두 입력하세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //닫기버튼 -- 임시값 저장
        addClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manuNameFlag ==false)
                {
                    Toast.makeText(getApplicationContext(), "설정 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(wordEdit.getText().toString().equals("") && meanEdit.getText().toString().equals("")) {
                    MyDataList temp = new MyDataList(MyValue.saveTempMenu, MyValue.saveTempContent);
                    adapterAddItem(temp);
                    MyValue.saveTempMenu = "";
                    MyValue.saveTempContent = "";

                    if (mPopupWindow.isShowing())
                        mPopupWindow.dismiss();
                    Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "추가하기를 눌러주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //뷰 페이저 초기화
    void viewPagerInit()
    {
        word = new ArrayList<>();
        mean = new ArrayList<>();
        getWordText(data);
        if(word.size() != mean.size())
        {
            word.clear();
            mean.clear();
        }
        pager = (ViewPager)findViewById(R.id.viewpager);
        mPagerAdapter = new PagerItem(getApplicationContext(),word,mean);
        pager.setAdapter(mPagerAdapter);
    }

    void adapterAddItem(MyDataList temp)
    {
        adapter.add(temp);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
    }
}



