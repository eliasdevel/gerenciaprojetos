/*
 * To change this template, choose Tools 
 * and open the template in the editor.
 */
package DAO;

import connection.ConexaoBD;
import entidadesRelacoes.Categoria;
import entidadesRelacoes.Cliente;
import entidadesRelacoes.Estado;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import util.Funcoes;

/**
 *
 * @author Elias Müller
 *
 */
public class ClientesDAO {


    ResultSet rs = null;

    public ClientesDAO() {
    }

    public ResultSet resultado(String pesquisa) {
        if (pesquisa != null) {

            pesquisa = "%" + pesquisa + "%";

            String sql = "SELECT C.idcliente,C.nome,C.telefone,C.email,C.adicional,C.idcidade,"
                    + "(select nome from cidades where idcidade=C.idcidade)AS cidade"
                    + "     FROM clientes C WHERE C.nome like ? ORDER BY nome";

            try {
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setString(1, pesquisa);
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

    public Estado linhaEstado(String sigla) {

        Estado es = new Estado(1, sigla, null);
        String sql = "SELECT * FROM estados WHERE sigla = ?";
        try {
            PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
            ps.setString(1, sigla);
            rs = ps.executeQuery();
            rs.first();
            es.setNome(rs.getString("nome"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return es;

    }

    public Cliente linha(String id) {
        Cliente c = new Cliente(1, 20, null, null, null, null);
        c.setId(Integer.parseInt(id));
        String sql = "SELECT * FROM clientes WHERE idcliente = ?";
        try {
            PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            rs.first();
            c.setIdcidade(rs.getInt("idcidade"));
            c.setNome(rs.getString("nome"));
            c.setTelefone(rs.getString("telefone"));
            c.setEmail(rs.getString("email"));
            c.setAdicional(rs.getString("adicional"));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return c;
    }

    public int iud(char op, Cliente c) {
        String sql = "";
        int rows = 0;
        try {
            if (op == 'i') {
                sql = ("INSERT INTO clientes (idcidade,nome,telefone,email,adicional) VALUES(?,?,?,?,?)");
                PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                ps.setInt(1, c.getIdcidade());
                ps.setString(2, c.getNome());
                ps.setString(3, c.getTelefone());
                ps.setString(4, c.getEmail());
                ps.setString(5, c.getAdicional());
                rows = ps.executeUpdate();
                rs = ConexaoBD.con.createStatement().executeQuery("SELECT MAX(idcliente) AS maior FROM clientes");
                rs.first();
                c.setId(rs.getInt("maior"));

                ps.close();
            } else {
                if (op == 'u') {
                    sql = "UPDATE clientes "
                            + "SET idcidade=?,"
                            + " nome = ?,"
                            + " telefone = ?,"
                            + " email = ?,"
                            + " adicional = ?"
                            + " WHERE idcliente = ?";
                    PreparedStatement ps = ConexaoBD.con.prepareStatement(sql);
                    ps.setInt(1, c.getIdcidade());
                    ps.setString(2, c.getNome());
                    ps.setString(3, c.getTelefone());
                    ps.setString(4, c.getEmail());
                    ps.setString(5, c.getAdicional());
                    ps.setInt(6, c.getId());
                    rows = ps.executeUpdate();
                    ps.close();
                } else {
                    if (op == 'd') {
                        rows = ConexaoBD.con.createStatement().executeUpdate(""
                                + "DELETE FROM clientes "
                                + "WHERE idcategoria = " + c.getId());
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex);
        }
        return rows;
    }
}
