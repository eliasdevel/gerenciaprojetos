/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConexaoBD;
import entidadesRelacoes.Desenvolvedor;
import entidadesRelacoes.Topico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author elias
 */
public class DesenvolvedoresDAO {

    ResultSet rs = null;
ArrayList<Desenvolvedor> desenvolvedoresList;
    public ResultSet resultado(String pesquisa) {
        if (pesquisa != null) {
//        String where = "";
            pesquisa = "%" + pesquisa + "%";

            String sql = "SELECT d.iddesenvolvedor,d.iddesenvolvedor, d.nome, d.telefone,d.email,c.titulo AS 'categoria'  "
                    + "FROM desenvolvedores d ,  categorias c "
                    + "WHERE d.idcategoria=c.idcategoria AND nome LIKE ? AND c.titulo LIKE ? ORDER BY nome";

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

    public Desenvolvedor linha(String id) {
        Desenvolvedor d = new Desenvolvedor(Integer.parseInt(id), 1, "", "", "");

        String sql = "SELECT d.iddesenvolvedor, d.idcategoria, d.nome, d.telefone, d.email "
                + "FROM desenvolvedores d "
                + "WHERE d.iddesenvolvedor= ?";
        try {
            PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            rs.first();
            d.setNome(rs.getString("nome"));
            d.setEmail(rs.getString("email"));
            d.setTelefone(rs.getString("telefone"));
            d.setIdCategoria(rs.getInt("idcategoria"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return d;
    }
     public ArrayList linhas(String idProjeto) {
        desenvolvedoresList = new ArrayList();
        String sql = "SELECT d.iddesenvolvedor,d.nome,d.idcategoria,d.telefone,d.email FROM desenvolvedores d INNER JOIN projetos_desenvolvedores pd ON d.iddesenvolvedor = pd.iddesenvolvedor WHERE pd.idprojeto = ?";
        try {
            PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
            ps.setString(1, idProjeto);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Desenvolvedor d = new Desenvolvedor(rs.getInt("iddesenvolvedor"), rs.getInt("idcategoria"),rs.getString("nome"),rs.getString("telefone"),rs.getString("telefone"));
                desenvolvedoresList.add(d);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return desenvolvedoresList;
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
