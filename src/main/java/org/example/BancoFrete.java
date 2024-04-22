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

            // Criação da tabela
            String sql = "CREATE TABLE IF NOT EXISTS clientes (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    peso DOUBLE,\n" +
                    "    cpf TEXT,\n" +
                    "    cepDestino TEXT,\n" +
                    "    valorFrete DOUBLE\n" +
                    ");";
            statement.execute(sql);

            // Exemplo de inserção de dados
            sql = "INSERT INTO clientes (peso, cpf, cepDestino, valorFrete) VALUES (10.5, '12345678900', '12345-678', 25.0)";
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
