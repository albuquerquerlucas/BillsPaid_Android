package com.samuniz.billspaid_android.Activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samuniz.billspaid_android.Entities.Conta;
import com.samuniz.billspaid_android.Entities.Despesa;
import com.samuniz.billspaid_android.Entities.Receita;
import com.samuniz.billspaid_android.R;
import com.samuniz.billspaid_android.Util.CalculaValores;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtContasM, txtDespesasM, txtReceitasM;
    private TextView textCardContVal, textCardDespVal, textCardRecVal;
    private Button btnContaM, btnDespesaM, btnReceitaM;
    private CardView cardContas, cardDespesas, cardReceitas;
    private AlertDialog aFormContas, aFormDespesas, aFormReceitas;
    private FirebaseAuth mmAuth;
    private FirebaseUser mmUser;
    private DatabaseReference mmReference, dbClientes, dbContas, dbDespesas, dbReceitas;
    private List<Conta> contas;
    private List<Despesa> despesas;
    private List<Receita> receitas;
    private ArrayList<String> listaParaSomaContas, listaParaSomaDespesas, listaParaSomaReceitas;
    private CalculaValores calcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mmAuth = FirebaseAuth.getInstance();
        mmUser = mmAuth.getCurrentUser();
        mmReference = FirebaseDatabase.getInstance().getReference();
        dbContas = FirebaseDatabase.getInstance().getReference("contas");
        dbClientes = FirebaseDatabase.getInstance().getReference("clientes");
        dbDespesas = FirebaseDatabase.getInstance().getReference("despesas");
        dbReceitas = FirebaseDatabase.getInstance().getReference("receitas");
    }

    @Override
    protected void onStart() {
        super.onStart();

        preencheCardContas();
        preencheCardDespesas();
        preencheCardReceitas();
    }

    @Override
    protected void onResume() {
        super.onResume();

        cardContas = findViewById(R.id.cardContas);
        cardContas.setOnClickListener(this);

        cardDespesas = findViewById(R.id.cardDespesas);
        cardDespesas.setOnClickListener(this);

        cardReceitas = findViewById(R.id.cardReceitas);
        cardReceitas.setOnClickListener(this);

        btnContaM = findViewById(R.id.btnContaM);
        btnContaM.setOnClickListener(this);

        btnDespesaM = findViewById(R.id.btnDespesaM);
        btnDespesaM.setOnClickListener(this);

        btnReceitaM = findViewById(R.id.btnReceitaM);
        btnReceitaM.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardContas:
                goToContasLista();
                break;
            case R.id.cardDespesas:
                goToDespesasLista();
                break;
            case R.id.cardReceitas:
                goToReceitasLista();
                break;
            case R.id.btnContaM:
                formNovaConta();
                break;
            case R.id.btnDespesaM:
                formNovaDespesa();
                break;
            case R.id.btnReceitaM:
                formNovaReceita();
                break;
        }
    }

    private void formNovaConta(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.form_conta, null);
        dialogBuilder.setView(dialogView);

        final EditText edtDescricaoFormC = dialogView.findViewById(R.id.edtDescricaoFormC);
        final EditText edtValorFormC = dialogView.findViewById(R.id.edtValorFormC);
        Button btnSalvarFormC = dialogView.findViewById(R.id.btnSalvarFormC);

        aFormContas = dialogBuilder.create();
        aFormContas.show();

        btnSalvarFormC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = UUID.randomUUID().toString();
                String descricao = edtDescricaoFormC.getText().toString();
                String valor = edtValorFormC.getText().toString();

                if(!descricao.equals("") && !valor.equals("")){
                    Conta c = new Conta(id, descricao, valor);
                    dbContas = FirebaseDatabase.getInstance().getReference("contas");
                    dbContas.child(id).setValue(c);
                    List<String> listaContas = new ArrayList<>();
                    listaContas.add(c.getId());

                    dbClientes.child(mmUser.getUid()).child("contas").setValue(listaContas);
                }else{
                    Toast.makeText(getApplicationContext(), "Preencha os campos.", Toast.LENGTH_SHORT).show();
                }
                aFormContas.dismiss();
            }
        });
    }

    private void formNovaDespesa(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.form_despesa, null);
        dialogBuilder.setView(dialogView);

        final EditText edtDescricaoFormD = dialogView.findViewById(R.id.edtDescricaoFormD);
        final EditText edtValorFormD = dialogView.findViewById(R.id.edtValorFormD);
        Button btnSalvarFormD = dialogView.findViewById(R.id.btnSalvarFormD);

        aFormDespesas = dialogBuilder.create();
        aFormDespesas.show();

        btnSalvarFormD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = UUID.randomUUID().toString();
                String descricao = edtDescricaoFormD.getText().toString();
                String valor = edtValorFormD.getText().toString();

                if(!descricao.equals("") && !valor.equals("")){
                    Despesa d = new Despesa(id, descricao, valor);
                    dbDespesas = FirebaseDatabase.getInstance().getReference("despesas");
                    dbDespesas.child(id).setValue(d);
                }else{
                    Toast.makeText(getApplicationContext(), "Preencha os campos.", Toast.LENGTH_SHORT).show();
                }
                aFormDespesas.dismiss();
            }
        });
    }

    private void formNovaReceita(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.form_receita, null);
        dialogBuilder.setView(dialogView);

        final EditText edtDescricaoFormR = dialogView.findViewById(R.id.edtDescricaoFormR);
        final EditText edtValorFormR = dialogView.findViewById(R.id.edtValorFormR);
        Button btnSalvarFormR = dialogView.findViewById(R.id.btnSalvarFormR);

        aFormReceitas = dialogBuilder.create();
        aFormReceitas.show();

        btnSalvarFormR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = UUID.randomUUID().toString();
                String descricao = edtDescricaoFormR.getText().toString();
                String valor = edtValorFormR.getText().toString();

                if(!descricao.equals("") && !valor.equals("")){
                    Receita r = new Receita(id, descricao, valor);
                    dbReceitas = FirebaseDatabase.getInstance().getReference("receitas");
                    dbReceitas.child(id).setValue(r);
                }else{
                    Toast.makeText(getApplicationContext(), "Preencha os campos.", Toast.LENGTH_SHORT).show();
                }
                aFormReceitas.dismiss();
            }
        });
    }

    private void preencheCardContas(){
        contas = new ArrayList<>();
        listaParaSomaContas = new ArrayList<>();
        textCardContVal = findViewById(R.id.textCardContVal);
        textCardContVal.setOnClickListener(this);

        dbClientes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contas.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Conta c = snapshot.getValue(Conta.class);
                    contas.add(c);
                    listaParaSomaContas.add(c.getValor());
                    calcular = new CalculaValores();
                    String total = calcular.calculaTotal(listaParaSomaContas);
                    textCardContVal.setText(total);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void preencheCardDespesas(){
        despesas = new ArrayList<>();
        listaParaSomaDespesas = new ArrayList<>();
        textCardDespVal = findViewById(R.id.textCardDespVal);
        textCardDespVal.setOnClickListener(this);

        dbDespesas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Despesa d = snapshot.getValue(Despesa.class);
                    despesas.add(d);
                    listaParaSomaDespesas.add(d.getValor());
                    calcular = new CalculaValores();
                    String total = calcular.calculaTotal(listaParaSomaDespesas);
                    textCardDespVal.setText(total);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void preencheCardReceitas(){
        receitas = new ArrayList<>();
        listaParaSomaReceitas = new ArrayList<>();
        textCardRecVal = findViewById(R.id.textCardRecVal);
        textCardRecVal.setOnClickListener(this);

        dbReceitas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Receita r = snapshot.getValue(Receita.class);
                    receitas.add(r);
                    listaParaSomaReceitas.add(r.getValor());
                    calcular = new CalculaValores();
                    String total = calcular.calculaTotal(listaParaSomaReceitas);
                    textCardRecVal.setText(total);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.itemSair) {
            mmAuth.signOut();
            goToLogin();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToContasLista(){
        Intent it = new Intent(getApplicationContext(), ContasActivity.class);
        startActivity(it);
        finish();
    }

    private void goToDespesasLista(){
        Intent it = new Intent(getApplicationContext(), DespesasActivity.class);
        startActivity(it);
        finish();
    }

    private void goToReceitasLista(){
        Intent it = new Intent(getApplicationContext(), ReceitasActivity.class);
        startActivity(it);
        finish();
    }

    private void goToLogin(){
        Intent it = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(it);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToLogin();
    }

}
