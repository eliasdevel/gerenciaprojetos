/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.ClientesDAO;
import DAO.DesenvolvedoresDAO;
import DAO.ProjetoDesenvolvedorDAO;
import DAO.ProjetosDAO;
import DAO.ProjetosTopicosDAO;
import DAO.TopicosDAO;
import entidadesRelacoes.Desenvolvedor;
import entidadesRelacoes.Projeto;
import entidadesRelacoes.ProjetoDesenvolvedor;
import entidadesRelacoes.ProjetoTopico;
import entidadesRelacoes.Topico;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import util.Data;
import util.Funcoes;
import view.Concluir;
import view.Editar;
import view.Excluir;
import view.Msg;

/**
 *
 * @author elias
 */
public class ProjetosControl {

    Funcoes f = new Funcoes();
    char operante;
    char operanteTopicos;
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
    ButtonGroup bgTopicos;
    JTextField tituloTopico;
    JButton btSalvarTopicos;
    JTextArea descricaoTopico;
    JTextArea descricaoTopicoCad;
    JTabbedPane tpTopicos;
    JPanel p1Topicos;
    JPanel p2Topicos;
    JComboBox desenvolvedorTopico;
    String[] idsDesenvolvedorTopico;
    ArrayList<Topico> topicosList = new ArrayList();
    ArrayList<Topico> topicosListRemovidos = new ArrayList();
    ArrayList<Desenvolvedor> desenvolvedorList = new ArrayList();
    int codigo;
    int codigoTopico;
    ResultSet rs;
//    Filtros
    JTextField filtroDes;
    int filtroIdDes;
    int filtroIdCli;
    JTextField filtroCli;
    JTextField filtroNom;
    JFormattedTextField filtroData1;
    JFormattedTextField filtroData2;
    SituacaoControl sitControl;
    JRadioButton rTodosTopicos;
    JRadioButton rTesteTopicos;
    JRadioButton rPlanejadosTopicos;
    JRadioButton rCriadosTopicos;
    JRadioButton rDesenvolvimentoTopicos;
    JRadioButton rfinalizadosTopicos;

    public void popularProjetos() {
        Data data1 = new Data(1, 1, 1);
        Data data2 = new Data(1, 1, 1);
        if (filtroData1.getText().trim().length() == 10) {
            data1.setData(filtroData1.getText());
        }
        if (filtroData2.getText().trim().length() == 10) {

            data2.setData(filtroData2.getText());
        }
        rs = new ProjetosDAO().resultado(filtroNom.getText(), filtroIdCli, filtroIdDes, data1, data2);

        Funcoes.populaTabela(tb, "Excluír,Editar,Titulo,Cliente,Descrição", rs, "idprojeto,idprojeto,titulo,cliente,descricao");
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
            ProjetoDesenvolvedorDAO iudsprProjetosDesenvolvedor = new ProjetoDesenvolvedorDAO();
            Projeto p = new Projeto(codigo, idCliente, titulo.getText(), descricao.getText(), false);
            p.setDataInicial(dataInicio.getText());
            p.setDataPrevisao(dataPrevisao.getText());
            if (p.getTitulo().length() > 0) {
                if (p.getIdcliente() > 0) {
                    if (iuds.iud(operante, p) > 0) {
                        retornaFiltrados();

//                      rotina para inserção de tópicos e desenvolvedores
                        ProjetoDesenvolvedor pd; //variável do projeto desenvolvedor
                        ProjetoTopico pt; //seta variavel de projeto tópico

                        if (operante == 'u') {//verifica se o projeto esta em modo de edição 
                            pt = new ProjetoTopico(0, p.getId(), false, 'd', 0);
                            iudsProjetosTopicos.iud('d', pt);//exclusão de topicos
                            pt = null;
                            pd = new ProjetoDesenvolvedor(p.getId(), 0);
                            iudsprProjetosDesenvolvedor.iud('d', pd);//exclusão de desenvolvedores
                            pd = null;
                        }
                        for (int i = 0; i < topicosList.size(); i++) {
                            pt = new ProjetoTopico(topicosList.get(i).getId(), p.getId(), topicosList.get(i).isPronto(), topicosList.get(i).getSituacao(), topicosList.get(i).getIdDesenvolvedor());  // instancia novo objeto;
                            iudsProjetosTopicos.iud('i', pt);//inserção dos tópicos
                            pt = null; //esvazia objeto
                        }
                        for (int i = 0; i < desenvolvedorList.size(); i++) {
                            pd = new ProjetoDesenvolvedor(p.getId(), desenvolvedorList.get(i).getId());  // instancia novo objeto;
                            iudsprProjetosDesenvolvedor.iud('i', pd);//inserção dos desenvolvedores
                            pt = null; //esvazia objeto
                        }
                        Funcoes.limparCampos(p1);
                        Funcoes.limparCampos(p2);
                        descricao.setText("");
                        descricaoTopico.setText("");
                        new Msg().msgRegistrado(form);
                        tp.setSelectedIndex(0);
                        btSalvar.setText("Novo");
                        popularProjetos();
                        topicosList = new ArrayList();
                        topicosListRemovidos = new ArrayList();
                        desenvolvedorList = new ArrayList();
                        if (bgTopicos.getSelection() != null) {
                            bgTopicos.getSelection().setSelected(false);

                        }
                        rTodosTopicos.setSelected(true);
                        populaTopicos();
                        populaDesenvolvedores();
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

    public void acaoBotaoNovoTopico() {
        if (desenvolvedores.getRowCount() > 0) {
            if (this.tpTopicos.getSelectedIndex() != 1) {
                operanteTopicos = 'n';
            } else {
                if (operanteTopicos != 'i') {
                    operanteTopicos = 'u';
                }
            }
            if (operanteTopicos == 'u' || operanteTopicos == 'i') {
                TopicosDAO iudsTopicos = new TopicosDAO();
                if (tituloTopico.getText().length() != 0) {
                    Topico t = new Topico(codigoTopico, tituloTopico.getText(), descricaoTopicoCad.getText(), false, 'c', desenvolvedorList.get(desenvolvedorTopico.getSelectedIndex()).getId());
                    if (iudsTopicos.iud(operanteTopicos, t) > 0) {
                        new Msg().msgRegistrado(form);
                        if (operanteTopicos == 'i') {
                            addTopico(t);
                        } else {
                            topicosList.get(topicos.getSelectedRow()).setDescricao(t.getDescricao());
                            topicosList.get(topicos.getSelectedRow()).setTitulo(t.getTitulo());
                            topicosList.get(topicos.getSelectedRow()).setIdDesenvolvedor(t.getIdDesenvolvedor());
                        }
                        populaTopicos();
                        tpTopicos.setSelectedIndex(0);
                        btSalvarTopicos.setText("Novo");
                        Funcoes.limparCampos(p1Topicos);
                        descricaoTopicoCad.setText("");
                        Funcoes.limparCampos(p2Topicos);
                    }
                } else {
                    new Msg().msgGeneric("O Título precisa ser preenchido");
                    tituloTopico.requestFocus();
                }
            }
            if (operanteTopicos == 'n') {
                btSalvarTopicos.setText("Salvar");
                tpTopicos.setSelectedIndex(1);
                operanteTopicos = 'i';
            }
        } else {
            new Msg().msgGeneric("Não tem nenhum desenvolvedor vinculado ao projeto, adicione desenvolvedores antes de criar tópicos.");
        }
    }

    public void acaoSair() {
        acaoCancelar();
        form.setVisible(false);
    }

    public void acaoCancelar() {
        rTodosTopicos.setSelected(true);
        tp.setSelectedIndex(0);
        Funcoes.limparCampos(p1);
        Funcoes.limparCampos(p2);
        descricao.setText("");
        descricaoTopico.setText("");
        bt.setText("Novo");
        operante = 'n';
        topicosList = null;
        desenvolvedorList = null;
        desenvolvedorList = new ArrayList();
        topicosList = new ArrayList();
        topicosListRemovidos = new ArrayList();
        populaDesenvolvedores();
        populaTopicos();
        popularProjetos();
        cancelarTopico();
    }

    public void cancelarTopico() {

        tpTopicos.setSelectedIndex(0);
        btSalvarTopicos.setText("Novo");
        Funcoes.limparCampos(p1Topicos);
        Funcoes.limparCampos(p2Topicos);
        descricaoTopicoCad.setText("");
        populaTopicos();
    }

    public void addTopico(Topico t) {
        if (!estaNaLista(t)) {
            topicosList.add(t);
        } else {
            new Msg().msgGeneric("O tópico já esta na lista.");
        }
        populaTopicos();
    }

    public void addDesenvolvedor(Desenvolvedor d) {
        if (!estaNaListaDesenvolvedor(d)) {
            desenvolvedorTopico.addItem(d.getNome());
            desenvolvedorList.add(d);
        } else {
            new Msg().msgGeneric("O desenvolvedor já esta na lista.");
        }
        populaDesenvolvedores();
    }

    public void removeTopico(int index) {
        if (new Msg().opcaoExcluir(form)) {

            topicosList.remove(index);
        }
        populaTopicos();
    }

    public void populaTopicos() {
        Object[][] dados = new Object[topicosList.size()][5];
        for (int i = 0; i < topicosList.size(); i++) {
            for (int j = 0; j < 5; j++) {
                switch (j) {
                    case 0:
                        dados[i][j] = topicosList.get(i).getId();
                        break;
                    case 1:
                        dados[i][j] = topicosList.get(i).getId();
                        break;
                    case 2:
                        dados[i][j] = topicosList.get(i).getSituacao();
                        break;
                    case 3:
                        dados[i][j] = new DesenvolvedoresDAO().linha(topicosList.get(i).getIdDesenvolvedor() + "").getNome();
                        break;
                    case 4:
                        dados[i][j] = topicosList.get(i).getTitulo();
                        break;
                }
            }
        }

        topicos.setModel(new DefaultTableModel(dados, new Object[]{"Editar", "Remover", "Situação", "Responsável", "Titulo"}));
        new editTopico(topicos, 0);
        new delTopico(topicos, 1);
        new concluirTopico(topicos, 2);
        TableColumnModel modeloDaColuna = topicos.getColumnModel();
        modeloDaColuna.getColumn(0).setMaxWidth(75);
        modeloDaColuna.getColumn(0).setMinWidth(75);
        modeloDaColuna.getColumn(1).setMaxWidth(75);
        modeloDaColuna.getColumn(1).setMinWidth(75);
        modeloDaColuna.getColumn(2).setMaxWidth(75);
        modeloDaColuna.getColumn(2).setMinWidth(75);
        modeloDaColuna.getColumn(3).setMaxWidth(150);
        modeloDaColuna.getColumn(3).setMinWidth(150);
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
            DesenvolvedoresDAO ddao = new DesenvolvedoresDAO();

            TopicosDAO tdao = new TopicosDAO();
            ProjetosDAO pdao = new ProjetosDAO();
            Projeto p = pdao.linha(e.getActionCommand());
            codigo = p.getId();
            descricao.setText(p.getDescricao());
            idCliente = p.getIdcliente();
            dataInicio.setText(p.getDataInicial());
            dataPrevisao.setText(p.getDataPrevisao());
            cliente.setText(new ClientesDAO().linha(idCliente + "").getNome());
            titulo.setText(p.getTitulo());
            topicosList = tdao.linhas(e.getActionCommand());
            desenvolvedorList = ddao.linhas(e.getActionCommand());
            populaDesenvolvedores();
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
            ProjetoDesenvolvedorDAO ptDao = new ProjetoDesenvolvedorDAO();
            Projeto p = pdao.linha(e.getActionCommand());
            if (new Msg().opcaoExcluir(form)) {
                tdao.iud('d', new ProjetoTopico(0, p.getId(), false, 'p', 0));
                ptDao.iud('d', new ProjetoDesenvolvedor(p.getId(), 0));
                if (pdao.iud('d', p) == 0) {
                    new Msg().msgGeneric("Erro ao excluír");
                }

            }
            popularProjetos();
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

    class editTopico extends Editar {

        public editTopico(JTable table, int column) {
            super(table, column);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            operanteTopicos = 'u';
            TopicosDAO dao = new TopicosDAO();
            ProjetosTopicosDAO ptdao = new ProjetosTopicosDAO();
            Topico t = dao.linha(e.getActionCommand());
            for (int i = 0; i < desenvolvedorList.size(); i++) {
                if (desenvolvedorList.get(i).getId() == topicosList.get(topicos.getSelectedRow()).getIdDesenvolvedor()) {
                    desenvolvedorTopico.setSelectedIndex(i);
                }
            }
            if (ptdao.estaEmDoisProjetos(e.getActionCommand())) {
                if (new Msg().opcaoDuplicado(form)) {
                    editar(t);
                } else {
                    populaTopicos();
                }
            } else {
                editar(t);
            }
        }

        public void editar(Topico t) {
            codigoTopico = t.getId();
            tituloTopico.setText(t.getTitulo());
            descricaoTopicoCad.setText(t.getDescricao());
            tpTopicos.setSelectedIndex(1);
            btSalvarTopicos.setText("Salvar");
        }
    }

    class concluirTopico extends Concluir {

        public concluirTopico(JTable tb, int column) {
            super(tb, column);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            sitControl.selectIndex(topicosList.get(topicos.getSelectedRow()).getSituacao());
            topicosList.get(topicos.getSelectedRow()).setSituacao(sitControl.getSituacao());

            System.out.println(topicosList.get(topicos.getSelectedRow()).getSituacao());
            populaTopicos();
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
        if (index >= 0) {
            descricaoTopico.setText(topicosList.get(index).getDescricao());
        }

    }

//    Desenvolvedores
    public void populaDesenvolvedores() {
        desenvolvedorTopico.removeAllItems();
        Object[][] dados = new Object[desenvolvedorList.size()][3];
        for (int i = 0; i < desenvolvedorList.size(); i++) {
            desenvolvedorTopico.addItem(desenvolvedorList.get(i).getNome());
            for (int j = 0; j < 4; j++) {
                switch (j) {
                    case 0:
                        dados[i][j] = desenvolvedorList.get(i).getId();
                        break;
                    case 1:
                        dados[i][j] = desenvolvedorList.get(i).getNome();
                        break;
                }
            }
        }
        desenvolvedores.setModel(new DefaultTableModel(dados, new Object[]{"Remover", "Nome"}));
        new delDesenvolvedor(desenvolvedores, 0);
        TableColumnModel modeloDaColuna = desenvolvedores.getColumnModel();
        modeloDaColuna.getColumn(0).setMaxWidth(75);
        modeloDaColuna.getColumn(0).setMinWidth(75);
    }

    private boolean estaNaListaDesenvolvedor(Desenvolvedor d) {
        boolean esta = false;
        for (int i = 0; i < desenvolvedorList.size(); i++) {
            if (d.getId() == desenvolvedorList.get(i).getId()) {
                esta = true;
            }
        }
        return esta;
    }

    class delDesenvolvedor extends Excluir
            implements ActionListener {

        public delDesenvolvedor(JTable tb, int column) {
            super(tb, column);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            removeDesenvolvedor(desenvolvedores.getSelectedRow());
        }
    }

    public void removeDesenvolvedor(int index) {
        boolean vinculado = false;
//       verificações para ver se está vinculado
        for (int i = 0; i < topicosList.size(); i++) {
            if (topicosList.get(i).getIdDesenvolvedor() == desenvolvedorList.get(index).getId()) {
                vinculado = true;
            }
        }

        for (int i = 0; i < topicosListRemovidos.size(); i++) {
            if (topicosListRemovidos.get(i).getIdDesenvolvedor() == desenvolvedorList.get(index).getId()) {
                vinculado = true;
            }
        }

        if (!vinculado) {
            if (new Msg().opcaoExcluir(form)) {
                desenvolvedorList.remove(index);
            }
        } else {
            new Msg().msgGeneric("O desenvolvedor está vinculado aos tópicos, não pode ser removido.");
        }
        populaDesenvolvedores();
    }

    public SituacaoControl getSitControl() {
        return sitControl;
    }

    public void setSitControl(SituacaoControl sitControl) {
        this.sitControl = sitControl;
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
        descricaoTopico.setEditable(false);
        descricaoTopico.setLineWrap(true);
        descricaoTopico.setWrapStyleWord(true);

        this.descricaoTopico = descricaoTopico;
    }

    public void setTb(JTable tb) {
        this.tb = tb;
    }

    public JTable getDesenvolvedores() {
        return desenvolvedores;
    }

    public void setDesenvolvedores(JTable desenvolvedores) {
        desenvolvedores.setRowHeight(24);
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
            new Msg().msgGeneric("Erro de definição no tamanho do formulário " + e);
        }

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

    public JTextArea getDescricaoTopicoCad() {
        return descricaoTopicoCad;
    }

    public void setDescricaoTopicoCad(JTextArea descricaoTopicoCad) {
        descricaoTopicoCad.setLineWrap(true);
        descricaoTopicoCad.setWrapStyleWord(true);
        this.descricaoTopicoCad = descricaoTopicoCad;
    }

    public JTextField getTituloTopico() {
        return tituloTopico;
    }

    public void setTituloTopico(JTextField tituloTopico) {
        this.tituloTopico = tituloTopico;
    }

    public JButton getBtSalvarTopicos() {
        return btSalvarTopicos;
    }

    public void setBtSalvarTopicos(JButton btSalvarTopicos) {
        this.btSalvarTopicos = btSalvarTopicos;
    }

    public JTabbedPane getTpTopicos() {
        return tpTopicos;
    }

    public void setTpTopicos(JTabbedPane tpTopicos) {
        this.tpTopicos = tpTopicos;
    }

    public JPanel getP1Topicos() {
        return p1Topicos;
    }

    public void setP1Topicos(JPanel p1Topicos) {
        this.p1Topicos = p1Topicos;
    }

    public JPanel getP2Topicos() {
        return p2Topicos;
    }

    public void setP2Topicos(JPanel p2Topicos) {
        this.p2Topicos = p2Topicos;
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
        descricao.setLineWrap(true);
        descricao.setWrapStyleWord(true);
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

    public JTextField getFiltroDes() {
        return filtroDes;
    }

    public void setFiltroDes(JTextField filtroDes) {
        this.filtroDes = filtroDes;
    }

    public JTextField getFiltroCli() {
        return filtroCli;
    }

    public void setFiltroCli(JTextField filtroCli) {
        this.filtroCli = filtroCli;
    }

    public JTextField getFiltroNom() {
        return filtroNom;
    }

    public void setFiltroNom(JTextField filtroNom) {
        this.filtroNom = filtroNom;
    }

    public JFormattedTextField getFiltroData1() {
        return filtroData1;
    }

    public void setFiltroData1(JFormattedTextField filtroData1) {
        Funcoes.formataCampo(filtroData1, "##/##/####");
        this.filtroData1 = filtroData1;
    }

    public JFormattedTextField getFiltroData2() {
        return filtroData2;
    }

    public void setFiltroData2(JFormattedTextField filtroData2) {
        Funcoes.formataCampo(filtroData2, "##/##/####");
        this.filtroData2 = filtroData2;
    }

    public int getFiltroIdDes() {
        return filtroIdDes;
    }

    public void setFiltroIdDes(int filtroIdDes) {
        this.filtroIdDes = filtroIdDes;
    }

    public int getFiltroIdCli() {
        return filtroIdCli;
    }

    public void setFiltroIdCli(int filtroIdCli) {
        this.filtroIdCli = filtroIdCli;
    }

    public ButtonGroup getBgTopicos() {
        return bgTopicos;
    }

    public void setBgTopicos(ButtonGroup bgTopicos) {

        this.bgTopicos = bgTopicos;
    }

    public JRadioButton getrTodosTopicos() {
        return rTodosTopicos;
    }

    public void setrTodosTopicos(JRadioButton rTodosTopicos) {
        rTodosTopicos.addActionListener(new AcaoRadioTopicosPro());
        this.rTodosTopicos = rTodosTopicos;
    }

    public JRadioButton getrTesteTopicos() {
        return rTesteTopicos;
    }

    public void setrTesteTopicos(JRadioButton rTesteTopicos) {
        rTesteTopicos.addActionListener(new AcaoRadioTopicosPro());
        this.rTesteTopicos = rTesteTopicos;
    }

    public JRadioButton getrPlanejadosTopicos() {
        return rPlanejadosTopicos;
    }

    public void setrPlanejadosTopicos(JRadioButton rPlanejadosTopicos) {
        rPlanejadosTopicos.addActionListener(new AcaoRadioTopicosPro());
        this.rPlanejadosTopicos = rPlanejadosTopicos;
    }

    public JRadioButton getrCriadosTopicos() {
        return rCriadosTopicos;
    }

    public JComboBox getDesenvolvedorTopico() {
        return desenvolvedorTopico;
    }

    public void setDesenvolvedorTopico(JComboBox desenvolvedorTopico) {

        DesenvolvedoresDAO dDao = new DesenvolvedoresDAO();
//      idsDesenvolvedorTopico = f.populaComboBox(desenvolvedorTopico, dDao.resultado(""), "nome","iddesenvolvedor").split(",");
        this.desenvolvedorTopico = desenvolvedorTopico;

    }

    public void setrCriadosTopicos(JRadioButton rCriadosTopicos) {
        rCriadosTopicos.addActionListener(new AcaoRadioTopicosPro());
        this.rCriadosTopicos = rCriadosTopicos;
    }

    public JRadioButton getrDesenvolvimentoTopicos() {
        return rDesenvolvimentoTopicos;
    }

    public void setrDesenvolvimentoTopicos(JRadioButton rDesenvolvimentoTopicos) {
        rDesenvolvimentoTopicos.addActionListener(new AcaoRadioTopicosPro());
        this.rDesenvolvimentoTopicos = rDesenvolvimentoTopicos;
    }

    public JRadioButton getRfinalizadosTopicos() {
        return rfinalizadosTopicos;
    }

    public void setRfinalizadosTopicos(JRadioButton rfinalizadosTopicos) {
        rfinalizadosTopicos.addActionListener(new AcaoRadioTopicosPro());
        this.rfinalizadosTopicos = rfinalizadosTopicos;
    }

    public class AcaoRadioTopicosPro implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            retornaFiltrados();
            if (!e.getActionCommand().equals("a")) {
                filtroTopicos();
            }

            populaTopicos();
        }
    }

    public void retornaFiltrados() {
        for (int i = 0; i < topicosListRemovidos.size(); i++) {
            topicosList.add(topicosListRemovidos.get(i));//Retorna removidos a lista de tópicos
        }
        topicosListRemovidos = new ArrayList();

    }

    public void filtroTopicos() {
        char situacao = bgTopicos.getSelection().getActionCommand().charAt(0);
        int posicao = 0;
        while (posicao != topicosList.size()) {
            if (topicosList.size() > 0) {
                if (topicosList.get(posicao).getSituacao() != situacao) {
                    topicosListRemovidos.add(topicosList.get(posicao));
                    topicosList.remove(posicao);
                } else {
                    posicao++;
                }
            }
        }

    }
}
