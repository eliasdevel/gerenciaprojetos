/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.swing.JOptionPane;

/**
 *
 * @author elias
 */
public class Data {

    int ano;
    int dia;
    int mes;

    public Data(int dia, int mes, int ano) {
        this.ano = ano;
        this.dia = dia;
        this.mes = mes;
        if (!validar()) {
            this.dia = 9;
            this.mes = 3;
            this.ano = 1993;
        }
    }

    public boolean validar() {
        boolean valido = true;
        if(this.mes>12 || this.mes<1){
            valido = false;
        }
        
        if (dia > 31 || dia < 1) {
            valido = false;
        }
        
        if(anoBissexto()){
            if(mes==2 && dia > 29){
            valido = false;
            }
        }else{
            if(mes==2 && dia > 28){
            valido = false;
            }
        }
        
        if (mes == 4 || mes == 6 || mes == 8 || mes == 11 && dia > 30) {
            valido = false;
        }

        if (!valido) {
            JOptionPane.showMessageDialog(null,"Data inválida!: " + this.getData() + " esta data será substituída por 09/03/1993" );
            this.dia = 9;
            this.mes = 3;
            this.ano = 1993;
        }
        return valido;
    }

    public void setDBData(String ymd) {
        if (ymd != null) {
            String[] d = ymd.split("-");
            this.ano = Integer.parseInt(d[0]);
            this.mes = Integer.parseInt(d[1]);
            this.dia = Integer.parseInt(d[2]);
        }
        validar();

    }

    public String getDBData() {
        return this.ano + "-" + this.mes + "-" + this.dia;
    }

    public String getData() {
        return this.dia + "/" + this.mes + "/" + this.ano;
    }

    public void setData(String data) {
        if (data != null) {
            String[] dmy = data.split("/");
            if (!data.trim().equals("/  /")) {
                this.dia = Integer.parseInt(dmy[0]);
                this.mes = Integer.parseInt(dmy[1]);
                this.ano = Integer.parseInt(dmy[2]);

            }
        }
        validar();
    }
    
    public boolean anoBissexto()
    {
        boolean ok = false;
        if ((ano % 4 == 0 && ano % 100 != 0) || ano % 400 == 0)
        {
            ok = true;
        }
        return ok;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }
}


