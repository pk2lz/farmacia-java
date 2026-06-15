package farmacia.modelos;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe abstrata Produto - base para todos os produtos da farmácia.
 * Define atributos e comportamentos comuns que serão compartilhados por todos os produtos.
 * Subclasses concretas: ProdutoHigiene, MedicamentoBasico, MedicamentoControlado.
 */
public abstract class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    // Código único e identificador do produto (chave primária)
    private int codigo;
    
    // Nome descritivo do produto
    private String nome;
    
    // Empresa responsável pela fabricação
    private String fabricante;
    
    // Preço unitário do produto em reais
    private double preco;

    /**
     * Construtor de Produto.
     * @param codigo o código único do produto
     * @param nome o nome do produto
     * @param fabricante o nome do fabricante
     * @param preco o preço do produto
     */
    public Produto(int codigo, String nome, String fabricante, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.fabricante = fabricante;
        this.preco = preco;
    }

    // ===== GETTERS E SETTERS =====

    /**
     * Retorna o código único do produto.
     * @return o código
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Modifica o código do produto.
     * @param codigo o novo código
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Retorna o nome do produto.
     * @return o nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Modifica o nome do produto.
     * @param nome o novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o fabricante do produto.
     * @return o nome do fabricante
     */
    public String getFabricante() {
        return fabricante;
    }

    /**
     * Modifica o fabricante do produto.
     * @param fabricante o novo fabricante
     */
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    /**
     * Retorna o preço do produto.
     * @return o preço unitário
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Modifica o preço do produto.
     * @param preco o novo preço
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Verifica se dois produtos são iguais comparando seus códigos.
     * Dois produtos com o mesmo código representam o mesmo produto.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return codigo == produto.codigo;
    }

    /**
     * Calcula hash baseado no código do produto.
     * Necessário quando usar Produto em HashSet ou HashMap.
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    /**
     * Representação em texto do produto com todos os atributos.
     */
    @Override
    public String toString() {
        return "Produto [codigo=" + codigo + ", nome=" + nome + ", fabricante=" + fabricante + ", preco=" + preco + "]";
    }
}
