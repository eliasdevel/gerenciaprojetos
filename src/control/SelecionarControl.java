/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.CidadesDAO;
import DAO.ClientesDAO;
import DAO.DesenvolvedoresDAO;
import DAO.TopicosDAO;
import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import util.Funcoes;
import view.Selecionar;

/**
 *
 * @author elias
 */
public class SelecionarControl {

    JDialog dl;
    String janela;
    JTable tb;
    JTextField filtro;
    String codigo;

    public void filtro() {
        ResultSet rs;
        switch (janela) {
            case "cidades":
                rs = new CidadesDAO().resultado(filtro.getText());
                Funcoes.populaTabelaSelecao(tb, "Selecione,Nome,Estado", rs, "idcidade,nome,siglaestado");
                dl.setSize(500, 600);
                break;
            case "clientes":
                rs = new ClientesDAO().resultado(filtro.getText());
                Funcoes.populaTabelaSelecao(tb, "Selecione,Nome,Telefone,Email,Cidade", rs, "idcliente,nome,telefone,email,cidade");
                dl.setSize(500, 600);
                break;
            case "topicos":
                rs = new TopicosDAO().resultado(filtro.getText());
                Funcoes.populaTabelaSelecao(tb, "Selecione,Titulo", rs, "idtopico,titulo");
                dl.setSize(500, 600);
                break;
            case "desenvolvedores":
                rs = new DesenvolvedoresDAO().resultado(filtro.getText());
                Funcoes.populaTabelaSelecao(tb, "Selecione,Nome", rs, "iddesenvolvedor,nome");
                dl.setSize(500, 600);
                break;
                
        }
        new Select(tb, 0);
        if (!dl.isShowing()) {
            dl.setLocationByPlatform(true);
            dl.setVisible(true);
        }
    }

    class Select extends Selecionar {

        public Select(JTable tb, int column) {
            super(tb, column);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            codigo = e.getActionCommand();
            dl.setVisible(false);
            filtro.setText("");
        }
    }

    public JDialog getDl() {
        return dl;
    }

    public void setDl(JDialog dl) {
        this.dl = dl;
    }

    public String getJanela() {
        return janela;
    }

    public void setJanela(String janela) {
        this.janela = janela;
    }

    public JTable getTb() {
        return tb;
    }

    public void setTb(JTable tb) {
        this.tb = tb;
    }

    public JTextField getFiltro() {
        return filtro;
    }

    public void setFiltro(JTextField filtro) {
        this.filtro = filtro;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
