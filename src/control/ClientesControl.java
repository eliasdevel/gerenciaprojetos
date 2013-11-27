package control;

import DAO.CidadesDAO;
import DAO.ClientesDAO;
import DAO.DAO;
import DAO.EstadosDAO;
import entidadesRelacoes.Cliente;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import view.Editar;
import view.Excluir;
import view.Msg;

/**
 *
 * @author elias
 */
public final class ClientesControl {

    JInternalFrame frame;
    ResultSet rs;
    JTabbedPane tp;
    JPanel p;
    JPanel p2;
    JTable tb;
    int codigo;
    JTextField filtro;
    JComboBox estado;
    JTextField nome;
    JFormattedTextField telefone;
    JTextField email;
    JTextField cidade;
    JTextArea adicional;
    String idcidade;
    JTable cidades;
    JTextField filtroCidade;
    JButton btSalvar;
    JButton btSair;
    String[] idEstado;
    String idsEstado;
    Funcoes f;
    char operante;

    public ClientesControl(JTable tb) {


        this.tb = tb;

    }

    public void popula() {
     
        rs = new ClientesDAO().resultado(filtro.getText());
        try {
            rs.first();
            Funcoes.populaTabela(this.tb, "Excluír,Editar,Nome,Telefone,Email", this.rs, "idcliente,idcliente,nome,telefone,email");
        } catch (SQLException ex) {
            Logger.getLogger(ClientesControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        new delCli(tb, 0);
        new editCli(tb, 1);


    }

    public void acaoBotaoNovoSalvar() {


        int idCat = 0;
        if (this.tp.getSelectedIndex() != 1) {
            operante = 'n';
        } else {
            if (operante != 'i') {
                operante = 'u';
            }
        }
        ClientesDAO iuds = new ClientesDAO();
        if (operante == 'u' || operante == 'i') {
            if (nome.getText().length() > 0) {
                if (cidade.getText().length() > 0) {
                    Cliente c = new Cliente(codigo, Integer.parseInt(idcidade), nome.getText(), telefone.getText(), email.getText(), adicional.getText());
                    if (iuds.iud(operante, c) > 0) {
                        Funcoes.limparCampos(p);
                        Funcoes.limparCampos(p2);
                        adicional.setText("");
                        new Msg().msgRegistrado(frame);
                        tp.setSelectedIndex(0);
                        btSalvar.setText("Novo");
                        popula();
                        branco();
                    }
                } else {
                    new Msg().msgGeneric("A Cidade precisa ser preenchida!");
                    cidade.requestFocus();
                    cidade.setBackground(new Color(150, 0, 0));
                }
            } else {
                new Msg().msgGeneric("O nome precisa ser preenchido!");
                nome.requestFocus();
                nome.setBackground(new Color(150, 0, 0));

            }
        }
        if (operante == 'n') {
            btSalvar.setText("Salvar");
            tp.setSelectedIndex(1);
            codigo = 1;
            operante = 'i';
        }
    }

    public void selecionarClientes() {
    }

    public void acaoCancelar() {
        popula();
        this.btSalvar.setText("Novo");
        Funcoes.limparCampos(p);
        Funcoes.limparCampos(p2);
        this.tp.setSelectedIndex(0);
        branco();
    }

    public void acaoBotaoSair() {
        acaoCancelar();
        this.frame.setVisible(false);
    }

    class editCli extends Editar {

        public editCli(JTable tb, int coluna) {
            super(tb, coluna);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            tp.setSelectedIndex(1);
            codigo = Integer.parseInt(e.getActionCommand());
            operante = 'u';
            f = new Funcoes();
            ClientesDAO dao = new ClientesDAO();
            Cliente c = dao.linha(e.getActionCommand() + "");
            nome.setText(c.getNome() + "");
            telefone.setText(c.getTelefone());
            idcidade = c.getIdcidade() + "";
            adicional.setText(c.getAdicional());
            cidade.setText(new CidadesDAO().linha(idcidade).getNome());
            email.setText(c.getEmail());
            btSalvar.setText("Salvar");
        }
    }

    class delCli extends Excluir {

        public delCli(JTable table, int column) {
            super(table, column);
        }

        public void actionPerformed(ActionEvent e) {
            ClientesDAO dao = new ClientesDAO();
            Cliente c = dao.linha(e.getActionCommand());
            if (!dao.estaEmProjeto(e.getActionCommand())) {
                if (new Msg().opcaoExcluir(p)) {
                    dao.iud('d', c);
                }
            } else {
                new Msg().msgGeneric("Este Cliente pertence a um projeto Não pode ser excluído");
            }
            popula();
        }
    }

    public void branco() {
        nome.setBackground(new Color(255, 255, 255));
        cidade.setBackground(new Color(255, 255, 255));

    }

    public JInternalFrame getFrame() {
        return frame;
    }

    public void setFrame(JInternalFrame frame) {
        frame.setSize(500, 600);
        this.frame = frame;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public JTabbedPane getTp() {
        return tp;
    }

    public void setTp(JTabbedPane tp) {
        this.tp = tp;
    }

    public JPanel getP() {
        return p;
    }

    public void setP(JPanel p) {
        this.p = p;
    }

    public JPanel getP2() {
        return p2;
    }

    public void setP2(JPanel p2) {
        this.p2 = p2;
    }

    public JTable getTb() {
        return tb;
    }

    public void setTb(JTable tb) {
        this.tb = tb;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public JTextField getFiltro() {
        return filtro;
    }

    public void setFiltro(JTextField filtro) {
        this.filtro = filtro;
    }

    public JComboBox getEstado() {
        return estado;
    }

    public void setEstado(JComboBox estado) {
        this.estado = estado;
    }

    public JTextField getNome() {
        return nome;
    }

    public void setNome(JTextField nome) {
        this.nome = nome;
    }

    public JFormattedTextField getTelefone() {
        return telefone;
    }

    public void setTelefone(JFormattedTextField telefone) {
        Funcoes.formataCampo(telefone, "(**)****-****");
        this.telefone = telefone;
    }

    public JTextField getEmail() {
        return email;
    }

    public void setEmail(JTextField email) {
        this.email = email;
    }

    public JTextArea getAdicional() {
        return adicional;
    }

    public void setAdicional(JTextArea adicional) {
        this.adicional = adicional;
    }

    public JTable getCidades() {
        return cidades;
    }

    public void setCidades(JTable cidades) {
        this.cidades = cidades;
    }

    public JTextField getFiltroCidade() {
        return filtroCidade;
    }

    public void setFiltroCidade(JTextField filtroCidade) {
        this.filtroCidade = filtroCidade;
    }

    public JButton getBtSalvar() {
        return btSalvar;
    }

    public void setBtSalvar(JButton btSalvar) {
        this.btSalvar = btSalvar;
    }

    public JButton getBtSair() {
        return btSair;
    }

    public void setBtSair(JButton btSair) {
        this.btSair = btSair;
    }

    public String[] getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(String[] idEstado) {
        this.idEstado = idEstado;
    }

    public String getIdsEstado() {
        return idsEstado;
    }

    public void setIdsEstado(String idsEstado) {
        this.idsEstado = idsEstado;
    }

    public char getOperante() {
        return operante;
    }

    public void setOperante(char operante) {
        this.operante = operante;
    }

    public JTextField getCidade() {
        return this.cidade;
    }

    public void setCidade(JTextField cidade) {
        this.cidade = cidade;
    }

    public String getIdcidade() {
        return idcidade;
    }

    public void setIdcidade(String idcidade) {
        this.idcidade = idcidade;
    }
}
