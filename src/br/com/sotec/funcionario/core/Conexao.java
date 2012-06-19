package br.com.sotec.funcionario.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static Connection conexao;
    private static final String URL = "jdbc:mysql://localhost/db_atlas";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";

    public static Connection getInstance() {
        try {
            if (conexao == null) {
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return conexao;
    }

    public static void fechaConexao() {
        try {
            conexao.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            conexao = null;
        }
    }
}
