/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConexaoBD;
import entidadesRelacoes.Categoria;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author elias
 */
public class TopicosDAO {
    
      ResultSet rs = null;

    public ResultSet resultado(String pesquisa) {
        if (pesquisa != null) {
//        String where = "";
            pesquisa = "%" + pesquisa + "%";

            String sql = "SELECT idcategoria,idcategoria,titulo,descricao FROM categorias WHERE titulo like ? ORDER BY titulo";

            try {
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setString(1, pesquisa);
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

    public Categoria linha(String id) {
        Categoria c = new Categoria(1,null,null);
            c.setId(Integer.parseInt(id));
        String sql = "SELECT * FROM categorias WHERE idcategoria = ?";
        try {
            PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            rs.first();
            c.setTitulo(rs.getString("titulo"));
            c.setDescricao(rs.getString("descricao"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return c;
    }

    public int iud(char op, Categoria c) {
        String sql;
        int rows = 0;
        try {
            if (op == 'i') {
                sql = ("INSERT INTO categorias (titulo,descricao) VALUES(?,?)");
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setString(1, c.getTitulo());
                ps.setString(2, c.getDescricao());
                rows = ps.executeUpdate();
                ps.close();
            } else {
                if (op == 'u') {
//                                                        1              2                     3
                    sql = "UPDATE categorias SET titulo = ?, descricao = ? WHERE idcategoria = ?";
                    PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                    ps.setString(1, c.getTitulo());
                    ps.setString(2, c.getDescricao());
                    ps.setString(3, c.getId() + "");
                    rows = ps.executeUpdate();
                    ps.close();
                } else {
                    if (op == 'd') {
                        rows = ConexaoBD.con.createStatement().executeUpdate("DELETE FROM categorias "
                                + "WHERE idcategoria = " + c.getId());
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return rows;
    }
}
