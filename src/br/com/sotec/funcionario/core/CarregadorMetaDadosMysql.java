package br.com.sotec.funcionario.core;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarregadorMetaDadosMysql implements CarregadorMetaDadosBanco {

    private Connection conexao;

    @Override
    public Map<String, String> getColunasTabela(String tabela) {
        Map<String, String> colunas = new HashMap<String, String>();
        PreparedStatement ps;
        ResultSet rs;
        String strQuey = "SELECT COLUMN_NAME, DATA_TYPE "
                + "  FROM INFORMATION_SCHEMA.COLUMNS"
                + " WHERE TABLE_NAME = ?";
        try {
            conexao = Conexao.getInstance();
            ps = conexao.prepareStatement(strQuey);
            ps.setString(1, tabela);
            rs = ps.executeQuery();
            while (rs.next()) {
                colunas.put(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarregadorMetaDadosMysql.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps = null;
            rs = null;
            conexao = null;
        }
        return colunas;
    }

    @Override
    public boolean ehCampoNulo(String tabela, String coluna) {
        PreparedStatement ps;
        String isNullable = null;
        ResultSet rs;
        String strQuey = "SELECT IS_NULLABLE "
                + "  FROM INFORMATION_SCHEMA.COLUMNS"
                + " WHERE TABLE_NAME = ?"
                + "       AND COLUMN_NAME = ?";
        try {
            conexao = Conexao.getInstance();
            ps = conexao.prepareStatement(strQuey);
            ps.setString(1, tabela);
            ps.setString(2, coluna);
            rs = ps.executeQuery();

            while (rs.next()) {
                isNullable = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarregadorMetaDadosMysql.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps = null;
            rs = null;
            conexao = null;
        }
        return isNullable.equals("YES") ? true : false;
    }

    @Override
    public boolean temReferenciaDeIntegridade(String tabela, String coluna, Object valor) {
        String tabelaFk = null;
        String colunaFk = null;
        String constraintType = "FOREIGN KEY";
        PreparedStatement ps;
        ResultSet rs;
        String strQuery = "SELECT KCU.REFERENCED_TABLE_NAME, "
                + "       KCU.REFERENCED_COLUMN_NAME "
                + "  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS TC, "
                + "       INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU  "
                + " WHERE TC.CONSTRAINT_NAME = KCU.CONSTRAINT_NAME "
                + "       AND TC.TABLE_NAME = ? "
                + "       AND TC.CONSTRAINT_TYPE = ? "
                + "       AND KCU.COLUMN_NAME = ? ";

        try {
            conexao = Conexao.getInstance();
            ps = conexao.prepareStatement(strQuery);
            ps.setString(1, tabela);
            ps.setString(2, constraintType);
            ps.setString(3, coluna);
            rs = ps.executeQuery();

            while (rs.next()) {
                tabelaFk = rs.getString(1);
                colunaFk = rs.getString(2);
            }
            strQuery = "SELECT 1 FROM " + tabelaFk + " WHERE " + colunaFk + " = ?";
            ps = conexao.prepareStatement(strQuery);
            ps.setObject(1, valor);
            rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarregadorMetaDadosMysql.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps = null;
            rs = null;
            conexao = null;
        }
        return false;
    }

    @Override
    public int tamanhoColuna(String tabela, String coluna) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        CarregadorMetaDadosBanco c = new CarregadorMetaDadosMysql();
        System.out.println(c.getColunasTabela("tb_pessoa"));
        System.out.println(c.ehCampoNulo("tb_pessoa", "NOME_MAE"));
        System.out.println(c.temReferenciaDeIntegridade("tb_usuario", "id_disciplina", 14));
    }

    @Override
    public boolean ehChaveEstrangeira(String tabela, String coluna) {
        String constraintType = "FOREIGN KEY";
        PreparedStatement ps;
        ResultSet rs;
        String strQuery = "SELECT COUNT(*) "
                + "  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS TC, "
                + "       INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU  "
                + " WHERE TC.CONSTRAINT_NAME = KCU.CONSTRAINT_NAME "
                + "       AND TC.TABLE_NAME = ? "
                + "       AND TC.CONSTRAINT_TYPE = ? "
                + "       AND KCU.COLUMN_NAME = ? ";

        try {
            conexao = Conexao.getInstance();
            ps = conexao.prepareStatement(strQuery);
            ps.setString(1, tabela);
            ps.setString(2, constraintType);
            ps.setString(3, coluna);
            rs = ps.executeQuery();

            while (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarregadorMetaDadosMysql.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps = null;
            rs = null;
            conexao = null;
        }
        return false;
    }
}
