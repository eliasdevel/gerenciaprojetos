/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConexaoBD;
import entidadesRelacoes.ProjetoTopico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import view.Msg;

/**
 *
 * @author elias
 */
public class ProjetosTopicosDAO {

    public static void main(String[] args) {
        ConexaoBD con = new ConexaoBD();
        if (con.abriuConexao()) {
            ProjetosTopicosDAO teste = new ProjetosTopicosDAO();

            if (teste.estaNoProjeto("1", "1")) {
                new Msg().msgGeneric("o tópico esta no projeto");
                new Msg().msgGeneric(teste.linha(1, 1).getIdProjeto() + "");

            } else {
                new Msg().msgGeneric("o tópico não esta no projeto");
            }
        }
    }
    ResultSet rs = null;

    public boolean estaNoProjeto(String idprojeto, String idtopico) {
        boolean esta = false;
        String sql = "SELECT idtopico,idprojeto,pronto FROM projetos_topicos WHERE idprojeto = " + idprojeto + " AND  idtopico = " + idtopico;
        try {
            rs = ConexaoBD.con.createStatement().executeQuery(sql);
            rs.beforeFirst();
            if (rs.next()) {
                esta = true;
            }
        } catch (SQLException ex) {
            new Msg().msgGeneric("Erro Projetos Topicos:  " + ex);
        }

        return esta;
    }

    public void addTopicoProjeto() {
    }

    public ResultSet resultadoProjeto(String idprojeto) {
        if (idprojeto != null) {
//        String where = "";
            idprojeto = "%" + idprojeto + "%";

            String sql = "SELECT idtopico,idpojeto,pronto FROM projetos_topicos WHERE idprojeto = " + idprojeto;

            try {
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setString(1, idprojeto);
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex);
            }
            System.out.println(sql);
        } else {
            JOptionPane.showMessageDialog(null, "A variável de pesquisa não pode ser nula!, se quiser uma variavé sem valor mande: \"\" ");
        }
        return rs;
    }

    public boolean estaEmDoisProjetos(String idtopico) {
        boolean esta = false;
        String sql = "select * from projetos_topicos where idtopico = ?" ;
        try {
            PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
            ps.setString(1, idtopico);
            rs = ps.executeQuery();
            int count = 0;
            if (rs.next()) {
                rs.beforeFirst();
                while (rs.next()) {
                    count++;
                }
            }
            if (count != 0) {
                if (count > 0) {
                    esta = true;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro para verificacao: " + ex+ sql);
        }
        return esta;
    }
    
    public int porcentagem(int idprojeto){
        int porcentagem =0;
         String sql = "select ((select count(idtopico) from projetos_topicos where idprojeto = ? and pronto = true)*100/"
                 + "(select count(idtopico) from projetos_topicos where idprojeto = ?))as porcentagem";
            try {
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setInt(1, idprojeto);
                ps.setInt(2, idprojeto);
                rs = ps.executeQuery();
                rs.first();
                porcentagem = rs.getInt("porcentagem");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex);
            }
        return  porcentagem;
    }

    public ProjetoTopico linha(int idTopico, int idProjeto) {
        ProjetoTopico pt = null;
        String sql = "SELECT * FROM projetos_topicos WHERE idtopico = ? AND idprojeto = ?";
        try {
            PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
            ps.setInt(1, idTopico);
            ps.setInt(2, idProjeto);
            rs = ps.executeQuery();
            rs.first();
            pt = new ProjetoTopico(idTopico, rs.getInt("idprojeto"), rs.getBoolean("pronto"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return pt;
    }

    public int iud(char op, ProjetoTopico pt) {
        String sql;
        int rows = 0;
        try {
            if (op == 'i') {
                sql = ("INSERT INTO projetos_topicos (idprojeto,idtopico,pronto) VALUES(?,?,?)");
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setInt(1, pt.getIdProjeto());
                ps.setInt(2, pt.getIdTopico());
                ps.setBoolean(3, pt.isPronto());
                rows = ps.executeUpdate();
                ps.close();
            } else {
                if (op == 'u') {
//                                                              1                   2                 3
                    sql = "UPDATE projetos_topicos SET pronto = ?  WHERE idtopico = ? AND idprojeto = ?";
                    PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                    ps.setBoolean(1, pt.isPronto());
                    ps.setInt(2, pt.getIdProjeto());
                    ps.setInt(3, pt.getIdTopico());
                    rows = ps.executeUpdate();
                    ps.close();
                } else {
                    if (op == 'd') {
                        rows = ConexaoBD.con.createStatement().executeUpdate("DELETE FROM projetos_topicos "
                                + "WHERE idprojeto = " + pt.getIdProjeto());
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return rows;
    }
}
