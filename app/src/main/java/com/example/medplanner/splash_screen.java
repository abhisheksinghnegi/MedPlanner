package com.example.medplanner;
import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.os.Handler;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sh = getSharedPreferences("login", MODE_PRIVATE);
                String isLogin = sh.getString("is_login", "N/A");
                if (isLogin.equals("1")) {
                    Intent i1 = new Intent(splash_screen.this, Primary_Screen.class);
                    startActivity(i1);
                    finish();
                } else {
                    Intent i = new Intent(splash_screen.this, loginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        },5000);
    }
}
