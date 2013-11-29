package control;

import DAO.CategoriasDAO;
import DAO.DesenvolvedoresDAO;
import entidadesRelacoes.Desenvolvedor;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumnModel;
import view.Editar;
import view.Excluir;
import util.Funcoes;
import view.Msg;

/**
 *
 * @author Elias Müller
 * @version 0.1
 * @since 01/08/2013
 *
 */
public class DesenvolvedoresControl {

    char operante;
    ResultSet rs;
    JTabbedPane tp;
    JPanel p;
    JPanel p2;
    JComboBox cb;
    JTextField nome;
    JFormattedTextField telefone;
    JTextField email;
    JInternalFrame form;
    JButton bt;
    JTextField filtro;
    JTable tb;
    String[] idCategoria;
    int codigo;
    String idsCategoria;
    JInternalFrame frame;
    Funcoes f;

    public DesenvolvedoresControl(ResultSet rs, JTabbedPane tp, JComboBox cb, JTextField nome,
            JFormattedTextField telefone, JTextField email, JPanel p, JPanel p2,
            JInternalFrame form, JButton bt, JTextField filtro, JTable tb,
            JInternalFrame frame) {
        this.rs = rs;
        this.frame = frame;
        this.tp = tp;
        this.tp.setSize(600, 500);
        this.cb = cb;
        this.nome = nome;
        Funcoes.formataCampo(telefone, "(##)####-####");
        this.telefone = telefone;
        this.email = email;
        this.p = p;
        this.p2 = p2;
        this.form = form;
        this.form.setSize(600, 500);
        this.bt = bt;
        this.filtro = filtro;
        this.tb = tb;
        this.tb.setSize(600, 500);

    }

    public void pupulaComboCategorias() {
        f = new Funcoes();
        CategoriasDAO cat = new CategoriasDAO();
        rs = cat.resultado("");
        idsCategoria = f.populaComboBox(cb, rs, "titulo", "idcategoria");
        idCategoria = idsCategoria.split(",");
        f = null;
    }

    public void populaDesenvolvedores() {
        String pesquisa = filtro.getText();
        DesenvolvedoresDAO iuds = new DesenvolvedoresDAO();
        rs = iuds.resultado(pesquisa);
        Funcoes.populaTabela(tb, "Exclir,Editar,nome,email,Telefone,categoria", rs, "iddesenvolvedor,iddesenvolvedor,nome,email,telefone,categoria");
        new editDes(tb, 1);
        new delDes(tb, 0);
    }

    public void acaoBotaoNovoSalvar() {
        int idCat = 0;
        if (this.tp.getSelectedIndex() != 1) {
            operante = 'n';
        } else {
            idCat = Integer.parseInt(this.idCategoria[cb.getSelectedIndex()]);
            if (operante != 'i') {
                operante = 'u';
            }
        }
        Desenvolvedor d = new Desenvolvedor(codigo,
                idCat, nome.getText(),
                telefone.getText(),
                email.getText());

        DesenvolvedoresDAO iuds = new DesenvolvedoresDAO();
        if (operante == 'u' || operante == 'i') {
            if (d.getNome().length() > 0) {
                if (iuds.iud(operante, d) > 0) {
                    Funcoes.limparCampos(p);
                    Funcoes.limparCampos(p2);
                    new Msg().msgRegistrado(form);
                    tp.setSelectedIndex(0);
                    bt.setText("Novo");
                     nome.setBackground(new Color(255, 255, 255));
                    populaDesenvolvedores();
                }
            } else {

                nome.setBackground(new Color(150, 0, 0));
                nome.requestFocus();
                new Msg().msgGeneric("O nome precisa ser preenchido!");
            }
        }
        if (operante == 'n') {
            Funcoes.limparCampos(p);
            Funcoes.limparCampos(p2);
            bt.setText("Salvar");
            tp.setSelectedIndex(1);

            operante = 'i';
        }
    }

    public void acaoCancelar() {
        tp.setSelectedIndex(0);
        nome.setBackground(new Color(255, 255, 255));

        Funcoes.limparCampos(p);
        Funcoes.limparCampos(p2);
        bt.setText("Novo");
        operante = 'n';

    }

    public void acaoSair() {
        acaoCancelar();
        frame.setVisible(false);
    }

    class editDes extends Editar
            implements ActionListener {

        public editDes(JTable tb, int column) {
            super(tb, column);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            aqui vão todas as ações quendo vc clicar no botão aqui tem algumas classes que eu uso no meu sistema
            
            tp.setSelectedIndex(1);
            codigo = Integer.parseInt(e.getActionCommand());//código da célula
            operante = 'u';
            f = new Funcoes();
            DesenvolvedoresDAO pes = new DesenvolvedoresDAO();
            Desenvolvedor des = pes.linha(e.getActionCommand() + "");
            nome.setText(des.getNome() + "");
            telefone.setText(des.getTelefone());
            email.setText(des.getEmail());
            bt.setText("Salvar");
            f.selecionaIndiceCombo(cb, idCategoria, des.getIdCategoria() + "");

            //JOptionPane.showMessageDialog(null, des.getIdCategoria()+idCategoria[2]);
        }
    }
    public void adicionarBotao(){
        int coluna = 0;
        JTable suajtable = new JTable();
        
        new editDes(suajtable, coluna);
    }

    class delDes extends Excluir
            implements ActionListener {

        public delDes(JTable tb, int column) {
            super(tb, column);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
           
            if (new Msg().opcaoExcluir(p)) {

                DesenvolvedoresDAO dao = new DesenvolvedoresDAO();
                if(!dao.estaEmProjeto(e.getActionCommand())){
                dao.iud('d', new Desenvolvedor(Integer.parseInt(e.getActionCommand()), 0, "", "", ""));
                }else{
                    new Msg().msgGeneric("O desenvolvedor está vinculado a um projeto, não pode ser excluido!");
                }
                dao = null;
            }
            populaDesenvolvedores();
        }
    }
}
