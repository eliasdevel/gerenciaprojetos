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

            new Msg().msgGeneric(teste.porcentagensTopicosProjeto(3)[3]);
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

    public String[] porcentagensTopicosProjeto(int idProjeto) {
        String[] porcentagens = new String[5];
        String[] sql = new String[5];

        sql[0] = "select concat((select count(idtopico) * 100 from projetos_topicos where idprojeto = " + idProjeto + " and situacao = 'f')/ count(idtopico),'% dos Tópicos estão finalizados') as porcentagem from projetos_topicos where idprojeto = " + idProjeto;
        sql[1] = "select concat((select count(idtopico) * 100 from projetos_topicos where idprojeto = " + idProjeto + " and situacao = 'd')/ count(idtopico),'% dos Tópicos estão em desenvolvimento') as porcentagem from projetos_topicos where idprojeto = " + idProjeto;
        sql[2] = "select concat((select count(idtopico) * 100 from projetos_topicos where idprojeto = " + idProjeto + " and situacao = 'c')/ count(idtopico),'% dos Tópicos estão criados') as porcentagem from projetos_topicos where idprojeto = " + idProjeto;
        sql[3] = "select concat((select count(idtopico) * 100 from projetos_topicos where idprojeto = " + idProjeto + " and situacao = 't')/ count(idtopico),'% dos Tópicos estão em teste') as porcentagem from projetos_topicos where idprojeto = " + idProjeto;
        sql[4] = "select concat((select count(idtopico) * 100 from projetos_topicos where idprojeto = " + idProjeto + " and situacao = 'p')/ count(idtopico),'% dos Tópicos estão planejados') as porcentagem from projetos_topicos where idprojeto = " + idProjeto;
        for (int i = 0; i < sql.length; i++) {
            try {
                rs = ConexaoBD.con.createStatement().executeQuery(sql[i]);
                rs.beforeFirst();
                if (rs.next()) {
                    porcentagens[i] = rs.getString("porcentagem");
                }
            } catch (SQLException ex) {
                new Msg().msgGeneric("Erro Projetos Topicos:  " + ex);
            }
        }
        return porcentagens;
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
        String sql = "select * from projetos_topicos where idtopico = ?";
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
            JOptionPane.showMessageDialog(null, "Erro para verificacao: " + ex + sql);
        }
        return esta;
    }

    public int porcentagem(int idprojeto) {
        int porcentagem = 0;
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
        return porcentagem;
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
            pt = new ProjetoTopico(idTopico, rs.getInt("idprojeto"), rs.getBoolean("pronto"), rs.getString("situacao").charAt(0), rs.getInt("iddesenvolvedor"));
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
                sql = ("INSERT INTO projetos_topicos (idprojeto,idtopico,pronto,situacao,iddesenvolvedor) VALUES(?,?,?,?,?)");
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setInt(1, pt.getIdProjeto());
                ps.setInt(2, pt.getIdTopico());
                ps.setBoolean(3, pt.isPronto());
                ps.setString(4, pt.getSituacao() + "");
                ps.setString(5, pt.getIdDesenvolvedor() + "");

                rows = ps.executeUpdate();
                ps.close();
            } else {
                if (op == 'u') {
//                                                              1                   2                 3
                    sql = "UPDATE projetos_topicos SET pronto = ? , situacal = WHERE idtopico = ? AND idprojeto = ?";
                    PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                    ps.setBoolean(1, pt.isPronto());
                    ps.setString(2, pt.getSituacao() + "");
                    ps.setInt(3, pt.getIdTopico());
                    ps.setInt(4, pt.getIdProjeto());
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
