/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConexaoBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Funcoes;

/**
 *
 * @author elias
 */
public class Query {

    private PreparedStatement ps;
    private String bancoTabela;
    private String[] consulta;
    private String consultas;
    private String[] coluna;
    private String colunas;
    private String sqlFrom;
    private String sqlOrder;
    private String sqlLimit;
    ResultSet rs = null;

    public Query(String bancoTabela, String[] coluna, String[] consulta) {
        this.consulta = consulta;
        this.coluna = coluna;
        this.colunas = Funcoes.implode(coluna, ",");
        System.out.println(coluna.length);
        this.bancoTabela = bancoTabela;
        this.sqlFrom = " FROM " + this.bancoTabela;
        try {
            this.rs = preparaWhere().executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private PreparedStatement preparaWhere() {

        String[] vet = new String[2];
        String sql = "SELECT " + this.colunas + this.sqlFrom + " WHERE ";
        int tam = this.consulta.length;
        String[] valor = new String[tam];
        for (int i = 0; i < tam; i++) {
            if (consulta[i].split("=").length == 2) {
                vet = consulta[i].split("=");
                valor[i] = vet[1];
                sql += vet[0] + " = ";
                sql += "? ";
            }
        }
        try {

//            ps = ConexaoBD.con.prepareStatement(sql);

            System.out.println(sql);
            this.ps = ConexaoBD.con.prepareStatement(sql);

            for (int i = 0; i < tam; i++) {
//                System.out.println(sql);
                String valorx = valor[i];
                System.out.println(i);
                System.out.println(valorx);
                this.ps.setString(i + 1, valorx);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.ps;
    }

    public static void main(String[] args) {
//        Query q = new Query();
//        q.consulta = new String[2];
//        q.consulta[0] = "cidade like valor";
//        q.consulta[1] = "AND nome like valor1";
////        q.preparaWhere();
//        ConexaoBD c = new ConexaoBD();
//        if (c.abriuConexao()) {
//
////            System.out.println();
////            Query q = new Query("categorias", new String[]{"titulo", "idcategoria"}, new String[]{"idcategoria = 116 "});
//            try {
//                if (q.rs.next()) {
//
//                    System.out.println(q.rs.getString("titulo"));
//
//                } else {
//                    System.out.println("não entrou");
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }
}
