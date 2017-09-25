/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaleilao;

/**
 *
 * @author Gustavo Torres Custódio, Mateus Assis Rocha
 */
public class Produto {

    private int codProduto;
    private int codPessoa;
    String nomeProduto;
    int tempoUso;
    String estadoConservacao;
    double valor;
    String descricao;

    Produto(int codProduto, int codPessoa, String nomeProduto, double valor,
            String estadoConservacao, int tempoUso, String descricao) {
        this.codProduto = codProduto;
        this.codPessoa = codPessoa;
        this.nomeProduto = nomeProduto;
        this.tempoUso = tempoUso;
        this.estadoConservacao = estadoConservacao;
        this.valor = valor;
        this.descricao = descricao;
    }

    public String getNomeProduto() {
        return this.nomeProduto;
    }
}
