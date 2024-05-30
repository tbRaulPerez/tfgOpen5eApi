package com.example.tfg.controller;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tfg.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Actividad que gestiona el registro de usuarios en la base de datos, se usará la libreria SugarORM
public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etConfirmPassword;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        etEmail = findViewById(R.id.etRegisterEmail);
        etPassword = findViewById(R.id.etRegisterPass);
        etConfirmPassword = findViewById(R.id.etRegisterConfirmPass);
        btRegister = findViewById(R.id.btRegister);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmptyFields()){
                    Toast.makeText(RegisterActivity.this, "You must fill all the fields", Toast.LENGTH_LONG).show();
                }else {
                    if(checkPassword()){
                        mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "createUserWithEmail:success");
                                            finish();
                                            Log.d(TAG, mAuth.getCurrentUser().getEmail());
                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(RegisterActivity.this, "There is already an account with this email", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                }
            }
        });
    }
    //Comprueba que los campos no estan vacíos
    private boolean checkEmptyFields() {
        Boolean isEmpty = false;
        if( etEmail.getText().toString().equals("")
                || etPassword.getText().toString().equals("")
                || etConfirmPassword.getText().toString().equals("")){
            isEmpty = true;
        }
        return isEmpty;
    }

    private boolean checkPassword(){
        boolean returns = false;
        if(etPassword.getText().toString().equals(etConfirmPassword.getText().toString())){
            if(etPassword.length() < 6){
                Toast.makeText(RegisterActivity.this, "Password must contain at least six characters", Toast.LENGTH_LONG).show();
            }else {
                returns = true;
            }
        } else{
            Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_LONG).show();
        }
        return returns;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

        }
    }

}