package com.thanhan.realtimedatabasefirebase_197pm09415;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CapNhatContactActivity extends AppCompatActivity {
    Button update, delete, cancel;
    EditText id, name, email, gender, address, home, mobile, office;
    String i_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_contact);

        matching();

        getContactDetail();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("contacts");
                reference.child(String.valueOf(i_id)).removeValue();
                Toast.makeText(getApplicationContext(),"Xóa thành công!",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                    Toast.makeText(getApplicationContext(), "Sửa thành công contact "
                            +sid, Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception e){
                    Log.d("error update", e.toString());
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getContactDetail() {
        Intent intent = getIntent();
        i_id = intent.getStringExtra("ID");
        id.setEnabled(false);
        id.setText(String.valueOf(i_id));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("contacts");
        reference.child(String.valueOf(i_id)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    name.setText(hashMap.get("name").toString());
                    address.setText(hashMap.get("address").toString());
                    gender.setText(hashMap.get("gender").toString());
                    email.setText(hashMap.get("email").toString());
                    home.setText(hashMap.get("home").toString());
                    mobile.setText(hashMap.get("mobile").toString());
                    office.setText(hashMap.get("office").toString());
                } catch (Exception e) {
                    Log.d("Loi_json",e.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Loi_detail", databaseError.toString());
            }
        });
    }

    private void matching() {
        delete = (Button) findViewById(R.id.btn_Delete);
        update = (Button) findViewById(R.id.btn_Update);
        cancel = (Button) findViewById(R.id.btn_Cancel);
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