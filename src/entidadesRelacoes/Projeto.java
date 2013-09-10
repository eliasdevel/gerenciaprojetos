/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidadesRelacoes;

/**
 *
 * @author elias
 */
public class Projeto {
    int id;
    int idcliente;
    String titulo;
    String descricao;
    boolean pronto;

    public Projeto(int id, int idcliente, String titulo, String descricao, boolean pronto) {
        this.id = id;
        this.idcliente = idcliente;
        this.titulo = titulo;
        this.descricao = descricao;
        this.pronto = pronto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isPronto() {
        return pronto;
    }

    public void setPronto(boolean pronto) {
        this.pronto = pronto;
    }
    
    

}
