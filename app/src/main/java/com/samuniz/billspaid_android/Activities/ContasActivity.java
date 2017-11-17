package com.samuniz.billspaid_android.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samuniz.billspaid_android.Adapters.ContasAdapter;
import com.samuniz.billspaid_android.Entities.Conta;
import com.samuniz.billspaid_android.R;
import com.samuniz.billspaid_android.Util.CalculaValores;

import java.util.ArrayList;
import java.util.List;

public class ContasActivity extends AppCompatActivity {
    private ListView listContasCL;
    private TextView txtContasListaTotal;
    private List<Conta> contas;
    private ContasAdapter contasAdapter;
    private FirebaseAuth mAuthC;
    private FirebaseUser mUserC;
    private DatabaseReference mReferenceC;
    private ArrayList<String> listaParaSoma;
    private CalculaValores calcular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas);

        mAuthC = FirebaseAuth.getInstance();
        mUserC = mAuthC.getCurrentUser();
        mReferenceC = FirebaseDatabase.getInstance().getReference("contas");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        contas = new ArrayList<>();
        listaParaSoma = new ArrayList<>();
        listContasCL = findViewById(R.id.listContasCL);
        txtContasListaTotal = findViewById(R.id.txtContasListaTotal);

        mReferenceC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Conta c = snapshot.getValue(Conta.class);
                    contas.add(c);
                    listaParaSoma.add(c.getValor());
                    calcular = new CalculaValores();
                    String total = calcular.calculaTotal(listaParaSoma);
                    txtContasListaTotal.setText(total);

                }
                contasAdapter = new ContasAdapter(ContasActivity.this, R.layout.conta_item_list, contas);
                listContasCL.setAdapter(contasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnToMain();
    }

    private void returnToMain(){
        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(it);
        finish();
    }

}
