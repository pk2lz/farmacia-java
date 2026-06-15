package farmacia.promocoes;

import farmacia.modelos.Venda;
import java.io.Serializable;

/**
 * Interface Promocao - define o contrato para qualquer tipo de promoção no sistema.
 * Todas as promoções devem implementar o método calcularDesconto para definir
 * como o desconto será aplicado a uma venda específica.
 * Implementações: PromocaoPercentual, PromocaoLeveXPagueY.
 */
public interface Promocao extends Serializable {
    
    /**
     * Calcula o valor do desconto que esta promoção oferece para a venda fornecida.
     * Cada implementação define sua lógica específica de desconto.
     * @param venda a venda que será analisada para aplicação de desconto
     * @return o valor total a ser descontado (em reais)
     */
    double calcularDesconto(Venda venda);
}
