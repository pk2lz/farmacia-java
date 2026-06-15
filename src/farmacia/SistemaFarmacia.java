package farmacia;

import farmacia.excecoes.PromocaoInvalidaException;
import farmacia.excecoes.RegraNegocioException;
import farmacia.modelos.*;
import farmacia.promocoes.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe SistemaFarmacia - gerencia toda a lógica de negócio da farmácia.
 * Responsável pelo cadastro de clientes, produtos e vendas, além de gerar relatórios
 * e persistir dados em arquivo.
 * Utiliza Streams da API Funcional do Java para consultas e agregações de dados.
 */
public class SistemaFarmacia {
    private static final String ARQUIVO_DADOS = "dados_farmacia.dat";

    private List<Cliente> clientes;
    private List<Produto> produtos;
    private List<Promocao> promocoes;
    private List<Venda> vendas;

    public SistemaFarmacia() {
        carregarDados();
    }

    // ===== MÉTODOS DE CADASTRO =====
    // Estes métodos adicionam novos dados ao sistema com validação de regras de negócio

    /**
     * Cadastra um novo cliente no sistema.
     * Valida se o CPF já não foi registrado para evitar duplicatas.
     * @param cliente o objeto Cliente a ser cadastrado
     * @throws RegraNegocioException se o CPF já existe no sistema
     */
    public void cadastrarCliente(Cliente cliente) throws RegraNegocioException {
        // Verifica se outro cliente já possui este CPF
        if (buscarClientePorCpf(cliente.getCpf()) != null) {
            throw new RegraNegocioException("Cliente com CPF " + cliente.getCpf() + " já cadastrado.");
        }
        // Se passou na validação, adiciona à lista
        clientes.add(cliente);
    }

    /**
     * Cadastra um novo produto no sistema.
     * Valida se o código já não foi registrado para evitar duplicatas.
     * @param produto o objeto Produto a ser cadastrado
     * @throws RegraNegocioException se o código do produto já existe no sistema
     */
    public void cadastrarProduto(Produto produto) throws RegraNegocioException {
        // Verifica se outro produto já possui este código
        if (buscarProdutoPorCodigo(produto.getCodigo()) != null) {
            throw new RegraNegocioException("Produto com código " + produto.getCodigo() + " já cadastrado.");
        }
        // Se passou na validação, adiciona à lista
        produtos.add(produto);
    }

    /**
     * Cadastra uma nova promoção no sistema.
     * Aplica validações específicas: medicamentos controlados não podem ter promoção Leve X Pague Y.
     * @param promocao a promoção a ser cadastrada
     * @throws RegraNegocioException se houver violação de regras de negócio
     */
    public void cadastrarPromocao(Promocao promocao) throws RegraNegocioException {
        // Valida se é uma promoção do tipo Leve X Pague Y
        if (promocao instanceof PromocaoLeveXPagueY) {
            PromocaoLeveXPagueY p = (PromocaoLeveXPagueY) promocao;
            // Busca o produto referenciado na promoção
            Produto prod = buscarProdutoPorCodigo(p.getCodigoProduto()).orElse(null);
            
            if (prod == null) {
                throw new PromocaoInvalidaException("Produto não encontrado para a promoção.");
            }
            
            // Regra de negócio: medicamentos controlados não participam dessa promoção
            if (prod instanceof MedicamentoControlado) {
                throw new PromocaoInvalidaException("Medicamento controlado não pode participar da promoção Leve X Pague Y.");
            }
        }
        // Se passou em todas as validações, adiciona a promoção
        promocoes.add(promocao);
    }

    /**
     * Remove uma promoção do sistema pelo seu índice na lista.
     * @param index o índice da promoção a remover
     * @throws RegraNegocioException se o índice for inválido
     */
    public void removerPromocao(int index) throws RegraNegocioException {
        // Valida se o índice está dentro do intervalo válido
        if (index < 0 || index >= promocoes.size()) {
            throw new RegraNegocioException("Índice de promoção inválido.");
        }
        // Remove a promoção no índice especificado
        promocoes.remove(index);
    }

    /**
     * Cadastra uma nova venda no sistema.
     * Calcula automaticamente valores com descontos aplicados e taxas específicas.
     * @param venda a venda a ser registrada
     */
    public void cadastrarVenda(Venda venda) {
        // Calcula valores finais aplicando todas as promoções disponíveis
        venda.calcularValores(promocoes);
        // Registra a venda no sistema
        vendas.add(venda);
    }

    // Estes métodos utilizam Streams para buscar dados de forma funcional

    /**
     * Busca um cliente pelo seu CPF.
     * @param cpf o CPF do cliente a buscar
     * @return um Optional contendo o cliente se encontrado, ou vazio se não encontrado
     */
    public Optional<Cliente> buscarClientePorCpf(String cpf) {
        // Usa Stream para filtrar e retornar o primeiro cliente com CPF correspondente
        return clientes.stream().filter(c -> c.getCpf().equals(cpf)).findFirst();
    }

    /**
     * Busca um produto pelo seu código numérico.
     * @param codigo o código do produto a buscar
     * @return um Optional contendo o produto se encontrado, ou vazio se não encontrado
     */
    public Optional<Produto> buscarProdutoPorCodigo(int codigo) {
        // Usa Stream para filtrar e retornar o primeiro produto com código correspondente
        return produtos.stream().filter(p -> p.getCodigo() == codigo).findFirst();
    }

    // Estes métodos geram relatórios consultando dados com programação funcional

    /**
     * Lista todas as vendas online com seus dados principais.
     * Exibe: código da venda, endereço do cliente e e-mail.
     */
    public void listarVendasOnline() {
        System.out.println("=== VENDAS ONLINE ===");
        // Filtra vendas do tipo VendaOnline e exibe as informações do cliente
        vendas.stream()
            .filter(v -> v instanceof VendaOnline)
            .map(v -> (VendaOnline) v)
            .forEach(v -> {
                System.out.println("Venda Cód: " + v.getCodigo() + 
                                   " | Endereço: " + v.getCliente().getEndereco() + 
                                   " | E-mail: " + v.getCliente().getEmail());
            });
    }

    /**
     * Lista todos os medicamentos controlados vendidos com suas quantidades totais.
     * Agrupa e soma quantidades por nome do medicamento.
     */
    public void listarMedicamentosControladosVendidos() {
        System.out.println("=== MEDICAMENTOS CONTROLADOS VENDIDOS ===");
        
        // Agrupa os itens vendidos que são medicamentos controlados e soma as quantidades
        // utiliza Map com collector groupingBy e summingInt
        Map<String, Integer> consolidados = vendas.stream()
            .flatMap(v -> v.getItens().stream())
            .filter(item -> item.getProduto() instanceof MedicamentoControlado)
            .collect(Collectors.groupingBy(
                item -> item.getProduto().getNome(),
                Collectors.summingInt(ItemVenda::getQuantidade)
            ));

        if (consolidados.isEmpty()) {
            System.out.println("Nenhum medicamento controlado foi vendido.");
        } else {
            // Exibe cada medicamento e sua quantidade total
            consolidados.forEach((nome, qtde) -> {
                System.out.println("Medicamento: " + nome + " | Quantidade: " + qtde);
            });
        }
    }

    /**
     * Lista o valor total de descontos aplicados em cada venda.
     */
    public void listarDescontosPorVenda() {
        System.out.println("=== TOTAL DE DESCONTOS POR VENDA ===");
        // Itera sobre cada venda exibindo o código e o valor total descontado
        vendas.forEach(v -> {
            System.out.println("Venda Cód: " + v.getCodigo() + " | Total Descontos: R$ " + String.format("%.2f", v.getValorDesconto()));
        });
    }

    /**
     * Lista o valor total (com descontos e taxas) de cada venda.
     */
    public void listarValorTotalPorVenda() {
        System.out.println("=== VALOR TOTAL REALIZADO POR VENDA ===");
        // Itera sobre cada venda exibindo código e valor final após descontos e taxas
        vendas.forEach(v -> {
            System.out.println("Venda Cód: " + v.getCodigo() + " | Valor Final (com descontos e taxas): R$ " + String.format("%.2f", v.getValorTotalComDesconto()));
        });
    }

    /**
     * Lista os fabricantes ordenados pela quantidade total de produtos vendidos.
     * Mostra o fabricante com mais produtos vendidos primeiro (ordem decrescente).
     */
    public void listarFabricantesMaisVendidos() {
        System.out.println("=== FABRICANTES COM MAIOR QUANTIDADE DE PRODUTOS VENDIDOS ===");
        
        // Agrupa itens vendidos por fabricante e soma quantidades de cada um
        Map<String, Integer> fabricanteQtd = vendas.stream()
            .flatMap(v -> v.getItens().stream())
            .collect(Collectors.groupingBy(
                item -> item.getProduto().getFabricante(),
                Collectors.summingInt(ItemVenda::getQuantidade)
            ));

        // Ordena em ordem decrescente por quantidade de produtos vendidos e exibe
        fabricanteQtd.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(entry -> {
                System.out.println("Fabricante: " + entry.getKey() + " | Produtos vendidos: " + entry.getValue());
            });
    }

    // Métodos para carregar e salvar dados em arquivo (serialização)

    /**
     * Carrega os dados do sistema a partir de um arquivo serializado.
     * Se o arquivo não existir, inicializa listas vazias e exibe mensagem informativa.
     * É chamado automaticamente no construtor.
     */
    @SuppressWarnings("unchecked")
    private void carregarDados() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                // Desserializa os objetos na mesma ordem em que foram salvos
                clientes = (List<Cliente>) ois.readObject();
                produtos = (List<Produto>) ois.readObject();
                promocoes = (List<Promocao>) ois.readObject();
                vendas = (List<Venda>) ois.readObject();
                System.out.println("Dados carregados com sucesso do arquivo " + ARQUIVO_DADOS);
            } catch (Exception e) {
                // Em caso de erro na leitura, começa do zero
                System.err.println("Erro ao carregar dados: " + e.getMessage());
                inicializarListas();
            }
        } else {
            System.out.println("Arquivo de dados não encontrado. Iniciando sistema vazio.");
            inicializarListas();
        }
    }

    /**
     * Salva todos os dados do sistema em um arquivo serializado.
     * Salva clientes, produtos, promoções e vendas nesta ordem exata.
     */
    public void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            // Serializa os objetos na mesma ordem para depois desserializar corretamente
            oos.writeObject(clientes);
            oos.writeObject(produtos);
            oos.writeObject(promocoes);
            oos.writeObject(vendas);
            System.out.println("Dados salvos com sucesso no arquivo " + ARQUIVO_DADOS);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Inicializa todas as listas como ArrayLists vazias.
     * É chamado quando o arquivo de dados não existe ou há erro na leitura.
     */
    private void inicializarListas() {
        clientes = new ArrayList<>();
        produtos = new ArrayList<>();
        promocoes = new ArrayList<>();
        vendas = new ArrayList<>();
    }

    // Métodos para acessar dados do sistema

    /**
     * Retorna a lista de promoções ativas do sistema.
     * @return a lista de promoções
     */
    public List<Promocao> getPromocoes() {
        return promocoes;
    }

    public static String getArquivoDados() {
        return ARQUIVO_DADOS;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void setPromocoes(List<Promocao> promocoes) {
        this.promocoes = promocoes;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }
}
