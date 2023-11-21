package com.uni.plovdiv.hapnitopni;


import static java.sql.DriverManager.println;

import android.content.Intent;

import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //取得資源識別碼的方法，
        int resID_1 = getResId("pizza", R.drawable.class); // or other resource class
        //println(Integer.toString(resID_1));
        int resID_2 = getResId("fish", R.drawable.class);
        int resID_3 = getResId("sushi", R.drawable.class);
        int resID_4 = getResId("salad", R.drawable.class);
        int resID_5 = getResId("pasta", R.drawable.class);
        int resID_6 = getResId("cake", R.drawable.class);
        int resID_7 = getResId("cocktail", R.drawable.class);
        //not the best way to do it but still works :)
        //can be optimized!!!
        //創建Products 物件，先定義Produce類別  entities/Produce
        Products pr1 = new Products(resID_1,"披薩","在烤箱中烘烤","12.90");
        Products pr2 = new Products(resID_2,"魚","放在盤子裡烤","15.20");
        Products pr3 = new Products(resID_3,"壽司","配芝麻和鮭魚","20.40");
        Products pr4 = new Products(resID_4,"沙拉","用愛製造","10.55");
        Products pr5 = new Products(resID_5,"義大利麵","博洛尼亞肉醬有兩種肉","14.10");
        Products pr6 = new Products(resID_6,"甜點","自製","4.30");
        Products pr7 = new Products(resID_7,"雞尾酒","杜松子酒和補品","8.30");

        //now try with one more to be sure - adding to database


        //添加Produce在list中
        products.add(pr1);
        products.add(pr2);
        products.add(pr3);
        products.add(pr4);
        products.add(pr5);
        products.add(pr6);
        products.add(pr7);

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