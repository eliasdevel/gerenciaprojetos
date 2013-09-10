/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.mysql.jdbc.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elias
 */
public class DAO {

    ResultSet rs;
    public String where[];
    public String limit;
    public String order;

    public DAO(ResultSet rs) {
        this.rs = rs;
    }

    private PreparedStatement preparaWhere(String[] whereV) {
        PreparedStatement ps = null;
        String[] per = new String[whereV.length];
        String[] aux = new String[2];
        String comando = "";
        String where = "";
        if (this.where.length > 1) {
            int i = 0;
            while (i <= whereV.length - 1) {
                if (whereV[i].split("like").length > 1) {
                    aux = whereV[i].split("like");
                    comando = "like";
                } else {
                    if (whereV[i].split("=").length > 1) {
                        aux = whereV[i].split("=");
                        comando = "=";
                    }
                }
                if (i != whereV.length - 1) {
                    where += whereV[i] + "AND";
                } else {
                    where += whereV[i];
                }
            }
        } else {
            where += whereV[0];
        }
        return ps;
    }

    public ResultSet get(String tabela) {
        String sql = "SELECT * FROM " + tabela;
        try {
            PreparedStatement st = (PreparedStatement) connection.ConexaoBD.con.prepareStatement(sql);
            this.rs = st.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.rs;
    }

    public ResultSet getWhere(String tabela, String[] where) {
        String sql = "SELECT * FROM " + tabela + " WHERE " + this.where;

        return this.rs;
    }
}
