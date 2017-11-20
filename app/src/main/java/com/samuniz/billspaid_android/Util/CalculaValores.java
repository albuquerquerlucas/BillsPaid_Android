package com.samuniz.billspaid_android.Util;

import java.text.DecimalFormat;
import java.util.List;

public class CalculaValores {

    public CalculaValores() {
    }

    public String calculaTotal(List<String> lista){
        double vTotal = 0, vAtual;
        String result = "";
        DecimalFormat df = new DecimalFormat("#,###.00");

        for(int i = 0; i < lista.size(); i++){
            String s = lista.get(i);
            vAtual = Double.parseDouble(s);
            vTotal = vAtual + vTotal;
        }

        if(vTotal == 0){
            result = "0,00";
        }else{
            result = df.format(vTotal);
        }

        return result;
    }
}
