/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConexaoBD;
import entidadesRelacoes.Categoria;
import entidadesRelacoes.Projeto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author elias
 */
public class ProjetosDAO {
      ResultSet rs = null;

    public ResultSet resultado(String pesquisa) {
        if (pesquisa != null) {
//        String where = "";
            pesquisa = "%" + pesquisa + "%";

            String sql = "SELECT idprojeto,(select nome from clientes c where p.idcliente = c.idcliente ) as cliente, titulo,descricao FROM projetos p "               ;
//                    + "WHERE p.titulo like ? OR p.descricao LIKE ? ORDER BY p.titulo";

            try {
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
//                ps.setString(1, pesquisa);
//                ps.setString(2, pesquisa);
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex);
            }
            System.out.println(sql);
        } else {
            JOptionPane.showMessageDialog(null, "A variável de pesquisa não pode ser nula!, se quiser uma variável sem valor mande: \"\" ");
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

    public int iud(char op, Projeto p) {
        String sql;
        int rows = 0;
        try {
            if (op == 'i') {
                sql = ("INSERT INTO projetos (titulo,idcliente,descricao) VALUES(?,?,?)");
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setString(1, p.getTitulo());
                ps.setInt(2, p.getIdcliente());
                ps.setString(3, p.getDescricao());
                rows = ps.executeUpdate();
                ps.close();
            } else {
                if (op == 'u') {
//                                                      1              2              3                     4
                    sql = "UPDATE projetos SET titulo = ?, descricao = ?, idcliente = ? WHERE idcategoria = ?";
                    PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                    ps.setString(1, p.getTitulo());
                    ps.setString(2, p.getDescricao());
                    ps.setInt(3, p.getIdcliente());
                    ps.setInt(4, p.getId());
                    rows = ps.executeUpdate();
                    ps.close();
                } else {
                    if (op == 'd') {
                        rows = ConexaoBD.con.createStatement().executeUpdate("DELETE FROM projetos "
                                + "WHERE idprojeto = " + p.getId());
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return rows;
    }
}
