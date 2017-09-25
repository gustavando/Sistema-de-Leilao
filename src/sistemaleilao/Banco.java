package sistemaleilao;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/*
 * ;
 *
 * @author Gustavo Torres Custódio, Mateus Assis Rocha
 */

/*
 * O construtor desta classe pode ser executado somente uma vez e ela é
 * responsável por toda a comunicação com o banco de dados (Padrão Singleton e
 * DAO)
 */
public final class Banco {

    public static final Banco banco = new Banco();
    private Connection con;
    private Statement stmt;

    // Utiliza o driver do mySQL para se conectar ao banco de dados leilao
    private Banco() {
        //Nome do Driver
        String driverName = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driverName);
            String username = "root";
            String senha = "senha3141592654";
            String url = "jdbc:mysql://localhost/leilao";
            con = DriverManager.getConnection(url, username, senha);
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao se conectar");
        } catch (SQLException e) {
            System.err.println("Erro no banco de dados");
        }
    }

    public static Banco getBanco() {

        return banco;
    }

    // Quando o usuário tenta se logar,as informações são verificadas
    public Pessoa selecionaPessoa(String login, String senha) throws SQLException {
        Pessoa pessoa = null;
        int codLogin = 0;
        int codPessoa = 0;
        //cria Statement
        stmt = con.createStatement();
        String query = "Select * from tbl_login where LGN_C_LOGIN='" + login
                + "' and LGN_C_SENHA='" + senha + "'";
        //Armazena os resultados que corresponndem à pesquisa do SQL
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            // Armazena o número de login correspondente
            codLogin = rs.getInt("LGN_N_CODIGO");
        }
        stmt = con.createStatement();
        //Encontra a pessoa pelo login
        query = "Select * from tbl_pessoa where PES_LGN_N_CODIGO=" + codLogin;
        rs = stmt.executeQuery(query);
        //Se a pessoa não for encontrada, o retorno é null
        if (!rs.next()) {
            return pessoa;
        }
        while (rs.next()) {
            codPessoa = rs.getInt("PES_N_CODIGO");
        }
        pessoa = new Pessoa(codPessoa, codLogin);
        rs.close();
        stmt.close();
        return pessoa;
    }

    public void cadastraEndereco(int codPessoa, String rua, String bairro,
            String cidade, String UF, String CEP, int numero, String complemento) throws SQLException {
        //Cadastra, na tabela tbl_endereco, as informações de endereço fornecidas pelo usuário
        String query = "Insert into TBL_ENDERECO (END_PES_N_CODIGO,END_C_LOGRADOURO,"
                + "END_C_BAIRRO,END_C_CIDADE,END_C_UF,END_N_CEP,END_N_NUMERO,END_C_COMPLEMENTO)"
                + "values(" + codPessoa + ",'" + rua + "','"
                + bairro + "','" + cidade + "','" + UF + "','" + CEP + "'," + numero + ",'" + complemento + "')";
        stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
    }

    public void cadastraTelefone(int codPessoa, int DDD, long telefone) throws SQLException {
        //Cadastra, na tabela tbl_telefone, as informações de telefone fornecidas pelo usuário
        String query = "Insert into TBL_TELEFONE values(TLF_N_CODIGO," + codPessoa
                + "," + DDD + "," + telefone + ")";
        stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
    }

    public void cadastraDadosBancarios(int codPessoa, String nome, String conta,
            String agencia, String cartao) throws SQLException {
        //Cadastra, na tabela tbl_Dados_Bancarios, as informações dos dados bancários fornecidas pelo usuário
        String query = "Insert into TBL_DADOS_BANCARIOS values(DBC_N_CODIGO," + codPessoa + ",'"
                + nome + "','" + conta + "','" + agencia + "','" + cartao + "')";

        stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();

    }

    public int retornaCodigoLogin() throws SQLException {

        /*
         * Retorna o maior número da PK, em ordem crescente, da tabela
         * tbl_login. o usuário não escolhe o código de cadastro que recebe do
         * sistema
         */
        int codLogin = 0;

        stmt = con.createStatement();

        String query = "Select MAX(LGN_N_CODIGO) from tbl_login";

        ResultSet rs = stmt.executeQuery(query);
        //Roda enquanto não cehagr no fim da tabela
        while (rs.next()) {
            codLogin = rs.getInt("MAX(LGN_N_CODIGO)");
        }
        rs.close();
        stmt.close();
        return codLogin;
    }

    public int retornaCodigoPessoa() throws SQLException {
        /*
         * Retorna o maior número da PK, em ordem crescente, da tabela
         * tbl_pessoa. o usuário não escolhe o código de cadastro que recebe do
         * sistema
         */
        int codPessoa = 0;

        stmt = con.createStatement();

        String query = "Select MAX(PES_N_CODIGO) from tbl_pessoa";

        ResultSet rs = stmt.executeQuery(query);

        //Roda enquanto não cehagr no fim da tabela
        while (rs.next()) {
            codPessoa = rs.getInt("MAX(PES_N_CODIGO)");
        }
        rs.close();
        stmt.close();
        return codPessoa;
    }

    //Cadastra o login e a senha do usuário
    public boolean cadastraLogin(int codLogin, String login, String senha) throws SQLException {
        String query = "select * from tbl_login where LGN_C_LOGIN='" + login + "'";
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        /*
         * Se for encontrado alguma linha da tabela tbl_login com o mesmo nome
         * de usuário, ele para o cadastro e retorna falso
         */
        if (rs.next()) {
            return false;
        }
        /*
         * Se não houver duplicatas de nome de usuário, é cadastrado normalmente
         * e retorna verdadeiro
         */
        query = "Insert into TBL_LOGIN (LGN_N_CODIGO,LGN_C_LOGIN,LGN_C_SENHA)"
                + "values (" + codLogin + ",'" + login + "','" + senha + "')";
        stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
        return true;
    }

    //Cadastra a pessoa e recupera seu código
    public void cadastraPessoa(int codPessoa, int codLogin, String nome, String cpf,
            char sexo, String uf) throws SQLException {
        //Query de inserçao das informações da pessoa na tabela
        String query = "Insert into TBL_PESSOA (PES_N_CODIGO,PES_LGN_N_CODIGO,"
                + "PES_C_NOME,PES_C_CPF,PES_C_SEXO,PES_C_UF_NASCIMENTO) values"
                + "(" + codPessoa + "," + codLogin + ",'" + nome + "','" + cpf
                + "','" + sexo + "','" + uf + "')";
        //Abre o statement
        stmt = con.createStatement();
        //Executa o query
        stmt.executeUpdate(query);
        //fecha o statement;
        stmt.close();
    }
    /*
     * A classe Pessoa recebe o maior código de leilão da tabela tbl_leilao,
     * assim, evita PKs iguais no momento de cadastrar um produto
     */

    public int retornaCodigoLeilao() throws SQLException {
        int codLeilao = 0;
        stmt = con.createStatement();
        //Seleciona o código máximo
        String query = "Select MAX(LIL_N_CODIGO) from tbl_leilao";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            codLeilao = rs.getInt("MAX(LIL_N_CODIGO)");
        }
        rs.close();
        stmt.close();
        return codLeilao;
    }

    public int retornaCodigoProduto() throws SQLException {
        /*
         * Mesmo princípio do método retornaCodigoLeilao()
         */
        int codProduto = 0;
        stmt = con.createStatement();
        //Seleciona o código máximo
        String query = "Select MAX(PDT_N_CODIGO) from tbl_produto";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            codProduto = rs.getInt("MAX(PDT_N_CODIGO)");
        }
        rs.close();
        stmt.close();
        return codProduto;
    }
    // Recebe os parâmetros da classe Pessoa e cadastra um novo produto no banco de dados

    public void cadastraProduto(int codProduto, int codLogin, String nome, int tempo, double preco, String estado,
            String descricao) throws SQLException {
        //Insere dados na tabela tbl_pessoa
        String query = "insert into tbl_produto (PDT_N_CODIGO,PDT_LGN_N_CODIGO,PDT_C_NOME,"
                + "PDT_N_TEMPO_USO,PDT_C_ESTADO_CONSERVACAO,PDT_D_VALOR,PDT_C_DESCRICAO) "
                + "values(" + codProduto + "," + codLogin + ",'" + nome + "'," + tempo + ",'" + estado + "',"
                + preco + ",'" + descricao + "')";
        stmt = con.createStatement();

        stmt.executeUpdate(query);
        stmt.close();
    }
    // Recebe os parâmetros da classe Pessoa e cadastra um novo leilao no banco de dados

    public void cadastraLeilao(int codLeilao, int codProduto, int codLeiloeiro, double valor)
            throws SQLException {
        //Insere dados na tabela tbl_pessoa
        String query = "insert into tbl_leilao(LIL_N_CODIGO,LIL_LGN_LEILOEIRO_N_CODIGO,"
                + "LIL_PDT_N_CODIGO,LIL_D_VALOR_MINIMO,LIL_LGN_ARREMATANTE_N_CODIGO,LIL_ABERTO)"
                + "values(" + codLeilao + "," + codLeiloeiro + "," + codProduto + "," + valor + "," + codLeiloeiro + "," + 1 + ")";
        stmt = con.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
    }
    /*
     * Encerra o leilão. Existe uma coluna na tabela tbl_leilao que tem valor
     * verdadeiro ou falso. Os leilões abertos são verdadeiros e os fechados são
     * falsos. Este método muda o valor de verdadeiro para falso e avisa ao
     * usuário quanto ele conseguiu com o leilão.
     */

    public void encerraLeilao(int codLogin, int codLeilao) throws SQLException {
        /*
         * O usuário escolhe um número de um leilão correspondente para ser
         * fechado. O sistema confere se esse leilão existe e foi aberto pelo
         * usuário
         */
        String query = "Update tbl_leilao set LIL_ABERTO=" + false + " where LIL_N_CODIGO=" + codLeilao
                + " and LIL_LGN_LEILOEIRO_N_CODIGO=" + codLogin; //Atualiza tabela
        String queryBusca = "Select * from TBL_LEILAO where LIL_N_CODIGO=" + codLeilao
                + " and LIL_LGN_LEILOEIRO_N_CODIGO=" + codLogin; // Seleciona itens
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(queryBusca);
        if (!rs.next()) {
            //Verifica se o leilão que o usuário tentou fechar pertence a ele
            JOptionPane.showMessageDialog(null, "Seu código de usuário não corresponde ao leilão",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            JOptionPane.showMessageDialog(null,
                    "Vendido por R$ " + rs.getDouble("LIL_D_VALOR_MINIMO"),
                    "Vendido", JOptionPane.DEFAULT_OPTION);
        }
        stmt.executeUpdate(query);
    }

    /*
     * Confere se o lance dado pelo usuário é maior do que o lance atual e se o
     * leilão no qual ele tentou dar um lance existe. Se existir e for maior, chama
     * o método atualizaLeilao()
     */
    public void verificaLance(int codLogin, int codLeilao, double lance) throws SQLException {
        String query = "select * from tbl_Leilao where LIL_N_CODIGO=" + codLeilao;
        //verificar se o lance é o maior
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            if (rs.getDouble("LIL_D_VALOR_MINIMO") < lance) {
                atualizaLeilao(codLeilao, codLogin, lance);
                JOptionPane.showMessageDialog(null, "Lance registrado com sucesso", "Sucesso",
                        JOptionPane.DEFAULT_OPTION);
            } else {
                JOptionPane.showMessageDialog(null, "Erro, lance muito baixo",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    // Atualiza o valor do lance mínimo no leilão
    public void atualizaLeilao(int codLeilao, int codLogin, double lance) throws SQLException {
        String query = "update tbl_Leilao set LIL_D_VALOR_MINIMO=" + lance + ", "
                + "LIL_LGN_ARREMATANTE_N_CODIGO=" + codLogin + " where LIL_N_CODIGO=" + codLeilao;
        stmt = con.createStatement();
        stmt.executeUpdate(query);
    }

    /* Seleciona um produto na tabela, por código, e devolve uma String com o nome
     * do produto correspondente.
     */
    public String selecionaProduto(int codProduto) throws SQLException {
        Produto produto = null;
        String query = "Select * from tbl_produto where PDT_N_CODIGO=" + codProduto;
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            int codigoProduto = rs.getInt("PDT_N_CODIGO");
            int codLeiloeiro = rs.getInt("PDT_LGN_N_CODIGO");
            String nomeProduto = rs.getString("PDT_C_NOME");
            int tempoUso = rs.getInt("PDT_N_TEMPO_USO");
            String estadoProduto = rs.getString("PDT_C_ESTADO_CONSERVACAO");
            double valor = rs.getDouble("PDT_D_VALOR");
            String descricao = rs.getString("PDT_C_DESCRICAO");
            produto = new Produto(codigoProduto, codLeiloeiro, nomeProduto, valor,
                    estadoProduto, tempoUso, descricao);
        }
        return produto.getNomeProduto();
    }

    /*
     * Procura no banco de dados por leilões abertos que estão relacionados com
     * o código do usuário (leilões abertos pelo usuário)
     */
    public ArrayList<String> carregaLeiloes(int codLogin) throws SQLException {
        String query = "Select * from TBL_LEILAO where LIL_LGN_LEILOEIRO_N_CODIGO="
                + codLogin + " and LIL_ABERTO=1";
        ArrayList<String> leiloes = new ArrayList<String>();
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            int codigoLeilao = rs.getInt("LIL_N_CODIGO");
            int codLeiloeiro = rs.getInt("LIL_LGN_LEILOEIRO_N_CODIGO");
            int codProduto = rs.getInt("LIL_PDT_N_CODIGO");
            double valorMinimo = rs.getDouble("LIL_D_VALOR_MINIMO");
            int codigoArrematante = rs.getInt("LIL_LGN_ARREMATANTE_N_CODIGO");
            ToLeilao leilao = new ToLeilao(codigoLeilao, codLeiloeiro, codProduto,
                    valorMinimo, codigoArrematante);
            // Adiciona ao ArrayList o código do leilão e o nome do produto em leilão
            leiloes.add(leilao.getCodLeilao() + " " + this.selecionaProduto(codProduto)
                    + " / R$ " + leilao.getValorMinimo());
        }
        return leiloes;
    }

    //Procura no banco de dados por todos os leilões abertos
    public ArrayList<String> carregaLeiloes() throws SQLException {
        String query = "Select * from TBL_LEILAO where LIL_ABERTO=1";
        ArrayList<String> leiloes = new ArrayList<String>();

        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            int codigoLeilao = rs.getInt("LIL_N_CODIGO");
            int codLeiloeiro = rs.getInt("LIL_LGN_LEILOEIRO_N_CODIGO");
            int codProduto = rs.getInt("LIL_PDT_N_CODIGO");
            double valorMinimo = rs.getDouble("LIL_D_VALOR_MINIMO");
            int codigoArrematante = rs.getInt("LIL_LGN_ARREMATANTE_N_CODIGO");
            ToLeilao leilao = new ToLeilao(codigoLeilao, codLeiloeiro, codProduto,
                    valorMinimo, codigoArrematante);

            leiloes.add(leilao.getCodLeilao() + " / " + this.selecionaProduto(codProduto)
                    + " / R$ " + leilao.getValorMinimo());
        }

        return leiloes;
    }
}