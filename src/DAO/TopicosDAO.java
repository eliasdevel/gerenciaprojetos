/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConexaoBD;
import entidadesRelacoes.Topico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author elias
 */
public class TopicosDAO {

    ResultSet rs = null;
    ArrayList<Topico> topicosList;

    public ResultSet resultado(int idProjeto, char situacao,int iddesenvolvedor) {
        
//        String where = "";
            

            String sql = "SELECT t.idtopico,t.titulo,t.descricao,pt.pronto,pt.iddesenvolvedor ,"
                    + "(select nome from desenvolvedores where iddesenvolvedor = pt.iddesenvolvedor) as desenvolvedor ,"
                    + " (case when pt.situacao = 't'\n" +
"then 'Teste'\n" +
"else case when pt.situacao = 'd'\n" +
"    then 'Desenvolvimento'\n" +
"        else case when pt.situacao = 'c'\n" +
"        then 'Criado'\n" +
"            else case when pt.situacao = 'f'\n" +
"            then 'Finalizado'\n" +
"                else case when pt.situacao = 'p'\n" +
"                then 'Planejado'\n" +
"    \n" +
"                end\n" +
"            end\n" +
"        end\n" +
"    end\n" +
" end )  as situacao  FROM topicos t INNER JOIN projetos_topicos pt ON t.idtopico = pt.idtopico WHERE pt.idprojeto = ? ";

            if(situacao!= 'a'){
                sql += " AND pt.situacao = '"+ situacao+"'";
            }
            
            if(iddesenvolvedor>0){
                sql += " AND pt.iddesenvolvedor = "+iddesenvolvedor;
            }
            
                sql += " ORDER BY pt.situacao";
            try {
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setInt(1, idProjeto);
                rs = ps.executeQuery();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex);
            }
            System.out.println(sql);
       
        return rs;

    }

    public Topico linha(String id) {
        Topico c = new Topico(1, null, null, false, 'c',0);
        String sql = "SELECT * FROM topicos WHERE idtopico = ?";
        try {
            PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                rs.first();
                c.setTitulo(rs.getString("titulo"));
                c.setDescricao(rs.getString("descricao"));
                c.setId(rs.getInt("idtopico"));
                c.setPronto(false);
            } else {
                c = null;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return c;
    }

    public ArrayList linhas(String idProjeto) {
        topicosList = new ArrayList();
        String sql = "SELECT t.idtopico,t.titulo,t.descricao,pt.pronto,pt.situacao,pt.iddesenvolvedor FROM topicos t INNER JOIN projetos_topicos pt ON t.idtopico = pt.idtopico WHERE pt.idprojeto = ? ORDER BY pt.situacao";
        try {
            PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
            ps.setString(1, idProjeto);
            rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getInt("idtopico") > 0) {
                    Topico t = new Topico(rs.getInt("idtopico"), rs.getString("titulo"), rs.getString("descricao"), rs.getBoolean("pronto"), rs.getString("situacao").charAt(0),rs.getInt("iddesenvolvedor"));
                    topicosList.add(t);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return topicosList;
    }

    public int iud(char op, Topico c) {
        String sql;
        int rows = 0;
        try {
            if (op == 'i') {
                sql = ("INSERT INTO topicos (titulo,descricao) VALUES(?,?)");
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setString(1, c.getTitulo());
                ps.setString(2, c.getDescricao());
                rows = ps.executeUpdate();
                rs = ConexaoBD.con.createStatement().executeQuery("SELECT MAX(idtopico) AS maior FROM topicos");
                rs.first();
                c.setId(rs.getInt("maior"));
                ps.close();
            } else {
                if (op == 'u') {
//                                                        1              2                     3
                    sql = "UPDATE topicos SET titulo = ?, descricao = ? WHERE idtopico = ?";
                    PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                    ps.setString(1, c.getTitulo());
                    ps.setString(2, c.getDescricao());
                    ps.setString(3, c.getId() + "");
                    rows = ps.executeUpdate();
                    ps.close();
                } else {
                    if (op == 'd') {
                        rows = ConexaoBD.con.createStatement().executeUpdate("DELETE FROM topicos "
                                + "WHERE idtopico = " + c.getId());
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return rows;
    }
}
