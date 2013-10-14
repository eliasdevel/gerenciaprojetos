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
           String pesquisa2 = "%" + pesquisa + "%";
            String sql = "SELECT idprojeto,c.nome AS cliente, titulo,descricao FROM projetos p INNER JOIN clientes c ON c.idcliente = p.idcliente";
            String where = "WHERE p.titulo like ? OR p.descricao LIKE ?  OR c.nome LIKE ? ORDER BY p.titulo";

            try {
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                if (!pesquisa.trim().equals("")) {
                    sql += where;
                    ps.setString(1, pesquisa2);
                    ps.setString(2, pesquisa2);
                    ps.setString(3, pesquisa2);
                }
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

    public Projeto linha(String id) {
        Projeto p = new Projeto(0, 0, null, null, false);
        String sql = "SELECT * FROM projetos WHERE idprojeto = ?";
        try {
            PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            rs.first();
            p.setTitulo(rs.getString("titulo"));
            p.setDescricao(rs.getString("descricao"));
            p.setId(rs.getInt("idprojeto"));
            p.setIdcliente(rs.getInt("idcliente"));
            p.setPronto(rs.getBoolean("pronto"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return p;
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
                rs = ConexaoBD.con.createStatement().executeQuery("SELECT MAX(idprojeto) AS maior FROM projetos");
                rs.first();
                p.setId(rs.getInt("maior"));
                ps.close();
            } else {
                if (op == 'u') {
//                                                      1              2              3                     4
                    sql = "UPDATE projetos SET titulo = ?, descricao = ?, idcliente = ? WHERE idprojeto = ?";
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
