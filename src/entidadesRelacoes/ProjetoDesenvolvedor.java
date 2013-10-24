/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidadesRelacoes;

/**
 *
 * @author elias
 */
public class ProjetoDesenvolvedor {
   int  idProjeto;
   int  idDesenvolvedor;

    public ProjetoDesenvolvedor(int idProjeto, int idDesenvolvedor) {
        this.idProjeto = idProjeto;
        this.idDesenvolvedor = idDesenvolvedor;
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    public int getIdDesenvolvedor() {
        return idDesenvolvedor;
    }

    public void setIdDesenvolvedor(int idDesenvolvedor) {
        this.idDesenvolvedor = idDesenvolvedor;
    }
    
     
    
    
}
