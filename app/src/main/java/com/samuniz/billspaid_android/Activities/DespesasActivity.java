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
import com.samuniz.billspaid_android.Adapters.DespesasAdapter;
import com.samuniz.billspaid_android.Entities.Despesa;
import com.samuniz.billspaid_android.R;
import com.samuniz.billspaid_android.Util.CalculaValores;

import java.util.ArrayList;
import java.util.List;

public class DespesasActivity extends AppCompatActivity {

    private ListView listDespesasCL;
    private TextView txtDespesasListaTotal;
    private List<Despesa> despesas;
    private DespesasAdapter despesasAdapter;
    private FirebaseAuth mAuthD;
    private FirebaseUser mUserD;
    private DatabaseReference mReferenceD;
    private ArrayList<String> listaParaSoma;
    private CalculaValores calcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        mAuthD = FirebaseAuth.getInstance();
        mUserD = mAuthD.getCurrentUser();
        mReferenceD = FirebaseDatabase.getInstance().getReference("despesas");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        despesas = new ArrayList<>();
        listaParaSoma = new ArrayList<>();
        listDespesasCL = findViewById(R.id.listDespesasCL);
        txtDespesasListaTotal = findViewById(R.id.txtDespesasListaTotal);

        mReferenceD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Despesa d = snapshot.getValue(Despesa.class);
                    if(d.getIdCliente().equals(String.valueOf(mUserD.getUid()))){
                        despesas.add(d);
                        listaParaSoma.add(d.getValor());
                    }
                    calcular = new CalculaValores();
                    String total = calcular.calculaTotal(listaParaSoma);
                    txtDespesasListaTotal.setText(total);
                }
                despesasAdapter = new DespesasAdapter(DespesasActivity.this, R.layout.despesa_item_list, despesas);
                listDespesasCL.setAdapter(despesasAdapter);
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
