package com.uni.plovdiv.hapnitopni;


import static java.sql.DriverManager.println;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.uni.plovdiv.hapnitopni.Session.SessionManager;

import com.uni.plovdiv.hapnitopni.activities.StartActivity;
import com.uni.plovdiv.hapnitopni.databinding.ActivityMainBinding;

import com.uni.plovdiv.hapnitopni.entities.Products;
import com.uni.plovdiv.hapnitopni.entities.Locations;
import com.uni.plovdiv.hapnitopni.repository.MyDBHandler;


import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.ToDoubleBiFunction;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    MyDBHandler myDbHandler;
    Button exitButton;
    TextView currentDateTV;
    TextView emailForHeaderTV;
    TextView nameForHeaderTV;



    String currentDate ;

    //use this to get user_id from the login activity
    SessionManager session;

    //this should be the retrieved data from db
    String[] emailFromDB;
    String[] nameFromDB;

    int current_user_id;




    List<Products> products = new ArrayList<Products>();
    List<Locations> locations = new ArrayList<Locations>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //取得資源識別碼的方法，
        int resID_1 = getResId("brownsugar_bubble_boob", R.drawable.class); // or other resource class
        //println(Integer.toString(resID_1));
        int resID_2 = getResId("bubble_greenbean_milk", R.drawable.class);
        int resID_3 = getResId("bubble_milktea", R.drawable.class);
        int resID_4 = getResId("bubble_peanut_milkice", R.drawable.class);
        int resID_5 = getResId("carambola_juice", R.drawable.class);
        int resID_6 = getResId("dajia_taro", R.drawable.class);
        int resID_7 = getResId("fourgreentea", R.drawable.class);
        int resID_8 = getResId("honey_greentea", R.drawable.class);
        int resID_9 = getResId("lemon_carambola", R.drawable.class);
        int resID_10 = getResId("manor_cocoa_milk", R.drawable.class);
        int resID_11 = getResId("matcha_latte", R.drawable.class);
        int resID_12 = getResId("lemon_ice", R.drawable.class);
        int resID_13 = getResId("passion_greentea", R.drawable.class);
        int resID_14 = getResId("wintermelon_boob", R.drawable.class);
        //not the best way to do it but still works :)
        //can be optimized!!!
        //創建Products 物件，先定義Produce類別  entities/Produce
        Products pr1 = new Products(resID_1,"黑糖珍珠撞奶","天氣好冷該喝什麼好呢？❄️冬季飲品首選『黑糖珍珠撞奶』","55");
        Products pr2 = new Products(resID_2,"珍珠綠豆沙牛奶","新鮮檸檬·酸甜滋味·清爽沁涼\n" + "酸酸甜甜～夏天來一杯清涼解膩","60");
        Products pr3 = new Products(resID_3,"珍珠奶茶","戀愛可以慢慢談，奶茶必須馬上喝(*¯︶¯*)","50");
        Products pr4 = new Products(resID_4,"珍珠花生牛奶冰沙","濃郁花生冰沙尬濃醇鮮奶簡直絕配❤️\n" + "再搭配Q彈黑糖珍珠～一喝就愛上‼️\n" + "夏日快來杯花生冰沙～好事會花生","65");
        Products pr5 = new Products(resID_5,"楊桃汁","春夏喝一杯多冰的楊桃汁消暑解渴\n" + "秋冬務必喝一杯熱楊桃汁止咳潤肺","40");
        Products pr6 = new Products(resID_6,"大甲芋頭","嚴選在地大甲芋頭！\n" + "吃的到微微芋頭顆粒～濃郁香氣與綿密的口感讓人無法抵抗","60");
        Products pr7 = new Products(resID_7,"四季青茶","","30");
        Products pr8 = new Products(resID_8,"蜂蜜綠茶","人生短短幾個秋～不醉不罷休","40");
        Products pr9 = new Products(resID_9,"檸檬楊桃汁","獨特檸檬冰沙搭配楊桃汁，酸酸甜甜～消暑解渴\n" + "特調飲品首選，清涼～夠爽.ᐟ.ᐟ.ᐟ","40");
        Products pr10 = new Products(resID_10,"莊園可可奶","喝杯可可奶☕️能量滿滿的一天⚡️\n","55");
        Products pr11 = new Products(resID_11,"抹茶拿鐵","","55");
        Products pr12 = new Products(resID_12,"檸檬冰沙","新鮮檸檬·酸甜滋味·清爽沁涼\n" + "酸酸甜甜～夏天來一杯清涼解膩","40");
        Products pr13 = new Products(resID_13,"百香果綠茶","使用100%百香果原汁\n" + "酸酸甜甜回購率爆表\n" + "愛喝酸的冰友絕對不能錯過‼️","50");
        Products pr14 = new Products(resID_14,"冬瓜撞奶","","45");

        //now try with one more to be sure - adding to database


        //添加Produce在list中
        products.add(pr1);products.add(pr2);products.add(pr3);products.add(pr4);products.add(pr5);products.add(pr6);products.add(pr7);
        products.add(pr8);products.add(pr9);products.add(pr10);products.add(pr11);products.add(pr12);products.add(pr13);products.add(pr14);


        int resID_a = getResId("icon", R.drawable.class);
        Log.d("ResID", "icon Res ID: " + resID_a);
        Locations l1 = new Locations(resID_a,"楠梓創新店","811高雄市楠梓區創新路885號","");
        Locations l2 = new Locations(resID_a,"岡山前峰店","820高雄市岡山區園西路二段145號","");
        Locations l3 = new Locations(resID_a,"瑞豐店 ","813高雄市左營區裕誠路1096號第9排","");
        Locations l4 = new Locations(resID_a,"鳳山文化店 ","830高雄市鳳山區文化西路20號","");
        Locations l5 = new Locations(resID_a,"三民熱河店","807高雄市三民區熱河一街98號","");
        Locations l6 = new Locations(resID_a,"育樂店","806高雄市前鎮區育樂路69號","");
        Locations l7 = new Locations(resID_a,"大民店","815高雄市大社區三民路213號","");
        Locations l8 = new Locations(resID_a,"小港漢民店","812高雄市小港區漢民路129號","");
        Locations l9 = new Locations(resID_a,"中庄店","831高雄市大寮區鳳屏一路426號","");
        Locations l10 = new Locations(resID_a,"大寮輔英店","831高雄市大寮區進學路187號","");
        Locations l11 = new Locations(resID_a,"林園店","832高雄市林園區東林東路13號","");
        locations.add(l2);locations.add(l3);locations.add(l4);locations.add(l5);locations.add(l6);locations.add(l7);
        locations.add(l8);locations.add(l9);locations.add(l10);locations.add(l11);

        //inflate 方法會將 XML(ActivityMainBinding)(大小寫不影響) 佈局檔案轉換為對應的 View 物件
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //"設定"活動的內容視圖，setContentView 方法需要一個 View 物件(ActivityMainBinding)作為參數
        // ActivityMainBinding 側拉式選單的頁面
        // todo： getRoot了解使用方式
        setContentView(binding.getRoot());

        // todo 不懂下面會畫作用
        session = new SessionManager(this);

        myDbHandler = new MyDBHandler(this, null,null, 1);
        currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        current_user_id = session.getSession();


        emailFromDB = myDbHandler.getUserEmail(String.valueOf(current_user_id));
        nameFromDB = myDbHandler.getUserName(String.valueOf(current_user_id));


        //if I have already product with that name i dont want to add in the database
        for(Products x : products){
            if (myDbHandler.checkProductExist(x) !=true){
                myDbHandler.addProduct(x);
            }
        }
        //
        for(Locations y : locations){
            if (myDbHandler.checkLocationExist(y) !=true){
                myDbHandler.addLocation(y);
            }
        }
        // Activity 中的一種方法，設定為應用欄
        setSupportActionBar(binding.appBarMain.toolbar);

        // 旁邊拉出容器，抽屜，將其儲存在drawer容器中
        DrawerLayout drawer = binding.drawerLayout;
        // 導航列，可以放置文件
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //設定 側拉選單的config
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                //drawer 上方DrawerLayout 的引用。
                //setOpenableLayout(drawer) 方法将 DrawerLayout 设置为可打开的布局
                //etOpenableLayout設置為可打開的布局
                R.id.nav_home, R.id.nav_menu, R.id.nav_favourite,R.id.nav_location, R.id.nav_edit_user)
                .setOpenableLayout(drawer)
                .build();

        //Navigation Component 來設定導航的一種方式
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //here i initialize the optionMenu button- exit
        exitButton = findViewById(R.id.exitButton);

        emailForHeaderTV = findViewById(R.id.emailFromCurrentLoginTV);
        currentDateTV = findViewById(R.id.currentDateFull);
        nameForHeaderTV = findViewById(R.id.fullNameFromCurrentLoginTV);

        currentDateTV.setText(currentDate);
        emailForHeaderTV.setText(emailFromDB[0]);
        nameForHeaderTV.setText(nameFromDB[0]);





        //and set to go from one activity to start
        //and with that i reset the current state of program
        //按鈕監聽器
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });
        return true;
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    //this method retrieve int id from the resource and it is generic
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }





}