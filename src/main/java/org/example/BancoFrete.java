package org.example;

import java.sql.*;
public class BancoFrete {

    public static void main(String[] args) {
        // URL de conexão do banco de dados SQLite
        String url = "jdbc:sqlite:bd_frete.db";

        try {
            // Conectar ao banco de dados
            Connection connection = DriverManager.getConnection(url);

            // Criar uma declaração para executar comandos SQL
            Statement statement = connection.createStatement();

            // Criar uma tabela (exemplo)
            String sql = "CREATE TABLE IF NOT EXISTS ---- (\n"
                    + "    id INTEGER PRIMARY KEY,\n"
                    + "    ,\n"
                    + "    \n"
                    + ");";
            statement.execute(sql);

            // Exemplo de inserção de dados
            sql = "INSERT INTO ------- () VALUES ()";
            statement.execute(sql);

            // Fechar a conexão e a declaração
            statement.close();
            connection.close();

            System.out.println("Banco de dados criado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao criar ou acessar o banco de dados: " + e.getMessage());
        }
    }
}