package br.com.sotec.funcionario.gerador;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.WordUtils;


public class GeraServletControlador {
    
    public static final String NOME_PROJETO = "atlas";
    
    public List<String> geraCabecalhoServlet(String tabela){
        String nomeClasse = WordUtils.capitalize(tabela.substring(3));
        List<String> arquivoClasse = new ArrayList<String>();
        arquivoClasse.add("package br.com.sotec." + NOME_PROJETO + ".controllers;");
        arquivoClasse.add("");
        arquivoClasse.add("import br.com.sotec."  + NOME_PROJETO + ".entities."+ nomeClasse +";");
        arquivoClasse.add("import br.com.sotec."  + NOME_PROJETO + ".persistence."+ nomeClasse +"Dao;");
        arquivoClasse.add("import br.com.sotec."  + NOME_PROJETO + ".util.AtlasUtil;");
        arquivoClasse.add("import java.io.IOException;");
        arquivoClasse.add("import java.util.List;");
        arquivoClasse.add("import javax.servlet.ServletException;");
        arquivoClasse.add("import javax.servlet.http.HttpServlet;");
        arquivoClasse.add("import javax.servlet.http.HttpServletRequest;");
        arquivoClasse.add("import javax.servlet.http.HttpServletResponse;");
        arquivoClasse.add("");
        arquivoClasse.add("public class " + nomeClasse + "Manager extends HttpServlet {");
        arquivoClasse.add("");
        arquivoClasse.add("    private static final String FORM_CADASTRO = jsp/" + nomeClasse.toLowerCase() +"/formularioCadastro.jsp");
        arquivoClasse.add("    private static final String PAGINA_PESQUISA = jsp/" + nomeClasse.toLowerCase() +"/pesquisa.jsp");
        arquivoClasse.add("    private static final String MSG_ERRO_NA_PESQUISA = "
                + "\"Erro ao tentar consultar as "+ nomeClasse.toLowerCase() +", caso ocorra novamente favor informe ao suporte\";");
        arquivoClasse.add("    private static final String MSG_VALIDACAO        = \"Todos os dados do formulário necessitam ser informados.\";");
        arquivoClasse.add("");
        arquivoClasse.add("    @Override");
        arquivoClasse.add("    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { ");
        arquivoClasse.add("        if (request.getParameter(\"acao\").equals(\"pesquisar\")) {");
        arquivoClasse.add("            request.setAttribute(\"exibeLista\", \"ok\");");
        arquivoClasse.add("            AtlasUtil.adicionaListaNaSessao(request, busca"+nomeClasse+"(request), \""+ nomeClasse.toLowerCase()+"\");");
        arquivoClasse.add("            AtlasUtil.redirecionaPagina(request, response, PAGINA_PESQUISA);");
        arquivoClasse.add("        }else if(request.getParameter(\"acao\").equals(\"editar\")){");
        arquivoClasse.add("            "+nomeClasse +" c  = ("+nomeClasse+") AtlasUtil.getEntidade(request, new "+ nomeClasse+"Dao(),");
        arquivoClasse.add("                Integer.parseInt(request.getParameter(\"id"+nomeClasse+"\")));");


        return arquivoClasse;
    }
    
    public static void main(String[] args) {
        for (String string : new GeraServletControlador().geraCabecalhoServlet("tb_pessoa")) {
            System.out.println(string);
        }
       
    }
}
