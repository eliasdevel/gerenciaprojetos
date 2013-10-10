/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import DAO.CategoriasDAO;
import entidadesRelacoes.Categoria;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import util.Funcoes;
import view.Msg;

/**
 *
 * @author Elias Müller
 * @version 0.1
 * @since 2013
 * @see lol
 *
 */
public class CategoriasControl {
    
    int id;
    int codigo;
    char operante;
    ResultSet rs;
    
    public void populaCategorias(JTextField filtro, JTable tb, JTabbedPane tp, JButton bt, JTextField titulo, JTextArea descricao) {
        String pesquisa = filtro.getText();
        CategoriasDAO iuds = new CategoriasDAO();
        rs = iuds.resultado(pesquisa);
        Funcoes.populaTabela(tb, "Exclir,Editar,Titulo,Descricao", rs, "idcategoria,idcategoria,titulo,descricao");
        
        defineRenderers(tb);
        new Editar(tb, 1, tp, titulo, descricao, bt);
        new Excluir(tb, 0, tp, filtro, bt, titulo, descricao);
        
        
    }
    
    private void defineRenderers(JTable jTable) {
        DefaultTableCellRenderer rendererCentro = new DefaultTableCellRenderer();
        rendererCentro.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer rendererDireita = new DefaultTableCellRenderer();
        rendererDireita.setHorizontalAlignment(SwingConstants.RIGHT);
        DefaultTableCellRenderer rendererEsquerda = new DefaultTableCellRenderer();
        rendererEsquerda.setHorizontalAlignment(SwingConstants.LEFT);
        JTableHeader header = jTable.getTableHeader();
        header.setPreferredSize(new Dimension(0, 35));
        TableColumnModel modeloDaColuna = jTable.getColumnModel();
        modeloDaColuna.getColumn(0).setCellRenderer(rendererCentro);
        modeloDaColuna.getColumn(1).setCellRenderer(rendererEsquerda);
        modeloDaColuna.getColumn(2).setCellRenderer(rendererCentro);
        modeloDaColuna.getColumn(0).setMaxWidth(50);
        modeloDaColuna.getColumn(1).setMaxWidth(50);
        modeloDaColuna.getColumn(2).setMaxWidth(325);
        modeloDaColuna.getColumn(2).setMinWidth(225);
        modeloDaColuna.getColumn(3).setMaxWidth(195);
        
        
    }
    
    public void acaoBotaoNovoSalvar(JTabbedPane tp, JTextField titulo, JPanel p, JTextArea descricao, JInternalFrame form, JButton bt, JTextField filtro, JTable tb) {
        
        if (tp.getSelectedIndex() != 1) {
            operante = 'n';
        } else {
            if (operante != 'i') {
                operante = 'u';
            }
        }
        Categoria c = new Categoria(this.codigo, titulo.getText(), descricao.getText());
        CategoriasDAO iuds = new CategoriasDAO();
        
        if (operante == 'u' || operante == 'i') {
            if (c.getTitulo().length() > 0) {
                if (iuds.iud(operante, c) > 0) {
                    Funcoes.limparCampos(p);
                    descricao.setText("");
                    new Msg().msgRegistrado(form);
                    tp.setSelectedIndex(0);
                    bt.setText("Novo");
                    populaCategorias(filtro, tb, tp, bt, titulo, descricao);
                }
            } else {
                new Msg().msgGeneric("O título é Precisa ser preenchido!");
            }
        }

//        System.out.println(operante);

        if (operante == 'n') {
            bt.setText("Salvar");
            tp.setSelectedIndex(1);
            
            operante = 'i';
            
        }
        
    }
    
    public void acaoSair(JInternalFrame frame, JTabbedPane tp, JPanel pcad, JPanel ppes, JButton bt) {
        tp.setSelectedIndex(0);
        frame.setVisible(false);
        Funcoes.limparCampos(pcad);
        Funcoes.limparCampos(ppes);
        bt.setText("Novo");
        operante = 'n';
        
        
    }

//      class editCat extends Editar 
//    implements ActionListener{
//        public editCat(JTable tb,int column){
//            super(tb, column);
//            
//        }
//        public void acao(ActionEvent e){
//            tp.setSelectedIndex(1);
//            codigo.setText(e.getActionCommand() + "");
//            System.out.println(id);
//            operante = 'u';
//            CategoriasDAO pes = new CategoriasDAO();
//            Categoria cat = pes.linha(e.getActionCommand() + "");
//            titulo.setText(cat.getTitulo() + "");
//            System.out.println(cat.getDescricao());
//            descricao.setText(cat.getDescricao() + "");
//            btSalvar.setText("Salvar");
//        }
//    } 
    class Editar extends AbstractCellEditor
            implements TableCellRenderer, TableCellEditor, ActionListener {
        
        JButton bts;
        JTextField titulo;
       
        JTextArea descricao;
        JTable table;
        JButton renderButton;
        JTabbedPane tp;
        JButton editButton;
        String text = "Editar";
        
        public Editar(JTable table, int column, JTabbedPane tp, JTextField titulo, JTextArea descricao,  JButton bts) {
            super();
            this.bts = bts;
            this.table = table;
            this.tp = tp;
            this.descricao = descricao;
            this.titulo = titulo;
          
            renderButton = new JButton();
            
            try {
                Image img = ImageIO.read(getClass().getResource("/view/editar.png"));
                renderButton.setIcon(new ImageIcon(img));
                
            } catch (IOException ex) {
            }
            renderButton.setToolTipText("Editar");
            
            editButton = new JButton();
            editButton.setFocusPainted(false);
            editButton.addActionListener(this);
            
            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(column).setCellRenderer(this);
            columnModel.getColumn(column).setCellEditor(this);
        }
        
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (hasFocus) {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            } else if (isSelected) {
                renderButton.setForeground(table.getSelectionForeground());
                renderButton.setBackground(table.getSelectionBackground());
            } else {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }
            
            renderButton.setText("");
            
            return renderButton;
        }
        
        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value, boolean isSelected, int row, int column) {
            text = (value == null) ? "" : value.toString();
            editButton.setText(text);
            return editButton;
        }
        
        @Override
        public Object getCellEditorValue() {
            return text;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            tp.setSelectedIndex(1);
            codigo = Integer.parseInt(e.getActionCommand());
            System.out.println(id);
            operante = 'u';
            CategoriasDAO pes = new CategoriasDAO();
            Categoria cat = pes.linha(e.getActionCommand() + "");
            titulo.setText(cat.getTitulo() + "");
            descricao.setText(cat.getDescricao() + "");
            bts.setText("Salvar");
            
        }
    }
    
    class Excluir extends AbstractCellEditor
            implements TableCellRenderer, TableCellEditor, ActionListener {
        
        JButton bts;
        JTextField filtro;
        JLabel codigo;
        JTextArea descricao;
        JTable table;
        JButton renderButton;
        JTabbedPane tp;
        JButton editButton;
        String text = "Editar";
        
        public Excluir(JTable table, int column, JTabbedPane tp, JTextField filtro, JButton bt, JTextField titulo, JTextArea descricao) {
            super();
            this.filtro = filtro;
            this.table = table;
            this.tp = tp;
            this.descricao = descricao;
            this.bts = bt;
            this.codigo = codigo;
            renderButton = new JButton();
            try {
                Image img = ImageIO.read(getClass().getResource("/view/excluir.png"));
                renderButton.setIcon(new ImageIcon(img));
            } catch (IOException ex) {
            }
            renderButton.setToolTipText("Excluir");
            editButton = new JButton();
            editButton.setFocusPainted(false);
            editButton.addActionListener(this);
            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(column).setCellRenderer(this);
            columnModel.getColumn(column).setCellEditor(this);
        }
        
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (hasFocus) {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            } else if (isSelected) {
                renderButton.setForeground(table.getSelectionForeground());
                renderButton.setBackground(table.getSelectionBackground());
            } else {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }
            
            renderButton.setText("");
            
            return renderButton;
        }
        
        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value, boolean isSelected, int row, int column) {
            text = (value == null) ? "" : value.toString();
            editButton.setText(text);
            return editButton;
        }
        
        @Override
        public Object getCellEditorValue() {
            return text;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            
            CategoriasDAO iuds = new CategoriasDAO();
            // passa um objeto do tipo categoria que contem o id para executar a exclusão..
            
            if (new Msg().opcaoExcluir(tp)) {
                if (!iuds.temDesenvolvedor((e.getActionCommand() + "").trim())) {
                    iuds.iud('d', new Categoria(Integer.parseInt(e.getActionCommand() + ""), null, null));
                } else {
                    new Msg().msgEstaVinculado(null);
                }
            }
                    populaCategorias(filtro, table, tp, bts, filtro, descricao);
        }
    }
}
