package com.thanhan.realtimedatabasefirebase_197pm09415;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThemContactActivity extends AppCompatActivity {

    Button add, cancel;
    EditText id, name, email, gender, address, home, mobile, office;
    Integer i_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_contact);

        matching();

        Intent intent = getIntent();
        i_id = intent.getIntExtra("ID", -1);
        id.setEnabled(false);
        id.setText(String.valueOf(i_id));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulythemmoi();
            }
        });
    }

    private void xulythemmoi() {
        try {
            String sid = id.getText().toString().trim();
            String sname = name.getText().toString().trim();
            String semail = email.getText().toString().trim();
            String sgender = gender.getText().toString().trim();
            String saddress = address.getText().toString().trim();
            String shome = home.getText().toString().trim();
            String smobile = mobile.getText().toString().trim();
            String soffice = office.getText().toString().trim();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("contacts");

            reference.child(sid).child("name").setValue(sname);
            reference.child(sid).child("email").setValue(semail);
            reference.child(sid).child("address").setValue(saddress);
            reference.child(sid).child("gender").setValue(sgender);
            reference.child(sid).child("mobile").setValue(smobile);
            reference.child(sid).child("home").setValue(shome);
            reference.child(sid).child("office").setValue(soffice);

            Toast.makeText(getApplicationContext(), "Thêm thành công "
                    +sid, Toast.LENGTH_LONG).show();
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void matching() {
        add = (Button) findViewById(R.id.btn_add);
        cancel = (Button) findViewById(R.id.btn_cancel);
        id = (EditText) findViewById(R.id.et_id);
        name = (EditText) findViewById(R.id.et_name);
        email = (EditText) findViewById(R.id.et_email);
        gender = (EditText) findViewById(R.id.et_gender);
        address = (EditText) findViewById(R.id.et_address);
        home = (EditText) findViewById(R.id.et_home);
        mobile = (EditText) findViewById(R.id.et_mobile);
        office = (EditText) findViewById(R.id.et_office);
    }
}