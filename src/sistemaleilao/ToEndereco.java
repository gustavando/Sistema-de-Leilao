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
public class ToEndereco {

    private int codPessoa;
    private String rua;
    private String bairro;
    private String cidade;
    private String UF;
    private String CEP;
    private int numero;
    private String complemento;

    ToEndereco(int codPessoa, String rua, String bairro, String cidade, String UF, String CEP,
            int numero, String complemento) throws SQLException {
        this.codPessoa = codPessoa;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.UF = UF;
        this.CEP = CEP;
        this.numero = numero;
        this.complemento = complemento;
        Banco banco = Banco.getBanco();
        banco.cadastraEndereco(codPessoa, rua, bairro, cidade, UF, CEP, numero, complemento);

    }

    public int getCodPessoa() {
        return codPessoa;
    }

    public String getRua() {
        return rua;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUF() {
        return UF;
    }
}
