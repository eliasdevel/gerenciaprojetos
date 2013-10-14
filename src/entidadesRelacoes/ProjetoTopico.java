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

    public ProjetoTopico(int idTopico, int idProjeto, boolean pronto) {
        this.idTopico = idTopico;
        this.idProjeto = idProjeto;
        this.pronto = pronto;
    
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
