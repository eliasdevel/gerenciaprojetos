/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.Query;
import javax.swing.JOptionPane;

/**
 *
 * @author Suporte02
 */
public class ConexaoBD {

    public static Connection con;
    public ResultSet rs = null;

    public boolean abriuConexao() {
        boolean abriu = false;

        String url = "jdbc:mysql://localhost:3306/gerenciaprojetos";
        String user = "root";
        String pass = "934ceara";
        String driver = "com.mysql.jdbc.Driver";


        try {
            Class.forName(driver);

            con = DriverManager.getConnection(url, user, pass);


            abriu = true;
            System.out.println("Conexão concluida com sucesso! ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar: " + ex);
            abriu = false;
        } catch (ClassNotFoundException e) {
            System.out.println("Erro: Não encontrou a classe: " + e);
            abriu = false;
        }

        return abriu;
    }

    public Connection obterConexao() {
        return con;
    }

    public Statement obterStatement() {
        Connection con = obterConexao();
        Statement stmt = null;
        try {
            stmt = (Statement) con.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return stmt;

    }

    public Statement pesquisa(String sql) {
        Statement st = null;
        try {
            st = (Statement) con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return st;
    }

    public int iUD(String sql) {
        int rows = 0;
        try {
            rows = con.createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY).executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rows;
    }
}
