package farmacia;

import farmacia.excecoes.ClienteNaoEncontradoException;
import farmacia.excecoes.RegraNegocioException;
import farmacia.modelos.*;
import farmacia.promocoes.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe Main - ponto de entrada do sistema de farmácia.
 * Responsável por exibir o menu e gerenciar a interação com o usuário através de um loop principal.
 * Utiliza a classe SistemaFarmacia para executar todas as operações de negócio.
 */
public class Main {
    // Instância do sistema de farmácia que centraliza toda a lógica de negócio
    private static SistemaFarmacia sistema;
    
    // Scanner para leitura de entrada do usuário via console
    private static Scanner scanner;

    /**
     * Método main - ponto de entrada do programa.
     * Inicializa o sistema e abre um loop infinito que exibe o menu até o usuário escolher sair.
     * Trata exceções de regras de negócio e outras exceções inesperadas.
     */
    public static void main(String[] args) {
        // Inicializa o sistema (carrega dados do arquivo se existirem)
        sistema = new SistemaFarmacia();
        
        // Cria o scanner para ler entrada do usuário
        scanner = new Scanner(System.in);
        
        // Flag para controlar o loop principal
        boolean executando = true;

        // Loop principal do programa - continua até o usuário escolher sair (opção 0)
        while (executando) {
            // Exibe o menu de opções
            exibirMenu();
            
            // Lê a opção do usuário com validação de entrada
            int opcao = lerInteiro("Escolha uma opção: ");

            try {
                // Despacha para o método correspondente baseado na opção escolhida
                switch (opcao) {
                    case 1 -> menuCadastrarCliente();
                    case 2 -> menuCadastrarProduto();
                    case 3 -> menuGerenciarPromocoes();
                    case 4 -> menuCadastrarVenda();
                    case 5 -> sistema.listarVendasOnline();
                    case 6 -> sistema.listarMedicamentosControladosVendidos();
                    case 7 -> sistema.listarDescontosPorVenda();
                    case 8 -> sistema.listarValorTotalPorVenda();
                    case 9 -> sistema.listarFabricantesMaisVendidos();
                    case 0 -> {
                        // Opção para encerrar: salva dados em arquivo e sai do loop
                        System.out.println("Salvando dados e encerrando...");
                        sistema.salvarDados();
                        executando = false;
                    }
                    default -> System.out.println("Opção inválida!");
                }
            } catch (RegraNegocioException e) {
                // Captura erros de validação de regras de negócio
                System.out.println("ERRO DE NEGÓCIO: " + e.getMessage());
            } catch (Exception e) {
                // Captura qualquer outra exceção inesperada
                System.out.println("ERRO INESPERADO: " + e.getMessage());
                scanner.nextLine(); // limpar buffer de entrada
            }
            
            System.out.println();
        }
        
        // Libera os recursos utilizados pelo scanner
        scanner.close();
    }

    /**
     * Exibe o menu principal no console com todas as operações disponíveis.
     */
    private static void exibirMenu() {
        System.out.println("=====================================");
        System.out.println("        SISTEMA DA FARMÁCIA        ");
        System.out.println("=====================================");
        System.out.println("1. Cadastrar um novo cliente");
        System.out.println("2. Cadastrar um novo produto");
        System.out.println("3. Cadastrar ou remover uma promoção");
        System.out.println("4. Cadastrar uma venda");
        System.out.println("5. Listar vendas online (código, endereço e e-mail)");
        System.out.println("6. Listar medicamentos controlados vendidos (e quantidades)");
        System.out.println("7. Listar valor total em descontos por venda");
        System.out.println("8. Listar valor total realizado para cada venda");
        System.out.println("9. Listar fabricantes com mais produtos vendidos");
        System.out.println("0. Encerrar o programa");
        System.out.println("=====================================");
    }

    /**
     * Menu para cadastrar um novo cliente no sistema.
     * Coleta dados do cliente (nome, email, endereço, CPF) e salva no banco de dados.
     * @throws RegraNegocioException se o CPF já estiver registrado
     */
    private static void menuCadastrarCliente() throws RegraNegocioException {
        System.out.println("--- CADASTRAR CLIENTE ---");
        String nome = lerString("Nome: ");
        String email = lerString("E-mail: ");
        String endereco = lerString("Endereço: ");
        String cpf = lerString("CPF: ");

        Cliente cliente = new Cliente(nome, email, endereco, cpf);
        sistema.cadastrarCliente(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    /**
     * Menu para cadastrar um novo produto no sistema.
     * Permite escolher entre três tipos: Higiene, Medicamento Básico ou Controlado.
     * Coleta dados comuns (código, nome, fabricante, preço) e específicos de cada tipo.
     * @throws RegraNegocioException se o código do produto já estiver cadastrado
     */
    private static void menuCadastrarProduto() throws RegraNegocioException {
        System.out.println("--- CADASTRAR PRODUTO ---");
        System.out.println("1. Produto de Higiene");
        System.out.println("2. Medicamento Básico (Sem receita)");
        System.out.println("3. Medicamento Controlado (Com receita)");
        int tipo = lerInteiro("Tipo de produto: ");

        int codigo = lerInteiro("Código (inteiro): ");
        String nome = lerString("Nome do produto: ");
        String fabricante = lerString("Fabricante: ");
        double preco = lerDouble("Preço (ex: 10,50 ou 10.50): ");

        Produto produto;

        if (tipo == 1) {
            produto = new ProdutoHigiene(codigo, nome, fabricante, preco);
        } else if (tipo == 2 || tipo == 3) {
            String pAtivo = lerString("Princípio Ativo: ");
            String dosagem = lerString("Dosagem (ex: 500mg): ");
            if (tipo == 2) {
                produto = new MedicamentoBasico(codigo, nome, fabricante, preco, pAtivo, dosagem);
            } else {
                produto = new MedicamentoControlado(codigo, nome, fabricante, preco, pAtivo, dosagem);
            }
        } else {
            System.out.println("Tipo inválido. Operação cancelada.");
            return;
        }

        sistema.cadastrarProduto(produto);
        System.out.println("Produto cadastrado com sucesso!");
    }

    /**
     * Menu para gerenciar promoções ativas no sistema.
     * Permite cadastrar dois tipos de promoção (Leve X Pague Y ou Desconto Percentual) ou remover existentes.
     * Validações adicionais garantem que medicamentos controlados não participem de promoções.
     * @throws RegraNegocioException se houver violação de regras de negócio
     */
    private static void menuGerenciarPromocoes() throws RegraNegocioException {
        System.out.println("--- GERENCIAR PROMOÇÕES ---");
        System.out.println("1. Cadastrar Promoção 'Leve X Pague Y'");
        System.out.println("2. Cadastrar Promoção '% de Desconto'");
        System.out.println("3. Remover Promoção");
        int opcao = lerInteiro("Escolha: ");

        if (opcao == 1) {
            int codigo = lerInteiro("Código do produto: ");
            int x = lerInteiro("Quantidade necessária (Leve X): ");
            int y = lerInteiro("Quantidade cobrada (Pague Y): ");
            sistema.cadastrarPromocao(new PromocaoLeveXPagueY(codigo, x, y));
            System.out.println("Promoção cadastrada com sucesso!");
        } else if (opcao == 2) {
            int codigo = lerInteiro("Código do produto: ");
            double desconto = lerDouble("Percentual de desconto (ex: 15 para 15%): ");
            sistema.cadastrarPromocao(new PromocaoPercentual(codigo, desconto));
            System.out.println("Promoção cadastrada com sucesso!");
        } else if (opcao == 3) {
            System.out.println("Promoções ativas:");
            for (int i = 0; i < sistema.getPromocoes().size(); i++) {
                System.out.println("[" + i + "] " + sistema.getPromocoes().get(i));
            }
            int index = lerInteiro("Índice da promoção a remover: ");
            sistema.removerPromocao(index);
            System.out.println("Promoção removida!");
        } else {
            System.out.println("Opção inválida.");
        }
    }

    /**
     * Menu para cadastrar uma nova venda no sistema.
     * Permite dois tipos: Presencial ou Online.
     * Para vendas online, requer um cliente cadastrado. Medicamentos controlados só podem ser vendidos presencialmente.
     * O usuário adiciona itens um por um até digitar -1 para finalizar.
     * @throws RegraNegocioException se ocorrer alguma violação de regra de negócio
     */
    private static void menuCadastrarVenda() throws RegraNegocioException {
        System.out.println("--- CADASTRAR VENDA ---");
        System.out.println("1. Venda Presencial");
        System.out.println("2. Venda Online");
        int tipo = lerInteiro("Tipo de venda: ");

        int codigoVenda = lerInteiro("Código da venda (inteiro): ");
        Venda venda;

        boolean isOnline = (tipo == 2);

        if (isOnline) {
            String cpf = lerString("Informe o CPF do cliente cadastrado: ");
            Cliente cliente = sistema.buscarClientePorCpf(cpf)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado. Cadastre o cliente primeiro."));
            venda = new VendaOnline(codigoVenda, cliente);
        } else if (tipo == 1) {
            venda = new VendaPresencial(codigoVenda);
        } else {
            System.out.println("Tipo inválido. Operação cancelada.");
            return;
        }

        // Loop para adicionar múltiplos produtos à venda
        boolean adicionando = true;
        while (adicionando) {
            // Lê o código do produto (ou -1 para sair do loop)
            int codProduto = lerInteiro("Código do Produto a adicionar (ou -1 para finalizar): ");
            // Sinal para o usuário parar de adicionar produtos
            if (codProduto == -1) {
                break;
            }

            // Busca o produto no sistema
            var produto = sistema.buscarProdutoPorCodigo(codProduto)
                .orElseGet(() -> {
                    System.out.println("Erro: Produto não encontrado.");
                    return null;
                });
            
            // Se o produto não foi encontrado, volta para o início do loop
            if (produto == null) {
                continue;
            }

            // Validação: medicamentos controlados não podem ser vendidos online
            if (isOnline && (produto instanceof MedicamentoControlado)) {
                System.out.println("Erro: Medicamentos controlados não podem ser vendidos online!");
                continue;
            }

            // Lê a quantidade com validação
            int quantidade = lerInteiro("Quantidade: ");
            if (quantidade <= 0) {
                System.out.println("Erro: Quantidade deve ser maior que zero.");
                continue;
            }

            // Adiciona o item à venda
            venda.adicionarItem(new ItemVenda(produto, quantidade));
            System.out.println(quantidade + "x " + produto.getNome() + " adicionado(s) à venda.");
        }

        // Validação: não permite venda sem itens
        if (venda.getItens().isEmpty()) {
            System.out.println("Venda cancelada: Nenhum item adicionado.");
            return;
        }

        // Registra a venda e calcula valores com descontos e taxas
        sistema.cadastrarVenda(venda);
        System.out.println("Venda registrada com sucesso!");
        System.out.println("Valor total (com descontos/taxas): R$ " + String.format("%.2f", venda.getValorTotalComDesconto()));
    }

    // ===== MÉTODOS AUXILIARES PARA LEITURA DE ENTRADA DO USUÁRIO =====
    // Estes métodos validam e tratam as entradas do usuário, evitando erros de tipo

    /**
     * Lê uma String do usuário, exibindo uma mensagem e removendo espaços em branco.
     * @param mensagem a mensagem a exibir para o usuário
     * @return a String digitada pelo usuário
     */
    private static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    /**
     * Lê um inteiro do usuário com validação.
     * Se o usuário digitar algo que não seja um número, pede para tentar novamente.
     * @param mensagem a mensagem a exibir para o usuário
     * @return o inteiro digitado pelo usuário
     */
    private static int lerInteiro(String mensagem) {
        // Loop infinito até conseguir um inteiro válido
        while (true) {
            try {
                System.out.print(mensagem);
                int valor = scanner.nextInt();
                scanner.nextLine(); // Consome o caractere de nova linha
                return valor;
            } catch (InputMismatchException e) {
                // Tratamento de entrada inválida
                System.out.println("Entrada inválida. Digite um número inteiro.");
                scanner.nextLine(); // Limpa o buffer de entrada
            }
        }
    }

    /**
     * Lê um valor em ponto flutuante (double) do usuário com validação.
     * Aceita números com vírgula ou ponto decimal, convertendo para formato padrão.
     * @param mensagem a mensagem a exibir para o usuário
     * @return o valor double digitado pelo usuário
     */
    private static double lerDouble(String mensagem) {
        // Loop infinito até conseguir um double válido
        while (true) {
            try {
                System.out.print(mensagem);
                // Substitui vírgula por ponto para aceitar ambos os formatos
                String entrada = scanner.nextLine().trim().replace(",", ".");
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                // Tratamento de entrada inválida
                System.out.println("Entrada inválida. Digite um valor numérico válido.");
            }
        }
    }
}
