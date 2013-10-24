/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConexaoBD;
import entidadesRelacoes.Projeto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.Data;
import javax.swing.JOptionPane;

/**
 *
 * @author Elias Müller
 *
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
            Data d = new Data(1, 1, 1);
            d.setDBData(rs.getString("dataInicial"));
            p.setDataInicial(d.getData());
            d.setDBData(rs.getString("dataPrevisao"));
            p.setDataPrevisao(d.getData());
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
                sql = ("INSERT INTO projetos (titulo,idcliente,descricao,dataInicial,dataPrevisao) VALUES(?,?,?,?,?)");
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                Data d = new Data(1, 1, 1);
                ps.setString(1, p.getTitulo());
                ps.setInt(2, p.getIdcliente());
                ps.setString(3, p.getDescricao());
                d.setData(p.getDataInicial());
                ps.setString(4, d.getDBData());
                d.setData(p.getDataPrevisao());
                ps.setString(5, d.getDBData());
                rows = ps.executeUpdate();
                rs = ConexaoBD.con.createStatement().executeQuery("SELECT MAX(idprojeto) AS maior FROM projetos");
                rs.first();
                p.setId(rs.getInt("maior"));
                ps.close();
            } else {
                if (op == 'u') {
                    sql = "UPDATE projetos SET titulo = ?, descricao = ?, idcliente = ?, dataInicial = ?,dataPrevisao = ? WHERE idprojeto = ?";
                    PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                    Data d = new Data(1, 1, 1);
                    ps.setString(1, p.getTitulo());
                    ps.setString(2, p.getDescricao());
                    ps.setInt(3, p.getIdcliente());
                    d.setData(p.getDataInicial());
                    ps.setString(4, d.getDBData());
                    d.setData(p.getDataPrevisao());
                    ps.setString(5, d.getDBData());
                    ps.setInt(6, p.getId());
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
