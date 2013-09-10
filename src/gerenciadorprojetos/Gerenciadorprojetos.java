package gerenciadorprojetos;

import connection.ConexaoBD;
import java.awt.Dimension;
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

    public static void main(String[] args) {
        ConexaoBD conexao = new ConexaoBD();
        if (conexao.abriuConexao()) {
            fPrincipal form = new fPrincipal();
            form.setSize(DIMENSAOFULL);
            form.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "A conexão com o banco de dados falhou, contate o Administrador.");
        }
    }
}
