package br.com.sotec.funcionario.core;

import java.util.Map;


public interface CarregadorMetaDadosBanco {
    
    public Map<String, String> getColunasTabela(String tabela);
    
    public boolean ehCampoNulo(String tabela, String coluna);
    
    public int getTamanhoColuna(String tabela, String coluna);
        
    public boolean temReferenciaDeIntegridade(String tabela, String coluna, Object valor);
    
    public boolean ehChaveEstrangeira(String tabela, String coluna);
}
