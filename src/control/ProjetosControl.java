/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.ProjetosDAO;
import entidadesRelacoes.Projeto;
import entidadesRelacoes.ProjetoDesenvolvedor;
import entidadesRelacoes.ProjetoTopico;
import entidadesRelacoes.Topico;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import util.Funcoes;
import view.Editar;
import view.Excluir;
import view.Msg;

/**
 *
 * @author elias
 */
public class ProjetosControl {

    char operante;
    JCheckBox prontoPro;
   
    JTable tb;
    JTable desenvolvedores;
    JTable topicos;
    JInternalFrame form;
    JTabbedPane tp;
    JPanel p1;
    JPanel p2;
    JButton bt;
    JButton btSalvar;
    JTextField titulo;
    JTextArea descricao;
    int idCliente;
    JFormattedTextField dataInicio;
    JTextField cliente;
    JFormattedTextField dataPrevisao;
    JFormattedTextField dataFim;
//    Tópicos
    JTabbedPane tpTopicos;
    JPanel p1Topicos;
    JPanel p2Topicos;
    ArrayList<Topico> topicosList = new ArrayList();
    int codigo;
    ResultSet rs;

    public void popularProjetos(String pesquisa) {
        rs = new ProjetosDAO().resultado(pesquisa);
        try {
            rs.first();
        } catch (SQLException ex) {
            Logger.getLogger(ProjetosControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Funcoes.populaTabela(tb, "Titulo,Cliente,ID,Descrição", rs, "titulo,cliente,idprojeto,descricao");
    }

    public void acaoBotaoNovoSalvar() {
       
        
        if (this.tp.getSelectedIndex() != 1) {
            operante = 'n';
        } else {
            if (operante != 'i') {
                operante = 'u';
            }
        }
        if (operante == 'u' || operante == 'i') {
            ProjetosDAO iuds = new ProjetosDAO();
            Projeto p = new Projeto(codigo, idCliente, titulo.getText(), descricao.getText(), false);
            if (p.getTitulo().length() > 0) {
                if (p.getIdcliente() > 0) {
                    if (iuds.iud(operante, p) > 0) {
                        Funcoes.limparCampos(p1);
                        Funcoes.limparCampos(p2);
                        descricao.setText("");
                        new Msg().msgRegistrado(form);
                        tp.setSelectedIndex(0);
                        btSalvar.setText("Novo");
                        popularProjetos("");
                    }
                } else {
                    new Msg().msgGeneric("O Cliente precisa ser preenchido!");
                    cliente.requestFocus();
                }
            } else {
                new Msg().msgGeneric("O Título precisa ser preenchido!");
                titulo.requestFocus();
            }
        }
        if (operante == 'n') {
            btSalvar.setText("Salvar");
            tp.setSelectedIndex(1);
            operante = 'i';
        }

    }

    public void acaoSair() {
        acaoCancelar();
        form.setVisible(false);
    }

    public void acaoCancelar() {
        tp.setSelectedIndex(0);
        Funcoes.limparCampos(p1);
        Funcoes.limparCampos(p2);
        descricao.setText("");
        bt.setText("Novo");
        operante = 'n';
    }

    public JButton getBtSalvar() {
        return btSalvar;
    }

    public void setBtSalvar(JButton btSalvar) {
        this.btSalvar = btSalvar;
    }

    public JTextField getCliente() {
        return cliente;
    }

    public void setCliente(JTextField cliente) {
        this.cliente = cliente;
    }

    public JTable getTb() {
        return tb;
    }

    public void setTb(JTable tb) {
        this.tb = tb;
    }

    public JTable getDesenvolvedores() {
        return desenvolvedores;
    }

    public void setDesenvolvedores(JTable desenvolvedores) {
        this.desenvolvedores = desenvolvedores;
    }

    public JTable getTopicos() {
        return topicos;
    }

    public void setTopicos(JTable topicos) {
        this.topicos = topicos;
    }

    public JInternalFrame getForm() {
        return form;
    }

    public void setForm(JInternalFrame form) {
        this.form = form;
        this.form.setSize(1000, 800);
    }

    public JTabbedPane getTp() {
        return tp;
    }

    public void setTp(JTabbedPane tp) {
        this.tp = tp;
    }

    public JPanel getP1() {
        return p1;
    }

    public void setP1(JPanel p1) {
        this.p1 = p1;
    }

    public JPanel getP2() {
        return p2;
    }

    public void setP2(JPanel p2) {
        this.p2 = p2;
    }

    public JButton getBt() {
        return bt;
    }

    public void setBt(JButton bt) {
        this.bt = bt;
    }

    public JTextField getTitulo() {
        return titulo;
    }

    public void setTitulo(JTextField titulo) {
        this.titulo = titulo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public JTextArea getDescricao() {
        return descricao;
    }

    public void setDescricao(JTextArea descricao) {
        this.descricao = descricao;
    }

    public JFormattedTextField getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(JFormattedTextField dataInicio) {
        this.dataInicio = dataInicio;
    }

    public JFormattedTextField getDataPrevisao() {
        return dataPrevisao;
    }

    public void setDataPrevisao(JFormattedTextField dataPrevisao) {
        this.dataPrevisao = dataPrevisao;
    }

    public JFormattedTextField getDataFim() {
        return dataFim;
    }

    public void setDataFim(JFormattedTextField dataFim) {
        this.dataFim = dataFim;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public JCheckBox getProntoPro() {
        return prontoPro;
    }

    public void setProntoPro(JCheckBox prontoPro) {
        this.prontoPro = prontoPro;

    }
}
