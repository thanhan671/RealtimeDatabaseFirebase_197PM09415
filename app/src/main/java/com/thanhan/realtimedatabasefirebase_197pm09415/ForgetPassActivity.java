package com.thanhan.realtimedatabasefirebase_197pm09415;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassActivity extends AppCompatActivity {

    EditText email;
    TextView error;
    Button resetpass, cancel;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        matching();
        auth = FirebaseAuth.getInstance();

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = email.getText().toString().trim();
                if (TextUtils.isEmpty(semail)){
                    error.setText("Vui lòng nhập email!");
                    return;
                }
                auth.sendPasswordResetEmail(semail).addOnCompleteListener(ForgetPassActivity.this,
                        new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            error.setText(task.getException().toString());
                        }else {
                            Toast.makeText(getApplicationContext(), "Đã gửi email cho bạn!",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ForgetPassActivity.this,MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
    }
    private void matching() {
        email = (EditText) findViewById(R.id.forget_et_email);
        resetpass = (Button) findViewById(R.id.forget_btn_getpass) ;
        cancel = (Button) findViewById(R.id.forget_btn_cancel);
        error = (TextView) findViewById(R.id.forget_tv_error);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPassActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}