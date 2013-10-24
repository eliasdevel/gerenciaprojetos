/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Component;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 * @version 1.0
 * @author Elias Müller
 */
public class Funcoes {

    /**
     * Método para limpar campos de um painel
     *
     * @param container Painel que contém campos
     *
     */
    public static String implode(String[] ary, String delim) {
        String out = "";
        for (int i = 0; i < ary.length; i++) {
            if (i != 0) {
                out += delim;
            }
            out += ary[i];
        }
        return out;
    }

    public Date defineData(String data) {
        Date x = null;
        String[] datas = data.split("/");
        x = new Date(Integer.parseInt(datas[2]) - 1900, Integer.parseInt(datas[1]), Integer.parseInt(datas[0]));
        return x;
    }

    public String obtreData(Date d) {
        String data = "";
        data = "" + d.getDay() + "/" + d.getMonth() + "/" + d.getYear();
        return data;
    }

    public static void limparCampos(JPanel container) {
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText(null);
            } else {
            }
        }
    }

    /**
     * <div style='border: 1px solid black; text-align:center;
     * background:white'>
     * <b>Método para popular JTable</b></div> <br/>
     * Recebe um <span style='color:blue'>JTable:</span> tabela que será
     * populada <br />
     * Uma <span style='color:blue'>String:</span> Com os nomes dos índices da
     * JTable separados por vírgula<br />
     * Um <span style='color:blue'>ResultSet:</span> com o resultado de uma
     * consulta no banco de dados. <br />
     * E uma <span style='color:blue'>String:</span> com o nome das colunas da
     * tabela no banco de dados.
     *
     * @param tb JTable a ser populado
     * @param cabecas Nomes separados por vírgula que ficarão no cabeçalho da
     * tabela
     * @param rs resultado de um select em forma de ResultSet
     * @param colunas Nomes das colunas no banco de dados conforme a ordem dos
     * nomes do cabeçalho
     */
    public static void populaTabela(JTable tb, String cabecas, ResultSet rs, String colunas) {
        if (tb != null) {
            tb.setRowHeight(24);
            String separador = ",";
            String[] cabe = cabecas.split(separador);
            int col = cabe.length;
            try {
                rs.beforeFirst();
                if (rs.next()) {
                    rs.beforeFirst();
                    int lin = 0;
                    while (rs.next()) {
                        lin++;
                    }
                    rs.beforeFirst();
                    Object[][] dadosT = new Object[lin][col];
                    String[] indicess = colunas.split(separador);
                    int i = 0;
                    while (rs.next()) {
                        for (int j = 0; j < col; j++) {
                            dadosT[i][j] = rs.getString(indicess[j]);
                        }
                        i++;
                    }
                    tb.setModel(new DefaultTableModel(dadosT, cabe) {
                        //sobreescreve colunas dizendo se são editáveis
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            boolean editavel = false;

                            if (column == 0 || column == 1) {
                                editavel = true;
                            }
                            return editavel;
                        }
                    });
                } else {

                    Object[][] dadosT = new Object[1][col];
                    tb.setModel(new DefaultTableModel(dadosT, cabe) {
                        //sobreescreve colunas dizendo se são editáveis
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            boolean editavel = false;
                            return editavel;
                        }
                    });


                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao selecionar tabela:" + ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "A variável JTable setada esta nula");
        }
    }

    public static void populaTabelaSelecao(JTable tb, String cabecas, ResultSet rs, String colunas) {
        String separador = ",";
        String[] cabe = cabecas.split(separador);
        int col = cabe.length;
        try {
            rs.beforeFirst();
            int lin = 0;
            while (rs.next()) {
                lin++;
            }
            rs.beforeFirst();
            Object[][] dadosT = new Object[lin][col];
            String[] indicess = colunas.split(separador);
            int i = 0;
            while (rs.next()) {
                for (int j = 0; j < col; j++) {
                    dadosT[i][j] = rs.getString(indicess[j]);
                }
                i++;
            }
            tb.setModel(new DefaultTableModel(dadosT, cabe) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    boolean editavel = false;
                    if (column == 0) {
                        editavel = true;
                    }
                    return editavel;
                }
            });
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao selecionar tabela:" + ex);
        }
    }

    /**
     * Método para popular combobox, onde é passado um JComboBox, ResultSet,
     * String da coluna de texto do banco de dados que deve aparecer na combo,
     * String do id da coluna no banco de dados.
     *
     * @return String - com os ids separados por vírgila.
     */
    public String populaComboBox(JComboBox cb, ResultSet rs, String colunaNomes, String colunaId) {
        int lin = 0;
        String valores = "";
        try {
            while (rs.next()) {
                lin++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Funcoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rs.beforeFirst();
            int i = 0;
            while (rs.next()) {
                valores += rs.getString(colunaId);
                if (!rs.isLast()) {
                    valores += ",";
                }

                cb.addItem(rs.getString(colunaNomes));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Funcoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valores;
    }

    /**
     * Método para selecionar índice do combobox referenciando seu id.
     *
     * @param cb Combobox
     * @param ids Vetor de String com ids,
     * @param id String do id que deve ser sString[] indicess =
     * colunas.split(separador);elecionado
     */
    public void selecionaIndiceCombo(JComboBox cb, String[] ids, String id) {
        int j = 0;
        while (j <= ids.length - 1) {
            if (ids[j].equals(id)) {
                cb.setSelectedIndex(j);
            }
            j++;
        }
    }

    /**
     * <div style='border: solid 1px #080'>
     * Método para formatar camposJFormattedTextField
     * </div>
     *
     * @param jft campo a ser formatado
     * @param mascara String com o valor da máscara ex: "(##)####-####"
     */
    public static void formataCampo(JFormattedTextField jft, String mascara) {
        MaskFormatter formato = null;
        try {
            formato = new MaskFormatter(mascara);
        } catch (ParseException ex) {
            System.out.println("Erro na máscara: " + ex);
        }

        jft.setFormatterFactory(new DefaultFormatterFactory(formato, formato));

    }
}
