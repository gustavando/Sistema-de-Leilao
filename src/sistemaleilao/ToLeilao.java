/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaleilao;

/**
 *
 * @author Gustavo Torres Custódio, Mateus Assis Rocha
 */
public class ToLeilao {

    private int codLeilao;
    private int codLeiloeiro;
    private int codProduto;
    private double valorMinimo;
    private int codigoArrematante;
    private boolean aberto;

    ToLeilao(int codLeilao, int codLeiloeiro, int codProduto, double valorMinimo,
            int codigoArrematante) {
        this.codLeilao = codLeilao;
        this.codLeiloeiro = codLeiloeiro;
        this.codProduto = codProduto;
        this.valorMinimo = valorMinimo;
        this.codigoArrematante = codigoArrematante;
        this.aberto = true;

    }

    public int getCodLeilao() {
        return codLeilao;
    }

    public double getValorMinimo() {
        return valorMinimo;
    }
}
