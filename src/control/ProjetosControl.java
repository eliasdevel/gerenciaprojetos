/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import entidadesRelacoes.Projeto;
import entidadesRelacoes.ProjetoDesenvolvedor;
import entidadesRelacoes.ProjetoTopico;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
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
    
    JInternalFrame form;

    JTextField nome;
    JFormattedTextField data;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//     public void acaoBotaoNovoSalvar() {
//        int idCat = 0;
//        if (this.tp.getSelectedIndex() != 1) {
//            operante = 'n';
//        } else {
//            idCat = Integer.parseInt(this.idCategoria[cb.getSelectedIndex()]);
//            if (operante != 'i') {
//                operante = 'u';
//            }
//        }
//        Desenvolvedor d = new Desenvolvedor(Integer.parseInt(codigo.getText()),
//                idCat, nome.getText(),
//                telefone.getText(),
//                email.getText());
//
//        DesenvolvedoresDAO iuds = new DesenvolvedoresDAO();
//        if (operante == 'u' || operante == 'i') {
//            if (d.getNome().length() > 0) {
//                if (iuds.iud(operante, d) > 0) {
//                    Funcoes.limparCampos(p);
//                    Funcoes.limparCampos(p2);
//                    new Msg().msgRegistrado(form);
//                    tp.setSelectedIndex(0);
//                    bt.setText("Novo");
//                    populaDesenvolvedores();
//                }
//            } else {
//                new Msg().msgGeneric("O nome precisa ser preenchido!");
//            }
//        }
//        if (operante == 'n') {
//            Funcoes.limparCampos(p);
//            Funcoes.limparCampos(p2);
//            bt.setText("Salvar");
//            tp.setSelectedIndex(1);
//            codigo.setText(1 + "");
//            operante = 'i';
//        }
//    }
//
//    public void acaoSair() {
//        tp.setSelectedIndex(0);
//        frame.setVisible(false);
//        cb.removeAllItems();
//        Funcoes.limparCampos(p);
//        Funcoes.limparCampos(p2);
//        bt.setText("Novo");
//        operante = 'n';
//    }
//
//    class editDes extends Editar
//            implements ActionListener {
//
//        public editDes(JTable tb, int column) {
//            super(tb, column);
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            tp.setSelectedIndex(1);
//            codigo.setText(e.getActionCommand() + "");
//            operante = 'u';
//            f = new Funcoes();
//            DesenvolvedoresDAO pes = new DesenvolvedoresDAO();
//            Desenvolvedor des = pes.linha(e.getActionCommand() + "");
//            nome.setText(des.getNome() + "");
//            telefone.setText(des.getTelefone());
//            email.setText(des.getEmail());
//            bt.setText("Salvar");
//            f.selecionaIndiceCombo(cb, idCategoria, des.getIdCategoria() + "");
//            
//            //JOptionPane.showMessageDialog(null, des.getIdCategoria()+idCategoria[2]);
//        }
//    }
//
//    class delDes extends Excluir
//            implements ActionListener {
//
//        public delDes(JTable tb, int column) {
//            super(tb, column);
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (new Msg().opcaoExcluir(p)) {
//
//                DesenvolvedoresDAO dao = new DesenvolvedoresDAO();
//                dao.iud('d', new Desenvolvedor(Integer.parseInt(e.getActionCommand()), 0, "", "", ""));
//                populaDesenvolvedores();
//                dao = null;
//            }
//                populaDesenvolvedores();
//        }
//    }

   
    public JInternalFrame getForm() {
        return form;
    }

    public void setForm(JInternalFrame form) {
        this.form = form;
    }

    public JTextField getNome() {
        return nome;
    }

    public void setNome(JTextField nome) {
        this.nome = nome;
    }

    public JFormattedTextField getData() {
        return data;
    }

    public void setData(JFormattedTextField data) {
        this.data = data;
    }
    
}
