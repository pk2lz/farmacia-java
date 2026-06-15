package farmacia.modelos;

import java.io.Serializable;
import java.util.Objects;

/**
 * Classe Cliente - representa um cliente cadastrado na farmácia.
 * Armazena informações pessoais necessárias para contato e entrega de compras online.
 */
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    // Nome completo do cliente
    private String nome;
    
    // Email para contato
    private String email;
    
    // Endereço para entrega de pedidos online
    private String endereco;
    
    // CPF (Cadastro Pessoas Física) - identificador único de cada cliente
    private String cpf;

    /**
     * Construtor de Cliente.
     * @param nome o nome do cliente
     * @param email o e-mail do cliente
     * @param endereco o endereço de entrega do cliente
     * @param cpf o CPF do cliente
     */
    public Cliente(String nome, String email, String endereco, String cpf) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.cpf = cpf;
    }

    // ===== GETTERS E SETTERS =====

    /**
     * Retorna o nome do cliente.
     * @return o nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Modifica o nome do cliente.
     * @param nome o novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o email do cliente.
     * @return o email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modifica o email do cliente.
     * @param email o novo email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna o endereço do cliente.
     * @return o endereço para entrega
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Modifica o endereço do cliente.
     * @param endereco o novo endereço
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Retorna o CPF do cliente.
     * @return o CPF
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Modifica o CPF do cliente.
     * @param cpf o novo CPF
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Verifica se dois clientes são iguais comparando seus CPFs.
     * Dois clientes com o mesmo CPF representam a mesma pessoa.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf);
    }

    /**
     * Calcula hash baseado no CPF do cliente.
     * Necessário quando usar Cliente em HashSet ou HashMap.
     */
    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    /**
     * Representação em texto do cliente com informações principais.
     */
    @Override
    public String toString() {
        return "Cliente [nome=" + nome + ", cpf=" + cpf + ", email=" + email + "]";
    }
}
