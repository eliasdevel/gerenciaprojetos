/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidadesRelacoes;

/**
 *
 * @author elias
 */
public class ProjetoTopico {
    int idTopico;
    int idProjeto;
    boolean pronto;
    char situacao;

    public ProjetoTopico(int idTopico, int idProjeto, boolean pronto, char situacao) {
        this.idTopico = idTopico;
        this.idProjeto = idProjeto;
        this.pronto = pronto;
        this.situacao = situacao;
    }


    
    
    public char getSituacao() {
        return situacao;
    }

    public void setSituacao(char situacao) {
        this.situacao = situacao;
    }

    public int getIdTopico() {
        return idTopico;
    }

    public void setIdTopico(int idTopico) {
        this.idTopico = idTopico;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    public boolean isPronto() {
        return pronto;
    }

    public void setPronto(boolean pronto) {
        this.pronto = pronto;
    }
    
    
    
    
}
