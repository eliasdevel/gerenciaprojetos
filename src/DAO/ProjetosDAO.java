/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import connection.ConexaoBD;
import entidadesRelacoes.Projeto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.Data;
import javax.swing.JOptionPane;

/**
 *
 * @author Elias Müller
 *
 */
public class ProjetosDAO {

    ResultSet rs = null;

    public ResultSet resultado(String nome, int idCliente, int idDesenvolvedor, Data data1, Data data2) {
        if (nome != null) {
            String pesquisa2 = "%" + nome + "%";
            String sql = " SELECT idprojeto,c.nome AS cliente, titulo,descricao FROM projetos p INNER JOIN clientes c ON c.idcliente = p.idcliente WHERE "
                    + " (case"
                    + " when @dataInicialPro = true"
                    + " then  p.dataInicial BETWEEN ? AND ?"
                    + " else true"
                    + "  end)"
                    + " AND "
                    + " (case"
                    + " when @idClientePro = true"
                    + " then  c.idcliente = ?"
                    + " else true"
                    + "  end) "
                    + " AND "
                    + " (case"
                    + " when @idDesenvolvedorPro = true"
                    + " then  p.idprojeto IN (select pd.idprojeto from projetos_desenvolvedores pd where pd.iddesenvolvedor = ?)"
                    + " else true"
                    + "  end) "
                    + "  AND "
                    + " (case"
                    + " when @tituloPro = true"
                    + " then  p.titulo LIKE ?"
                    + " else true"
                    + " end) ORDER BY p.titulo";


            boolean temWhere = false;


            try {
                connection.ConexaoBD.con.createStatement().executeQuery(" set @dataInicialPro= false ;");
                connection.ConexaoBD.con.createStatement().executeQuery(" set @idDesenvolvedorPro= false;");
                connection.ConexaoBD.con.createStatement().executeQuery(" set @idClientePro= false ;");
                connection.ConexaoBD.con.createStatement().executeQuery(" set @tituloPro= false ;");
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                if (!nome.trim().equals("")) {
                    connection.ConexaoBD.con.createStatement().executeQuery("  set @tituloPro = true; ");
                }
                if (idCliente > 0) {
                    connection.ConexaoBD.con.createStatement().executeQuery(" set @idClientePro = true ");
                }
                if (idDesenvolvedor > 0) {
                    connection.ConexaoBD.con.createStatement().executeQuery(" set  @idDesenvolvedorPro = true ");
                }
                if (data2.getAno() > 1993) {
                    connection.ConexaoBD.con.createStatement().executeQuery(" set @dataInicialPro = true ");
                }
                ps.setString(1, data1.getDBData());
                ps.setString(2, data2.getDBData());
                ps.setInt(3, idCliente);
                ps.setInt(4, idDesenvolvedor);
                ps.setString(5, pesquisa2);

                rs = ps.executeQuery();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, " Erro: " + ex);
            }
            System.out.println(sql);
        } else {
            JOptionPane.showMessageDialog(null, " A variável de pesquisa não pode ser nula!, se quiser uma variável sem valor mande: \" \"  ");
        }
        return rs;
    }

    public Projeto linha(String id) {
        Projeto p = new Projeto(0, 0, null, null, false);
        String sql = " SELECT * FROM projetos WHERE idprojeto = ?";
        try {
            PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            rs.first();
            p.setTitulo(rs.getString("titulo"));
            p.setDescricao(rs.getString("descricao"));
            p.setId(rs.getInt("idprojeto"));
            p.setIdcliente(rs.getInt("idcliente"));
            p.setPronto(rs.getBoolean("pronto"));
            Data d = new Data(1, 1, 1);
            d.setDBData(rs.getString("dataInicial"));
            p.setDataInicial(d.getData());
            d.setDBData(rs.getString("dataPrevisao"));
            p.setDataPrevisao(d.getData());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return p;
    }

    public int iud(char op, Projeto p) {
        String sql;
        int rows = 0;
        try {
            if (op == 'i') {
                sql = ("INSERT INTO projetos (titulo,idcliente,descricao,dataInicial,dataPrevisao) VALUES(?,?,?,?,?)");
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                Data d = new Data(1, 1, 1);
                ps.setString(1, p.getTitulo());
                ps.setInt(2, p.getIdcliente());
                ps.setString(3, p.getDescricao());
                d.setData(p.getDataInicial());
                ps.setString(4, d.getDBData());
                d.setData(p.getDataPrevisao());
                ps.setString(5, d.getDBData());
                rows = ps.executeUpdate();
                rs = ConexaoBD.con.createStatement().executeQuery(" SELECT MAX(idprojeto) AS maior FROM projetos");
                rs.first();
                p.setId(rs.getInt("maior"));
                ps.close();
            } else {
                if (op == 'u') {
                    sql = " UPDATE projetos SET titulo = ?, descricao = ?, idcliente = ?, dataInicial = ?,dataPrevisao = ? WHERE idprojeto = ?";
                    PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                    Data d = new Data(1, 1, 1);
                    ps.setString(1, p.getTitulo());
                    ps.setString(2, p.getDescricao());
                    ps.setInt(3, p.getIdcliente());
                    d.setData(p.getDataInicial());
                    ps.setString(4, d.getDBData());
                    d.setData(p.getDataPrevisao());
                    ps.setString(5, d.getDBData());
                    ps.setInt(6, p.getId());
                    rows = ps.executeUpdate();
                    ps.close();
                } else {
                    if (op == 'd') {
                        rows = ConexaoBD.con.createStatement().executeUpdate(" DELETE FROM projetos "
                                + " WHERE idprojeto = " + p.getId());
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, " Erro: " + ex);
        }
        return rows;
    }
}
