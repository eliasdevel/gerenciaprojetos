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
import util.Data;
import java.lang.Object;
import groovy.lang.ExpandoMetaClass;
 import groovy.lang.GroovyObject;
 import groovy.lang.MetaClass;
 import groovy.lang.MetaMethod;
 import groovy.lang.MissingMethodException;

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
    
    public void relatorioProjetos(Data data1,Data data2,int idcliente,int iddesenvolvedor,String titulo){
        try {

            // Compila o relatorio
            JasperReport relatorio = JasperCompileManager.compileReport(getClass().getResourceAsStream("/Reports/ProjetosRel.jrxml"));

            // Mapeia campos de parametros para o relatorio, mesmo que nao existam
            Map parametros = new HashMap();

            // parametro 'nomeParametro' criado, mas não possui valor
            parametros.put("dataInicialPro", data1.getDBData());
            parametros.put("dataInicialPro2", data2.getDBData());
            parametros.put("idclientePro", idcliente);
            parametros.put("iddesenvolvedorPro", iddesenvolvedor);
            parametros.put("idprojeto", 1);
            parametros.put("tituloPro", "%"+titulo+"%");
            
            

            // Executa relatoio
            JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, new ConexaoBD().con);

            // Exibe resultado em video
            JasperViewer.viewReport(impressao, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e);
        }
        
    }
    public void relatorioClientes(String nome){
        try {

            // Compila o relatorio
            JasperReport relatorio = JasperCompileManager.compileReport(getClass().getResourceAsStream("/Reports/RelatorioClientes.jrxml"));

            // Mapeia campos de parametros para o relatorio, mesmo que nao existam
            Map parametros = new HashMap();

            // parametro 'nomeParametro' criado, mas não possui valor
          
            parametros.put("nome", "%"+nome+"%");

            // Executa relatoio
            JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, new ConexaoBD().con);

            // Exibe resultado em video
            JasperViewer.viewReport(impressao, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e);
        }
        
    }
}
