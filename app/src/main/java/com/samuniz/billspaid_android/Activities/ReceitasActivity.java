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
import com.samuniz.billspaid_android.Adapters.ReceitasAdapter;
import com.samuniz.billspaid_android.Entities.Receita;
import com.samuniz.billspaid_android.R;
import com.samuniz.billspaid_android.Util.CalculaValores;

import java.util.ArrayList;
import java.util.List;

public class ReceitasActivity extends AppCompatActivity {

    private ListView listReceitasCL;
    private TextView txtReceitasListaTotal;
    private List<Receita> receitas;
    private ReceitasAdapter receitasAdapter;
    private FirebaseAuth mAuthR;
    private FirebaseUser mUserR;
    private DatabaseReference mReferenceR;
    private ArrayList<String> listaParaSoma;
    private CalculaValores calcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);

        mAuthR = FirebaseAuth.getInstance();
        mUserR = mAuthR.getCurrentUser();
        mReferenceR = FirebaseDatabase.getInstance().getReference("receitas");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        receitas = new ArrayList<>();
        listaParaSoma = new ArrayList<>();
        listReceitasCL = findViewById(R.id.listReceitasCL);
        txtReceitasListaTotal = findViewById(R.id.txtReceitasListaTotal);

        mReferenceR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Receita r = snapshot.getValue(Receita.class);
                    if(r.getIdCliente().equals(String.valueOf(mUserR.getUid()))){
                        receitas.add(r);
                        listaParaSoma.add(r.getValor());
                    }
                    calcular = new CalculaValores();
                    String total = calcular.calculaTotal(listaParaSoma);
                    txtReceitasListaTotal.setText(total);
                }

                receitasAdapter = new ReceitasAdapter(ReceitasActivity.this, R.layout.receita_item_list, receitas);
                listReceitasCL.setAdapter(receitasAdapter);
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
