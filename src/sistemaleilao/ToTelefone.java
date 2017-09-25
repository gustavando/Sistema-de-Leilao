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
public class ToTelefone {

    private int codPessoa;
    private int DDD;
    private long telefone;

    ToTelefone(int codPessoa, int DDD, long telefone) throws SQLException {
        this.codPessoa = codPessoa;
        this.DDD = DDD;
        this.telefone = telefone;
        Banco banco = Banco.getBanco();
        banco.cadastraTelefone(codPessoa, DDD, telefone);
    }

    public int getCodPessoa() {
        return codPessoa;
    }

    public int getDDD() {
        return DDD;
    }

    public long getTelefone() {
        return telefone;
    }
}
