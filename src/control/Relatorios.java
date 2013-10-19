/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import connection.ConexaoBD;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author elias
 */
public class Relatorios {

    public void listagem(String siglaEstado) {
        try {

            // Compila o relatorio
            JasperReport relatorio = JasperCompileManager.compileReport(getClass().getResourceAsStream("/Reports/cidades.jrxml"));

            // Mapeia campos de parametros para o relatorio, mesmo que nao existam
            Map parametros = new HashMap();


            // parametro 'nomeParametro' criado, mas não possui valor
            parametros.put("estado", siglaEstado);

            // Executa relatoio
            JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, new ConexaoBD().con);

            // Exibe resultado em video
            JasperViewer.viewReport(impressao, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e);
        }
    }
}
