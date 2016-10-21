/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author elias
 */
public class Concluir extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener {

    JButton renderButton;
    JButton editButton;
    String text = "Concluir";

    public Concluir(JTable table, int column) {
        super();
        renderButton = new JButton();
        try {

            Image img = null;
            img = ImageIO.read(getClass().getResource("/view/incompleto.png"));
            renderButton.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
        }
        renderButton.setToolTipText(text);

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
        try {
            String situacao = "";
            if ((char) table.getValueAt(row, column) == 'f') {
                situacao = "Finalizado";
                Image img = null;
                img = ImageIO.read(getClass().getResource("/view/completo.png"));
                renderButton.setIcon(new ImageIcon(img));


            } else {
                if ((char) table.getValueAt(row, column) == 'c') {
                    situacao = "Criado";
                    Image img = null;
                    img = ImageIO.read(getClass().getResource("/view/incompleto.png"));
                    renderButton.setIcon(new ImageIcon(img));
                } else {
                    if ((char) table.getValueAt(row, column) == 't') {
                        situacao = "em fase de Teste";
                        Image img = null;
                        img = ImageIO.read(getClass().getResource("/view/teste.png"));
                        renderButton.setIcon(new ImageIcon(img));
                    } else {
                        if ((char)table.getValueAt(row, column) == 'd') {
                            situacao = "em Desenvolvimento";
                            Image img = null;
                            img = ImageIO.read(getClass().getResource("/view/desenvolvimento.png"));
                            renderButton.setIcon(new ImageIcon(img));

                        } else {
                            if ((char) table.getValueAt(row, column) == 'p') {
                                situacao = "Planejado";
                                Image img = null;
                                img = ImageIO.read(getClass().getResource("/view/planejado.png"));
                                renderButton.setIcon(new ImageIcon(img));
                            }
                        }
                    }
                }
            }
            String msg = "O tópico está " + situacao + ", clique para mudar a situação.";
            renderButton.setToolTipText(msg);
        } catch (IOException ex) {
            new Msg().msgGeneric("Erro de definir imagem do objeto da tabela.");
        }
        if (hasFocus) {
//            renderButton.setForeground(table.getForeground());
//            renderButton.setBackground(UIManager.getColor("Button.background"));
        } else if (isSelected) {
//            renderButton.setForeground(table.getSelectionForeground());
//            renderButton.setBackground(table.getSelectionBackground());
        } else {
//            renderButton.setForeground(table.getForeground());
//            renderButton.setBackground(UIManager.getColor("Button.background"));
        }

        renderButton.setSize(50, 30);

        return renderButton;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {

        text = (value == null) ? "" : value.toString();
        editButton.setActionCommand(text);
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return text;
    }

    public void reset() {
        this.renderButton.updateUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
    }
}
