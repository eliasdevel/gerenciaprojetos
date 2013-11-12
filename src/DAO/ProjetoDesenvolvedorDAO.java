/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConexaoBD;
import entidadesRelacoes.ProjetoDesenvolvedor;
import entidadesRelacoes.ProjetoTopico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import view.Msg;

/**
 *
 * @author elias
 */
public class ProjetoDesenvolvedorDAO {

    ResultSet rs = null;

    public ResultSet resultadoProjeto(String idprojeto) {
        if (idprojeto != null) {
//        String where = "";
            idprojeto = "%" + idprojeto + "%";

            String sql = "SELECT iddesenvolvedor,idpojeto FROM projetos_desenvolvedores WHERE idprojeto = ?";

            try {
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setString(1, idprojeto);
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

    public int iud(char op, ProjetoDesenvolvedor pd) {
        String sql;
        int rows = 0;
        try {
            if (op == 'i') {
                sql = ("INSERT INTO projetos_desenvolvedores (idprojeto,iddesenvolvedor) VALUES(?,?)");
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setInt(1, pd.getIdProjeto());
                ps.setInt(2, pd.getIdDesenvolvedor());
                rows = ps.executeUpdate();
                ps.close();
            } else {
                if (op == 'd') {
                    rows = ConexaoBD.con.createStatement().executeUpdate("DELETE FROM projetos_desenvolvedores "
                            + "WHERE idprojeto = " + pd.getIdProjeto());
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return rows;
    }
}
