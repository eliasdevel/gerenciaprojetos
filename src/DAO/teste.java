/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConexaoBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author elias
 */
public class teste {

    ResultSet rs = null;
ConexaoBD c = new ConexaoBD();
    private Connection con;

    private ResultSet consultaCadastro(String nome) {
con = c.obterConexao();
        System.out.println(nome);
        try {
            if (nome == null || nome.equals("")) {

              
                rs = connection.ConexaoBD.con.createStatement().executeQuery("select nome , telefone from clientes");
                while(rs.next()){
JOptionPane.showMessageDialog(null,rs.getString("nome"));
                }
            }
            rs = connection.ConexaoBD.con.createStatement().executeQuery("select nome , telefone from clientes");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return rs;
    }

    public void popularTabelaAgenda(JTable tabela, String nome) {
        int[] indices = {1, 2, 3, 4};
        consultaCadastro(nome);

        util.Funcoes.populaTabela(tabela, "nome,telefone", rs, "nome,telefone");
        try {
            System.out.println(rs.getRow());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
    }
}
