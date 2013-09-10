/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConexaoBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author elias
 */
public class EstadosDAO {

    ResultSet rs;

    public ResultSet linhas() {
        String sql = "SELECT * FROM estados ";
        try {
            PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
            rs = ps.executeQuery();
            rs.first();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return rs;
    }
}
