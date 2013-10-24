/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author elias
 */
public class Msg {

    public void msgRegistrado(Component f) {
        JOptionPane.showMessageDialog(f, "Seu Registro foi salvo com Sucesso!");
    }

    public void msgEstaVinculado(Component f) {
        JOptionPane.showMessageDialog(f, "Seu Registro esta vinculado a outro não pode ser excluído!");
    }

    public void msgGeneric(String text) {
        JOptionPane.showMessageDialog(null, text);
    }

    public boolean opcaoExcluir(Component f) {
        boolean excluir = true;
        int opcao = JOptionPane.showOptionDialog(null, "Deseja realmente excluir?", "Confirmação", 1, 1, null, new String[]{"Sim", "Não"}, 0);
        if (opcao == 1) {
            excluir = false;
        }
        return excluir;
    }
    public boolean opcaoDuplicado(Component f) {
        boolean excluir = true;
        int opcao = JOptionPane.showOptionDialog(null, "Este tópico esta vinculado a um ou mais projetos. Deseja realmente editar?", "Confirmação", 1, 1, null, new String[]{"Sim", "Não"}, 0);
        if (opcao == 1) {
            excluir = false;
        }
        return excluir;
    }
}
