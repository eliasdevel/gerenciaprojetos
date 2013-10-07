/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import org.w3c.dom.events.MouseEvent;

/**
 *
 * @author elias
 */
public class NoArvore extends DefaultMutableTreeNode implements MouseListener {

    NoArvore(Object descricao) {
        super(descricao);
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent me) {
        JOptionPane.showMessageDialog(null, "lol");
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent me) {
        JOptionPane.showMessageDialog(null, "lol");
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
