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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button signup,signin,forget,cancel;
    TextView error;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        matching();
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            Toast.makeText(getApplicationContext(), "Đã đăng nhập thành công!",Toast.LENGTH_LONG).show();
            finish();
        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail = email.getText().toString().trim();
                String spassword = password.getText().toString().trim();
                if (TextUtils.isEmpty(semail)){
                    error.setText("Vui lòng nhập email!");
                    return;
                }
                if (TextUtils.isEmpty(spassword)){
                    error.setText("Vui lòng nhập mật khẩu!");
                    return;
                }
                if (spassword.length()<6){
                    error.setText("Vui lòng nhập mật khẩu từ 6 kí tự!");
                    return;
                }
                auth.signInWithEmailAndPassword(semail,spassword).addOnCompleteListener(LoginActivity.this,
                        new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            error.setText(task.getException().toString());
                        }else {
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công!",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgetPassActivity.class));
                finish();
            }
        });
    }
    private void matching() {
        email = (EditText) findViewById(R.id.login_et_email);
        password = (EditText) findViewById(R.id.login_et_pass);
        signup = (Button) findViewById(R.id.login_btn_register);
        signin = (Button) findViewById(R.id.login_btn_signin);
        forget = (Button) findViewById(R.id.login_btn_forgetpass);
        cancel = (Button) findViewById(R.id.login_btn_cancel);
        error = (TextView) findViewById(R.id.login_tv_error);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}