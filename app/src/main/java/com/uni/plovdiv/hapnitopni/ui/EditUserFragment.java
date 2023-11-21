package com.uni.plovdiv.hapnitopni.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uni.plovdiv.hapnitopni.R;
import com.uni.plovdiv.hapnitopni.Session.SessionManager;
import com.uni.plovdiv.hapnitopni.activities.LoginActivity;
import com.uni.plovdiv.hapnitopni.activities.StartActivity;
import com.uni.plovdiv.hapnitopni.repository.MyDBHandler;

import java.util.regex.Pattern;


public class EditUserFragment extends Fragment {


    MyDBHandler myDbHandler;
    SessionManager session;

    TextView currentName;
    TextView currentEmail;
    TextView currentPassword;

    EditText editName;
    EditText editEmail;
    EditText editPassword;

    Button editButton;
    Button deleteButton;

    String regexPattern;

    int current_user_id;


    //Email Validation Regex pattern
    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        session = new SessionManager(getContext());
        current_user_id = session.getSession();
        myDbHandler = new MyDBHandler(getContext(),null,null,1);




        return inflater.inflate(R.layout.fragment_edit_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        currentName = view.findViewById(R.id.currentName);
        currentEmail = view.findViewById(R.id.currentEmail);
        currentPassword = view.findViewById(R.id.currentPassword);
        deleteButton = view.findViewById(R.id.deleteButton);
        editButton = view.findViewById(R.id.editButton);
        editName = view.findViewById(R.id.personNameET);
        editEmail =view.findViewById(R.id.emailET);
        editPassword = view.findViewById(R.id.passwordET);
        regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";


        String[] nameFromDB = myDbHandler.getUserName(String.valueOf(current_user_id));
        String[] emailFromDB = myDbHandler.getUserEmail(String.valueOf(current_user_id));
        String[] passwordFromDB = myDbHandler.getUserPassword(String.valueOf(current_user_id));

        //using this for creating a field with number of * stars from passwordCount symbols
        String passwordSequence = passwordFromDB[0];
        int passwordCount = passwordSequence.length();
        String stars = "";
        for (int i = 0; i<passwordCount; i++ ){
            stars += "*";
        }



        currentName.setText("你的名字：" + nameFromDB[0]);
        currentEmail.setText("你的郵件：" + emailFromDB[0]);
        currentPassword.setText("你的密碼：" + stars);



        super.onViewCreated(view, savedInstanceState);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDbHandler.deleteUser(current_user_id);
                Toast.makeText(getActivity(), "帳號刪除成功！", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), StartActivity.class));
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //working with first variation and the second one
                String newName = String.valueOf(editName.getText());
                String newEmail = editEmail.getText().toString();
                String newPassword = editPassword.getText().toString();
                Boolean checkEmail = false;

                if(newPassword.equals("")&&newName.equals("")&&newEmail.equals("")){
                    Toast.makeText(getContext(),
                            "您還沒有輸入任何要更改的資料！", Toast.LENGTH_SHORT).show();
                }else{
                    if(newName.equals("")){
                        newName = nameFromDB[0];
                    }
                    if (newEmail.equals("")){
                        newEmail = emailFromDB[0];
                    }else{
                        checkEmail = (patternMatches(newEmail, regexPattern));
                    }
                    if(newPassword.equals("")){
                        newPassword = passwordFromDB[0];
                    }

                    if(checkEmail){
                        myDbHandler.editUser(current_user_id, newName, newEmail, newPassword);
                        Toast.makeText(getActivity(),
                                "編輯成功！ 請使用更改後的詳細資訊登入！",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }else{
                        Toast.makeText(getActivity(),
                                "輸入錯誤！\n請再試一次！",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
}