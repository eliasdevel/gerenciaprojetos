/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidadesRelacoes;

/**
 *
 * @author elias
 */
public class Topico {
    int id;
    String titulo;
    String descricao;
    boolean pronto;
    char situacao;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getSituacao() {
        return situacao;
    }

    public void setSituacao(char situacao) {
        this.situacao = situacao;
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

    public Topico(int id, String titulo, String descricao, boolean pronto, char situacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.pronto = pronto;
        this.situacao = situacao;
    }
    
    

   
    
}
