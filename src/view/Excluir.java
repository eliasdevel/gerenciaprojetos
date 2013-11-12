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
public class Excluir extends AbstractCellEditor
        implements TableCellRenderer, TableCellEditor, ActionListener {

    JTable table;
    JButton renderButton;
    JButton editButton;
    String text = "Editar";
    int column;

    public Excluir(JTable table, int column) {
        super();
        this.column=column;
        this.table = table;
        renderButton = new JButton();
        editButton = new JButton();
        try {
            Image img = ImageIO.read(getClass().getResource("/view/excluir.png"));
            renderButton.setIcon(new ImageIcon(img));
            renderButton.setBackground(null);
            renderButton.setForeground(null);
        } catch (IOException ex) {
        }
        renderButton.setToolTipText("Excluir");
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
        editButton.setActionCommand(text);
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return text;
    }
  
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
