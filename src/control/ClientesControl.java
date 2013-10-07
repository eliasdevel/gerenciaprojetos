package control;

import DAO.CidadesDAO;
import DAO.ClientesDAO;
import DAO.DAO;
import DAO.EstadosDAO;
import entidadesRelacoes.Cliente;
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
    JLabel codigo;
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
        popula();
    }

    public void popula() {
        rs = new ClientesDAO().resultado("");
        try {
            rs.first();
            Funcoes.populaTabela(this.tb, "Editar,Nome,Telefone,Email", this.rs, "idcliente,nome,telefone,email");
        } catch (SQLException ex) {
            Logger.getLogger(ClientesControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        new editCli(tb, 0);

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
            Cliente c = new Cliente(Integer.parseInt(codigo.getText()), Integer.parseInt(idcidade), nome.getText(), telefone.getText(), email.getText(), adicional.getText());
            if (c.getNome().length() > 0) {
                if (iuds.iud(operante, c) > 0) {
                    Funcoes.limparCampos(p);
                    Funcoes.limparCampos(p2);
                    new Msg().msgRegistrado(frame);
                    tp.setSelectedIndex(0);
                    btSalvar.setText("Novo");
                    popula();
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

    class editCli extends Editar {

        public editCli(JTable tb, int coluna) {
            super(tb, coluna);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            tp.setSelectedIndex(1);
            codigo.setText(e.getActionCommand() + "");
            operante = 'u';
            f = new Funcoes();
            ClientesDAO dao = new ClientesDAO();
            Cliente c = dao.linha(e.getActionCommand()+"");
            nome.setText(c.getNome() + "");
            telefone.setText(c.getTelefone());
            idcidade = c.getIdcidade() +"";
            cidade.setText(new CidadesDAO().linha(idcidade).getNome());
            email.setText(c.getEmail());
            btSalvar.setText("Salvar");
            

        }
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

    public JLabel getCodigo() {
        return codigo;
    }

    public void setCodigo(JLabel codigo) {
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
