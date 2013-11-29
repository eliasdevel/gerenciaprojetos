package gerenciadorprojetos;

import connection.ConexaoBD;
import java.awt.Dimension;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.fPrincipal;

/**
 *
 * @author elias
 * @version 0.8
 * @since 01/08/2013
 *
 */
public class Gerenciadorprojetos {

    public static Dimension DIMENSAOFULL = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    Statement s;
    public static void main(String[] args) {
        ConexaoBD conexao = new ConexaoBD();
        if (conexao.abriuConexao()) {
            fPrincipal form = new fPrincipal();
            form.setSize(DIMENSAOFULL);
            form.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "A conexão com o banco de dados falhou, contate o Administrador.");
            try { 
               
                
                ConexaoBD.con = DriverManager.getConnection ("jdbc:mysql://localhost/?user=root&password=rootpassword");

            } catch (SQLException ex) {
                Logger.getLogger(Gerenciadorprojetos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
