package com.samuniz.billspaid_android.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.samuniz.billspaid_android.R;

public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText edtEmailL, edtSenhaL;
    private TextView btnCadastroL;
    private Button btnLoginL;
    private String emailInput, senhaInput;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtEmailL = findViewById(R.id.edtEmailL);
        edtSenhaL = findViewById(R.id.edtSenhaL);
        btnLoginL = findViewById(R.id.btnLoginL);
        btnCadastroL = findViewById(R.id.btnCadastroL);
        btnLoginL.setOnClickListener(this);
        btnCadastroL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoginL:
                validarDados();
                break;
            case R.id.btnCadastroL:
                goToCadastro();
                break;
        }
    }

    private void validarDados(){
        emailInput = edtEmailL.getText().toString().trim();
        senhaInput = edtSenhaL.getText().toString().trim();

        if(!emailInput.equals("") && !senhaInput.equals("")){
            efetuarLogin(emailInput, senhaInput);
        }else{
            Toast.makeText(getApplicationContext(), "Preencha os campos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void efetuarLogin(String email, String senha) {
        mAuth.signInWithEmailAndPassword(email, senha).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                goToMain();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Erro ao efetuar login, tente novamente!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToMain(){
        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(it);
        finish();
    }

    private void goToCadastro(){
        Intent it = new Intent(getApplicationContext(), CadastroActivity.class);
        startActivity(it);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
