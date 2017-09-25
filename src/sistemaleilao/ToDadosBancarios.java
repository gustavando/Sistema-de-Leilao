/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaleilao;

import java.sql.SQLException;

/**
 *
 * @author Gustavo Torres Custódio, Mateus Assis Rocha
 */
public class ToDadosBancarios {

    private int codPessoa;
    private String nome;
    private String conta;
    private String agencia;
    private String cartao;

    ToDadosBancarios(int codPessoa, String nome, String conta, String agencia, String cartao)
            throws SQLException {
        this.codPessoa = codPessoa;
        this.nome = nome;
        this.conta = conta;
        this.agencia = agencia;
        this.cartao = cartao;
        Banco banco = Banco.getBanco();
        banco.cadastraDadosBancarios(codPessoa, nome, conta, agencia, cartao);
    }

    public int getCodPessoa() {
        return codPessoa;
    }

    public String getNome() {
        return nome;
    }

    public String getConta() {
        return conta;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getCartao() {
        return cartao;
    }
}
