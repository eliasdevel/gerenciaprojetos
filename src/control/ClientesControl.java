package control;

import DAO.ClientesDAO;
import DAO.DAO;
import DAO.DesenvolvedoresDAO;
import DAO.EstadosDAO;
import entidadesRelacoes.Cliente;
import entidadesRelacoes.Desenvolvedor;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.TextArea;
import java.sql.ResultSet;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import util.Funcoes;
import view.Msg;
import view.fPrincipal;

/**
 *
 * @author elias
 */
public class ClientesControl {

    JInternalFrame frame;
    ResultSet rs;
    JTabbedPane tp;
    JPanel p;
    JPanel p2;
    JTable tb;
    JLabel codigo;
    JTextField filtro;
    JComboBox estado;
    JTextField nome;
    JFormattedTextField telefone;
    JTextField email;
    JTextArea adicional;
    JDialog cidade;
    JTable cidades;
    JTextField filtroCidade;
    JButton btSalvar;
    JButton btSair;
    String[] idEstado;
    String idsEstado;
    Funcoes f;
    int idCidade;
    char operante;

    public ClientesControl(JInternalFrame frame, JTabbedPane tp, JPanel p, JPanel p2, JTable tb, JLabel codigo, JTextField filtro, JComboBox estado,
            JTextField nome, JFormattedTextField telefone, JTextField email, JTextArea adicional,
            JDialog cidade, JTable cidades, JTextField filtroCidade, JButton btSalvar, JButton btSair) {
        this.frame = frame;

        this.tp = tp;
        this.p = p;
        this.p2 = p2;
        this.tb = tb;
        this.codigo = codigo;
        this.filtro = filtro;
        this.estado = estado;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.adicional = adicional;
        this.cidade = cidade;
        this.cidades = cidades;
        this.filtroCidade = filtroCidade;
//        filtroCidade = new JTextField() {
//            public void filtroCidadeKeyReleased(java.awt.event.ActionEvent evt) {
//            }
//        };

        this.btSalvar = btSalvar;
//        this.btSair.addActionListener(estado);
        this.btSair = btSair;
    }

    public void pupulaComboEstados() {
        f = new Funcoes();
        EstadosDAO dao = new EstadosDAO();

        rs = dao.linhas();
        idsEstado = f.populaComboBox(estado, rs, "nome", "sigla");
        idEstado = idsEstado.split(",");
//        f = null;
        //Obtem o Editor do seu JComboBox  
//        ComboBoxEditor editor = estado.getEditor();
//        //Obtem o componente de edicao do ComboBoxEditor  
//        Component component = editor.getEditorComponent();
//        //Adiciona os ouvintes FocusAdapter, que serao responsaveis por escutar eventos de foco  
//        component.addFocusListener(new java.awt.event.FocusAdapter() {
//            @Override
//            public void focusLost(java.awt.event.FocusEvent evt) {
//            }
//        });
    }
//    private void populaCidades() {
//        Dimension d = new Dimension(467, 200);
//        JTable tbCidades = new JTable();
//        Funcoes.populaTabelaComIndices(tbCidades, "Selecionar,Excluir,Nome", rs, "idcidade,idcidade");
//        cidades.add(tbCidades);
//        cidades.setSize(d);
//        tbCidades.setSize(d);
//    }

    public void acaoBotaoNovoSalvar() {
        int idCat = 0;
        if (this.tp.getSelectedIndex() != 1) {
            operante = 'n';
        } else {
//            idCat = Integer.parseInt(this.idCategoria[cb.getSelectedIndex()]);
            if (operante != 'i') {
                operante = 'u';
            }
        }
        Cliente c = new Cliente(Integer.parseInt(codigo.getText()), 1, nome.getText(), telefone.getText(), email.getText(), adicional.getText());
        ClientesDAO iuds = new ClientesDAO();
        if (operante == 'u' || operante == 'i') {
            if (c.getNome().length() > 0) {
                if (iuds.iud(operante, c) > 0) {
                    Funcoes.limparCampos(p);
                    Funcoes.limparCampos(p2);
                    new Msg().msgRegistrado(frame);
                    tp.setSelectedIndex(0);
                    btSalvar.setText("Novo");
//                    populaDesenvolvedores();
                }
            } else {
                new Msg().msgGeneric("O nome precisa ser preenchido!");
            }
        }
        if (operante == 'n') {
            btSalvar.setText("Salvar");
            tp.setSelectedIndex(1);
            codigo.setText(1 + "");
            operante = 'i';
        }
    }

    public void selecionarClientes() {
    }

    public void acaoBotaoSair() {
        this.frame.setVisible(false);
        this.btSalvar.setText("Novo");
        this.tp.setSelectedIndex(0);
    }

    public void mostraCidades() {
        cidade.setSize(600, 400);
        cidade.setVisible(true);
        ResultSet rs = null;
        DAO d = new DAO(rs);
        rs = d.get("cidades");
        f.populaTabela(cidades, "codigo,codigo,nome, sigla", rs, "idcidade,idcidade,nome,siglaestado");
    }
//    class cidades extends JDialog
}
