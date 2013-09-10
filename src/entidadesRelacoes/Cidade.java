/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidadesRelacoes;

/**
 *
 * @author elias
 */
public class Cidade {
    int id;
    String siglaestado;
    String cep;
    String nome ;

    public Cidade(int id, String siglaestado, String cep, String nome) {
        this.id = id;
        this.siglaestado = siglaestado;
        this.cep = cep;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiglaestado() {
        return siglaestado;
    }

    public void setSiglaestado(String siglaestado) {
        this.siglaestado = siglaestado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
