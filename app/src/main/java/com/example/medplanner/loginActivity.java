package com.example.medplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    EditText mail;
    EditText pass;
    TextView text;
    Button button_login;
    Mydbhelper db=new Mydbhelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView reg_pl = findViewById(R.id.register_id);
        reg_pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(loginActivity.this, registerActivity.class);
                startActivity(i1);

            }
        });


        mail = findViewById(R.id.login_mail);
        pass = findViewById(R.id.login_password);
        button_login = findViewById(R.id.login_button);
        text = findViewById(R.id.txt);


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("UserPass", MODE_PRIVATE);
//                String s1 = sp.getString("username", "N/A");
//                String s2 = sp.getString("password", "N/A");





                /*if (mail.getText().toString().equals(s1) && pass.getText().toString().equals(s2)) {
                    SharedPreferences sp2;
                    sp2 = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp2.edit();
                    editor.putString("is_login", "1");
                    editor.apply();


                    Intent i2 = new Intent(loginActivity.this, HomeScreen.class);
                    startActivity(i2);
                    finish();

                }*/

                if (db.check(mail.getText().toString(),pass.getText().toString()))
                {
                    SharedPreferences sp2;
                    sp2 = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp2.edit();
                    editor.putString("is_login", "1");
                    editor.apply();


                    Intent i2 = new Intent(loginActivity.this, Primary_Screen.class);
                    startActivity(i2);
                    finish();
                }

                else {
                    Toast.makeText(loginActivity.this, "wrong match", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
