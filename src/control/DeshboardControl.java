/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.ProjetosDAO;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumnModel;
import util.Data;
import util.Funcoes;

/**
 *
 * @author elias
 */
public class DeshboardControl {

    String titulo;
    int idCliente;
    int idDesenvolvedor;
    Data data1;
    Data data2;
    JTable projetos;
    JTable topicos;
    JInternalFrame frame;
    JFormattedTextField filtroData1;
    JFormattedTextField filtroData2;
    JTextField  filtroNom;
    int  filtroIdCli = 0;
    int  filtroIdDes= 0;
    ResultSet rs;

    
    
    
     public void popularProjetos() {
         data1 = new Data(1, 1, 1);
         data2 = new Data(1, 1, 1);
        if (filtroData1.getText().trim().length() == 10) {
            data1.setData(filtroData1.getText());
        }
        if (filtroData2.getText().trim().length() == 10) {
            data2.setData(filtroData2.getText());
        }
        
        rs = new ProjetosDAO().resultado(filtroNom.getText(), filtroIdCli, filtroIdDes, data1, data2);
  
           Funcoes.populaTabela(projetos, "Ver Detalhes,Titulo,Cliente,Descrição", rs, "idprojeto,titulo,cliente,descricao");
    }
    
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdDesenvolvedor() {
        return idDesenvolvedor;
    }

    public void setIdDesenvolvedor(int idDesenvolvedor) {
        this.idDesenvolvedor = idDesenvolvedor;
    }

    public Data getData1() {
        return data1;
    }

    public void setData1(Data data1) {
        this.data1 = data1;
    }

    public JFormattedTextField getFiltroData1() {
        return filtroData1;
    }

    public void setFiltroData1(JFormattedTextField filtroData1) {
        this.filtroData1 = filtroData1;
    }

    public JFormattedTextField getFiltroData2() {
        return filtroData2;
    }

    public void setFiltroData2(JFormattedTextField filtroData2) {
        this.filtroData2 = filtroData2;
    }

    public JTextField getFiltroNom() {
        return filtroNom;
    }

    public void setFiltroNom(JTextField filtroNom) {
        this.filtroNom = filtroNom;
    }

    public int getFiltroIdCli() {
        return filtroIdCli;
    }

    public void setFiltroIdCli(int filtroIdCli) {
        this.filtroIdCli = filtroIdCli;
    }

    public int getFiltroIdDes() {
        return filtroIdDes;
    }

    public void setFiltroIdDes(int filtroIdDes) {
        this.filtroIdDes = filtroIdDes;
    }
    

    public Data getData2() {
        return data2;
    }

    public JInternalFrame getFrame() {
        return frame;
    }

    public void setFrame(JInternalFrame frame) {
        try {
            frame.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(DeshboardControl.class.getName()).log(Level.SEVERE, null, "Erro técnico: "+ex);
        }
        this.frame = frame;
        
    }
    

    public void setData2(Data data2) {
        this.data2 = data2;
    }

    public JTable getProjetos() {
        return projetos;
    }

    public void setProjetos(JTable projetos) {
        this.projetos = projetos;
        popularProjetos();
    }

    public JTable getTopicos() {
        return topicos;
    }

    public void setTopicos(JTable topicos) {
        this.topicos = topicos;
    }
    
    
}
