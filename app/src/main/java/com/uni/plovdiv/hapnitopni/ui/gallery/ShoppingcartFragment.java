package com.uni.plovdiv.hapnitopni.ui.gallery;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uni.plovdiv.hapnitopni.MainActivity;
import com.uni.plovdiv.hapnitopni.R;
import com.uni.plovdiv.hapnitopni.adapters.LocationAdapter;
import com.uni.plovdiv.hapnitopni.adapters.OrderAdapter;
import com.uni.plovdiv.hapnitopni.entities.Locations;
import com.uni.plovdiv.hapnitopni.entities.Orders;
import com.uni.plovdiv.hapnitopni.repository.MyDBHandler;

import java.util.ArrayList;

public class ShoppingcartFragment extends Fragment  {

    private ArrayList<Orders> ordersArrayList;
    private RecyclerView recycleview;
    MyDBHandler myDbHandler;
    Orders order;
    Button checkout;
    private ArrayList<Orders> orders;
    private Cursor maincursor; // 記錄目前資料庫查詢指標
    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;
    private ListView listView = null;
    Button toGMap;
    private MyDBHandler dbHelper;
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //productsInfo = myDbHandler.allProducts();
        View root = inflater.inflate(R.layout.shoppinrcart_fragment, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        checkout = view.findViewById(R.id.checkoutButton);
        fab = view.findViewById(R.id.fabToMenu);


        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_nav_order_to_nav_menu);

            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myDbHandler = new MyDBHandler(getContext(), null,null, 1);
                // 创建并显示 AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("送出餐點");
                builder.setMessage("確定將購物車內的餐點送出結帳嗎?");
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDbHandler.clearOrderData();
                        Toast.makeText(getContext(), "餐點已送出!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        // 更新数据源
                        orders.clear();
                        ordersArrayList.clear();
                        ordersArrayList = myDbHandler.allOrders();
                        dataInitialize();
                        //refreshListView();

                        sendNotification();

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        //createNotificationChannel();
        super.onViewCreated(view, savedInstanceState);

        orders = new ArrayList<>();

        myDbHandler = new MyDBHandler(getContext(),null,null,1);
        ordersArrayList = myDbHandler.allOrders();
        dataInitialize();

        recycleview = view.findViewById(R.id.recycleView);
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleview.setHasFixedSize(true);

        OrderAdapter orderAdapter = new OrderAdapter(getContext(), orders);
        recycleview.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();
    }

    private void dataInitialize() {

        for (int i = 0; i<ordersArrayList.size(); i++){

             order = new Orders(ordersArrayList.get(i).getImage(),
                     ordersArrayList.get(i).getName(),
                     ordersArrayList.get(i).getDescription(),
                     ordersArrayList.get(i).getPrice(),
                     ordersArrayList.get(i).getQuantity());

            orders.add(order);
        }

    }
    private void sendNotification() {
        // 建立一個通知 Channel
        String channelId = "channel_id";
        String channelName = "Channel Name";
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // 設定通知的內容
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), channelId)
                .setSmallIcon(R.drawable.ic_baseline_restaurant_menu_24)
                .setContentTitle("訂單已送出")
                .setContentText("您的訂單已成功送出")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // 建立通知點擊後的操作
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);

        // 發送通知
        notificationManager.notify(0, builder.build());
    }


}