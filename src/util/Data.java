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
        if (!validar(this)) {
            this.dia = 9;
            this.mes = 3;
            this.ano = 1993;
        }
    }

    public boolean validar(Data d) {
        boolean valido = true;
        if (d.getDia() > 31 || d.getDia() < 1) {
            valido = false;
        }

        if (mes == 4 && dia > 30) {
            valido = false;
        }

        if (mes == 6 && dia > 30) {
            valido = false;
        }

        if (mes == 8 && dia > 30) {
            valido = false;
        }

        if (mes == 11 && dia > 30) {
            valido = false;
        }

        if (!valido) {
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
        validar(this);

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
        validar(this);
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
