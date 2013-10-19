/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConexaoBD;
import entidadesRelacoes.Cidade;
import entidadesRelacoes.Desenvolvedor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.Msg;

/**
 *
 * @author elias
 */
public class CidadesDAO {

    ResultSet rs = null;

    public ResultSet resultado(String pesquisa) {
        if (pesquisa != null) {
//        String where = "";
            pesquisa = "%" + pesquisa + "%";
            String sql = "SELECT idcidade,nome,siglaestado FROM cidades WHERE siglaestado LIKE ? OR nome LIKE ?";
            try {
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setString(1, pesquisa);
                ps.setString(2, pesquisa);
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex);
            }
            System.out.println(sql);
        } else {
            JOptionPane.showMessageDialog(null, "A variável de pesquisa não pode ser nula!, se quiser uma variavé sem valor mande: \"\" ");
        }
        return rs;

    }

    public ResultSet linhas() {
        String sql = "SELECT * FROM cidades";
        try {
            rs = ConexaoBD.con.createStatement().executeQuery(sql);
        } catch (SQLException ex) {
            new Msg().msgGeneric("Erro ao selecionar cidades.");
        }
        return rs;
    }

    public Cidade linha(String id) {
        Cidade c = new Cidade(Integer.parseInt(id), "", "", "");
        String sql = "SELECT idcidade,nome,siglaestado,cep FROM cidades WHERE idcidade = ?";
        try {
            PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
            rs.first();
            c.setNome(rs.getString("nome"));
            c.setSiglaestado(rs.getString("siglaestado"));
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return c;
    }

    public int iud(char op, Desenvolvedor d) {
        String sql;
        int rows = 0;
        try {
            if (op == 'i') {                                                               //1 2 3 4
                sql = ("INSERT INTO desenvolvedores (idcategoria,nome,telefone,email) VALUES(?,?,?,?)");
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setInt(1, d.getIdCategoria());
                ps.setString(2, d.getNome());
                ps.setString(3, d.getTelefone());
                ps.setString(4, d.getEmail());
                rows = ps.executeUpdate();
                ps.close();
            } else {
                if (op == 'u') {
                    //1         2             3          4                         5
                    sql = "UPDATE desenvolvedores SET idcategoria = ?, nome = ?, telefone = ?, email = ? WHERE iddesenvolvedor = ?";
                    PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                    ps.setInt(1, d.getIdCategoria());
                    ps.setString(2, d.getNome());
                    ps.setString(3, d.getTelefone());
                    ps.setString(4, d.getEmail());
                    ps.setInt(5, d.getId());
                    rows = ps.executeUpdate();
                    ps.close();
                } else {
                    if (op == 'd') {
                        rows = ConexaoBD.con.createStatement().executeUpdate("DELETE FROM desenvolvedores "
                                + "WHERE iddesenvolvedor = " + d.getId());
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return rows;
    }
}
