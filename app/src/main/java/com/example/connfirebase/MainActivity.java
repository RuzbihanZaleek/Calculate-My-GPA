package com.example.connfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ImageView passVis;
    private EditText email, password;
    private Button login;
    String trimEmail;
    private FirebaseAuth auth;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        passVis=(ImageView) findViewById(R.id.passVis);
        //passVis.setImageResource(R.drawable.hide);

        auth = FirebaseAuth.getInstance();

        passVis.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Toast.makeText(MainActivity.this, "Logged 1", Toast.LENGTH_SHORT).show();
                    passVis.setImageResource(R.drawable.show);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Toast.makeText(MainActivity.this, "Logged 2", Toast.LENGTH_SHORT).show();
                    passVis.setImageResource(R.drawable.hide);
                }
                return true;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // String txt_email = email.getText().toString();
               // String txt_password = password.getText().toString();
/////////////////////////////////////////////////////////
                String txt_email = "2021s00000@gmail.com";
                String txt_password = "lkas1209";
                //////////////////////////////////////////////////////
                int index = txt_email.indexOf('@');
                trimEmail = txt_email.substring(0,index);


                loginUser(txt_email, txt_password);
            }
        });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Logged", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AfterLogged.class);

                intent.putExtra("userName",trimEmail);

                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "incorrect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}