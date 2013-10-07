/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import view.*;
import entidadesRelacoes.Projeto;
import entidadesRelacoes.Topico;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.file.Path;
import java.util.Enumeration;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import sun.awt.RepaintArea;

/**
 *
 * @author elias
 */
public class ArvoreProjeto {

    public static void main(String[] args) {
        JFrame f = new JFrame();
        ScrollPane pane = new ScrollPane();
        JTree tree = new JTree();
        pane.add(tree);
        f.add(pane);
        ArvoreProjeto arvore = new ArvoreProjeto(new Projeto(0, 1, "teste", "teste", true), tree);
        arvore.setNode();
        arvore.getTree(tree);
        f.setSize(300, 400);
//        f.setL;
        f.setVisible(true);


    }
    private TreeModel model;
    private Projeto projeto;
    private Topico topico;
    private JTree tree;
    private DefaultMutableTreeNode nodeProjeto;
    private DefaultMutableTreeNode nodeTopico;
    Object[][] topicos = new Object[3][2];
    private int linhaSelecionada = 0;

    //    colocar ids na ordem dos tópicos
    //    construtor
    public ArvoreProjeto(Projeto p, final JTree tree) {
        this.tree = tree;
        this.tree.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
                linhaSelecionada = tree.getSelectionRows()[0];
                if(linhaSelecionada!=0){
                System.out.println(linhaSelecionada);
                JOptionPane.showMessageDialog(null, "entrou no evento"+topicos[linhaSelecionada-1][0]);
                }
                    

            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        topicos[0][1] = "fazer parte x do projeto";
        topicos[1][1] = "fazer parte y do projeto";
        topicos[2][1] = "fazer parte z do projeto";
        topicos[0][0] = "10";
        topicos[1][0] = "20";
        topicos[2][0] = "30";
                
        this.projeto = p;

    }

    /**
     * <b> Define nó da árvore;</b>
     *
     */
    public void setNode() {
        this.nodeProjeto = new DefaultMutableTreeNode(this.projeto.getDescricao());
        for (int i = 0; i < topicos.length; i++) {
            this.nodeTopico = new DefaultMutableTreeNode(topicos[i][1]);

            this.nodeProjeto.add(nodeTopico);
            JOptionPane.showMessageDialog(null, "índice: " + topicos[i][0]);
            this.nodeTopico = null;
        }
        if (this.tree.isSelectionEmpty()) {
        }
    }
    public void addTopico(String topico){
       int ta =  topicos.length;

       this.nodeProjeto.add(nodeTopico);
    }
    

    public void getTree(JTree tree) {
        this.model = new DefaultTreeModel(this.nodeProjeto);

        tree.setModel(model);
    }
}
