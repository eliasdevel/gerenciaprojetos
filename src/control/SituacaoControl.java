/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

/**
 *
 * @author elias
 */
public class SituacaoControl {

    JDialog dl;
    JComboBox cb;
    JButton bt;
    char[] situacoes;
    char situacao;

    public SituacaoControl() {
        situacoes = new char[5];
        situacoes[0] = 'c';
        situacoes[1] = 'p';
        situacoes[2] = 'd';
        situacoes[3] = 't';
        situacoes[4] = 'f';

    }

    public void selectIndex(char situacao){
        switch (situacao){
            case 'c':
                cb.setSelectedIndex(0);
                break;
            case 'p':
                cb.setSelectedIndex(1);
                break;
            case 'd':
                cb.setSelectedIndex(2);
                break;
            case 't':
                cb.setSelectedIndex(3);
                break;
            case 'f':
                cb.setSelectedIndex(4);
                break;
        }
        
    }
    
    public char getSituacao() {
        if (!dl.isShowing()) {
           dl.setSize(500, 200);
            dl.setLocationByPlatform(true);
            dl.setVisible(true);
        }

        this.situacao = situacoes[cb.getSelectedIndex()];
        if (dl.isShowing()) {
            dl.setVisible(false);
        }
        return this.situacao;
    }

    public JDialog getDl() {
        return dl;
    }

    public void setDl(JDialog dl) {
        
        this.dl = dl;
    }

    public JComboBox getCb() {
        return cb;
    }

    public void setCb(JComboBox cb) {
        this.cb = cb;
    }

    public JButton getBt() {
        return bt;
    }

    public void setBt(JButton bt) {
        this.bt = bt;
    }
}
