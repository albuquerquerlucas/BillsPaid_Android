package com.samuniz.billspaid_android.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.samuniz.billspaid_android.Entities.Despesa;
import com.samuniz.billspaid_android.R;

import java.util.List;

public class DespesasAdapter extends ArrayAdapter {

    private Activity context;
    private int resource;
    private List<Despesa> despesas;

    public DespesasAdapter(@NonNull Activity context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.despesas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View linha = inflater.inflate(resource, null);

        TextView txtItemDescricaoD = linha.findViewById(R.id.txtItemDescricaoD);
        TextView txtItemValorD = linha.findViewById(R.id.txtItemValorD);

        txtItemDescricaoD.setText(despesas.get(position).getDescricao());
        txtItemValorD.setText(despesas.get(position).getValor());

        return linha;
    }
}
