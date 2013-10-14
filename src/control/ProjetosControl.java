/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.ClientesDAO;
import DAO.ProjetosDAO;
import DAO.ProjetosTopicosDAO;
import DAO.TopicosDAO;
import entidadesRelacoes.Projeto;
import entidadesRelacoes.ProjetoTopico;
import entidadesRelacoes.Topico;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
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
    JTextArea descricaoTopico;
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
        Funcoes.populaTabela(tb, "Editar,Excluír,Titulo,Cliente,Descrição", rs, "idprojeto,idprojeto,titulo,cliente,descricao");
        new editProjeto(tb, 1);
        new delProjeto(tb, 0);
        TableColumnModel modeloDaColuna = tb.getColumnModel();
        modeloDaColuna.getColumn(0).setMaxWidth(70);
        modeloDaColuna.getColumn(1).setMaxWidth(70);
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
            TopicosDAO iudsTopicos = new TopicosDAO();
            ProjetosTopicosDAO iudsProjetosTopicos = new ProjetosTopicosDAO();
            Projeto p = new Projeto(codigo, idCliente, titulo.getText(), descricao.getText(), false);
            if (p.getTitulo().length() > 0) {
                if (p.getIdcliente() > 0) {
                    if (iuds.iud(operante, p) > 0) {
//                      rotina para inserção de tópicos 
                        ProjetoTopico pt; //seta variavel de projeto tópico
                        if (topicosList.size() > 0 && operante == 'u') {//verifica se o projeto esta em modo de edição e se array de tópicos não esta zerado
                            pt = new ProjetoTopico(topicosList.get(0).getId(), p.getId(), topicosList.get(0).isPronto());
                            iudsProjetosTopicos.iud('d', pt);//exclusão de topicos
                            pt = null;
                        }
                        for (int i = 0; i < topicosList.size(); i++) {
                            pt = new ProjetoTopico(topicosList.get(i).getId(), p.getId(), topicosList.get(i).isPronto());  // instancia novo objeto;
                            iudsProjetosTopicos.iud('i', pt);//inserção dos tópicos
                            pt = null; //esvazia objeto
                        }
                        Funcoes.limparCampos(p1);
                        Funcoes.limparCampos(p2);
                        descricao.setText("");
                        descricaoTopico.setText("");
                        new Msg().msgRegistrado(form);
                        tp.setSelectedIndex(0);
                        btSalvar.setText("Novo");
                        popularProjetos("");
                        topicosList = null;
                        topicosList = new ArrayList();
                        populaTopicos();
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
        topicosList = null;
        topicosList = new ArrayList();
        populaTopicos();
        popularProjetos("");
    }
    
    public void addTopico(Topico t) {
        if (!estaNaLista(t)) {
            topicosList.add(t);
        } else {
            new Msg().msgGeneric("O tópico já esta na lista.");
        }
        populaTopicos();
    }
    
    public void removeTopico(int index) {
        if (new Msg().opcaoExcluir(form)) {
            topicosList.remove(index);
        }
        populaTopicos();
    }
    
    public void populaTopicos() {
        
        Object[][] dados = new Object[topicosList.size()][2];
        for (int i = 0; i < topicosList.size(); i++) {
            for (int j = 0; j < 2; j++) {
                switch (j) {
                    case 0:
                        dados[i][j] = topicosList.get(i).getId();
                        break;
                    case 1:
                        dados[i][j] = topicosList.get(i).getTitulo();
                        break;
                }
            }
        }
        topicos.setModel(new DefaultTableModel(dados, new Object[]{"Remover", "Titulo"}));
        new delTopico(topicos, 0);
        TableColumnModel modeloDaColuna = topicos.getColumnModel();
        modeloDaColuna.getColumn(0).setMaxWidth(70);
    }
    
    class editProjeto extends Editar {
        
        public editProjeto(JTable tb, int column) {
            super(tb, column);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            operante = 'u';
            tp.setSelectedIndex(1);
            btSalvar.setText("Salvar");
            TopicosDAO tdao = new TopicosDAO();
            ProjetosDAO pdao = new ProjetosDAO();
            Projeto p = pdao.linha(e.getActionCommand());
            codigo = p.getId();
            descricao.setText(p.getDescricao());
            idCliente = p.getIdcliente();
            cliente.setText(new ClientesDAO().linha(idCliente + "").getNome());
            titulo.setText(p.getTitulo());
            topicosList = tdao.linhas(e.getActionCommand());
            populaTopicos();
        }
    }
    
    class delProjeto extends Excluir {

        public delProjeto(JTable tb, int column) {
            super(tb, column);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ProjetosDAO pdao = new ProjetosDAO();
            ProjetosTopicosDAO tdao = new ProjetosTopicosDAO();
            Projeto p = pdao.linha(e.getActionCommand());
            if(new Msg().opcaoExcluir(form)){
                tdao.iud('d', new ProjetoTopico(0, p.getId(), true));
            if(pdao.iud('d', p)==0){
                new Msg().msgGeneric("Erro ao excluír");
            }
            
            }
            popularProjetos("");
        }
    }
    
    class delTopico extends Excluir
            implements ActionListener {
        
        public delTopico(JTable tb, int column) {
            super(tb, column);
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            removeTopico(topicos.getSelectedRow());
        }
    }
    
    private boolean estaNaLista(Topico t) {
        boolean esta = false;
        for (int i = 0; i < topicosList.size(); i++) {
            if (t.getId() == topicosList.get(i).getId()) {
                esta = true;
            }
        }
        return esta;
    }
    
    public void descreveTopico() {
        int index = topicos.getSelectedRow();
        descricaoTopico.setText(topicosList.get(index).getDescricao());
        
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
    
    public JTextArea getDescricaoTopico() {
        return descricaoTopico;
    }
    
    public void setDescricaoTopico(JTextArea descricaoTopico) {
        this.descricaoTopico = descricaoTopico;
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
        topicos.setRowHeight(24);
        
        this.topicos = topicos;
    }
    
    public JInternalFrame getForm() {
        return form;
    }
    
    public void setForm(JInternalFrame form) {
        this.form = form;
        try {
            form.setMaximum(true);
        } catch (PropertyVetoException e) {
            // Vetoed by internalFrame
            // ... possibly add some handling for this case
        }
//        this.form.setSize(1000, 800);
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
        Funcoes.formataCampo(dataInicio, "##/##/####");
        this.dataInicio = dataInicio;
        
    }
    
    public JFormattedTextField getDataPrevisao() {
        return dataPrevisao;
    }
    
    public void setDataPrevisao(JFormattedTextField dataPrevisao) {
        Funcoes.formataCampo(dataPrevisao, "##/##/####");
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
