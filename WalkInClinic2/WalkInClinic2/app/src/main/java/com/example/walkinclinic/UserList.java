package com.example.walkinclinic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UserList extends AppCompatActivity {
    ListView userlist;
    EditText username;
    Button deleteuser;
    DatabaseSQLiteHelper db;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        username = (EditText) findViewById(R.id.usernameview);
        deleteuser = (Button) findViewById(R.id.btn_userDelete);
        userlist = (ListView) findViewById(R.id.listviewuser);
        db = new DatabaseSQLiteHelper(this);

        ArrayList<User> list = db.getAllUsers();

        //Just for Testing list of users
        for (int i = 0; i < list.size(); i++) {
            System.out.println("USER");
            System.out.println(list.get(i).getRole());
        }

        UserListAdapter adapter = new UserListAdapter(this, R.layout.userlist, list, this);
        userlist.setAdapter(adapter);

        deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = db.deleteUser(username.getText().toString());
                if (result) {
                    toastMessage("User Removed");
                    username.setText("");
                    finish();
                    startActivity(getIntent());
                } else
                    toastMessage("No Match Found");
            }
        });
    }

    public void toastMessage(String x){
        Toast.makeText(this,x, Toast.LENGTH_SHORT).show();
    }
}
