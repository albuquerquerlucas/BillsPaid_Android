package com.samuniz.billspaid_android.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.samuniz.billspaid_android.Entities.Receita;
import com.samuniz.billspaid_android.R;

import java.util.List;

public class ReceitasAdapter extends ArrayAdapter {

    private Activity context;
    private int resource;
    private List<Receita> receitas;

    public ReceitasAdapter(@NonNull Activity context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.receitas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View linha = inflater.inflate(resource, null);

        TextView txtItemDescricaoR = linha.findViewById(R.id.txtItemDescricaoR);
        TextView txtItemValorR = linha.findViewById(R.id.txtItemValorR);

        txtItemDescricaoR.setText(receitas.get(position).getDescricao());
        txtItemValorR.setText(receitas.get(position).getValor());

        return linha;
    }
}
