package com.samuniz.billspaid_android.Activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.samuniz.billspaid_android.Adapters.ContasAdapter;
import com.samuniz.billspaid_android.Entities.Cliente;
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
    private AlertDialog confirmExclusao;
    private String idContaA;


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
        //txtContasListaTotal = findViewById(R.id.txtContasListaTotal);

        trazDadosDoBancoComSoma();

        listContasCL.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Conta c = contas.get(position);
                confirmaExclusao(c);
                //Toast.makeText(getApplicationContext(), "" + c.getDescricao(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void confirmaExclusao(final Conta conta){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.confirm_exclusao_conta, null);
        dialogBuilder.setView(dialogView);

        TextView txtTitleContaExc = dialogView.findViewById(R.id.txtTitleContaExc);
        TextView txtMsgExclusaoConta = dialogView.findViewById(R.id.txtMsgExclusaoConta);
        txtTitleContaExc.setText("Conta: " + conta.getDescricao());
        txtMsgExclusaoConta.setText("Deseja realmente excluir a conta " + conta.getDescricao() + " no valor de R$ " + conta.getValor() + "?");
        Button btnExcluirConta = dialogView.findViewById(R.id.btnExcluirConta);

        confirmExclusao = dialogBuilder.create();
        confirmExclusao.show();

        btnExcluirConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReferenceC.child(conta.getId()).removeValue();
                contas.clear();
                confirmExclusao.dismiss();
            }
        });

    }

    private void trazDadosDoBancoComSoma(){
        mReferenceC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contas.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Conta c = snapshot.getValue(Conta.class);

                    if(c.getIdCLiente().equals(String.valueOf(mUserC.getUid()))){
                        contas.add(c);
                        listaParaSoma.add(c.getValor());
                    }

                    calcular = new CalculaValores();
                    String total = calcular.calculaTotal(listaParaSoma);
                    //txtContasListaTotal.setText(total);

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
