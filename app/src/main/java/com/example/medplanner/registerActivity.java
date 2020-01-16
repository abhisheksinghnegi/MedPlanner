package com.example.medplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

public class registerActivity extends AppCompatActivity {

    String name, phonenum, passcheck, passgive, mail;

    EditText edt_name;
    EditText email_id;
    TextInputLayout t2;
    TextInputLayout t3;
    TextInputLayout t4;
    Boolean mail_correct = false;
    TextInputLayout t5;
    TextInputLayout t1;
    boolean mailtrue = false;
    EditText phone_num;
    EditText pass1;
    EditText pass2;
    CheckBox term_cond;

    Mydbhelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mydb = new Mydbhelper(registerActivity.this);
        System.out.println("on crested");
        edt_name = findViewById(R.id.name_edit);
        email_id = findViewById(R.id.email);
        phone_num = findViewById(R.id.phone);
        pass1 = findViewById(R.id.pass_give);
        pass2 = findViewById(R.id.pass_check);
        term_cond = findViewById(R.id.t_and_c);
        t2 = findViewById(R.id.layout2);


        email_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = email_id.getText().toString().trim();
                if (str.indexOf('@') < 0 || (str.indexOf('.') < 0)) {
                    t2.setError("Enter correct mail");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = email_id.getText().toString().trim();
                if ((str.indexOf('@') > 0) & (str.indexOf('.') > 0)) {
                    t2.setError(null);
                    mail_correct = true;
                }
            }

        });
        edt_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = edt_name.getText().toString();
                if (name.length() != 0) {

                    t1 = findViewById(R.id.layout1);
                    t1.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        phone_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phonenum = phone_num.getText().toString();
                if (phonenum.length() != 0) {
                    TextInputLayout t3 = findViewById(R.id.layout3);
                    t3.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pass1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passgive = pass1.getText().toString();
                if (passgive.length() != 0) {
                    TextInputLayout t4 = findViewById(R.id.layout4);
                    t4.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pass2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passcheck = pass2.getText().toString();
                if (passcheck.length() != 0) {
                    TextInputLayout t5 = findViewById(R.id.layout5);
                    t5.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Button btn = findViewById(R.id.btn_submit_register);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edt_name.getText().toString();

                phonenum = phone_num.getText().toString();
                passgive = pass1.getText().toString();
                passcheck = pass2.getText().toString();
                mail = email_id.getText().toString();


                Intent i = new Intent(registerActivity.this, loginActivity.class);
//                Toast.makeText(registerActivity.this, name,Toast.LENGTH_SHORT).show();
                if ((name.length() == 0) || (phonenum.length() == 0) || (passgive.length() == 0) || (passcheck.length() == 0) || (mail.length() == 0) || !mail_correct) {
                    if (mail.length() != 0)
                        mailtrue = true;
                    if (name.length() == 0) {

                        t1 = findViewById(R.id.layout1);
                        t1.setError("enter a name");
                    }
                    if (!mailtrue) {
                        t2 = findViewById(R.id.layout2);
                        t2.setError("Enter mail");
                    }
                    if (!mail_correct) {
                        t2 = findViewById(R.id.layout2);
                        t2.setError("Incorrect Email");
                    }

                    if (phonenum.length() == 0) {
                        TextInputLayout t3 = findViewById(R.id.layout3);
                        t3.setError("enter valid phone number");
                    }

                    if (passgive.length() == 0) {
                        TextInputLayout t4 = findViewById(R.id.layout4);
                        t4.setError("enter password");
                    }


                    if (passcheck.length() == 0) {
                        TextInputLayout t5 = findViewById(R.id.layout5);
                        t5.setError("re-enter password");
                    }


                } else if (!term_cond.isChecked()) {
                    Toast.makeText(registerActivity.this, "Please read terms and condition", Toast.LENGTH_SHORT).show();
                } else if (!(passcheck.equals(passgive))) {
                    Toast.makeText(registerActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                } else {


                    //using shared prefernces
                    /*SharedPreferences sh = getSharedPreferences("UserPass", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putString("username", mail);
                    editor.putString("password", passgive);
                    editor.apply();
*/

                    // using sqlite

                    mydb.insertdata(name, mail, phonenum, passcheck);
                    if (!mydb.insertdata(name, mail, phonenum, passcheck)) {
                        Toast.makeText(registerActivity.this, "ID already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(registerActivity.this, "ID CREATED", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        finish();

                    }


                }
            }
        });
    }
}
