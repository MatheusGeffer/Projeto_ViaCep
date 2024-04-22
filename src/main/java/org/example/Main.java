package org.example;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.sql.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o peso da encomenda em kg: ");
        double peso;
        try {
            peso = Double.parseDouble(scanner.next());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira um valor numérico válido para o peso.");
            return;
        }

        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.next();

        System.out.print("Digite o CEP de destino: ");
        String cepDestino = scanner.next();

        InformacaoFrete informacaoFrete = new InformacaoFrete();
        informacaoFrete.setPeso(peso);
        informacaoFrete.setCpf(cpf);
        informacaoFrete.setCep(cepDestino);

        String estadoDestino = consultarEstadoPorCEP(informacaoFrete.getCep());

        if (!estadoDestino.isEmpty()) {
            double valorFrete = calcularFrete(informacaoFrete.getPeso(), estadoDestino);
            System.out.println("O valor do frete é R$ " + valorFrete);

            inserirNoBanco(peso, cpf, cepDestino, valorFrete);
            mostrarDadosDoBanco();

        } else {
            System.out.println("Não foi possível calcular o frete.");
        }

        scanner.close();
    }

    public static String consultarEstadoPorCEP(String cep) {
        String estado = "";
        try {
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            estado = json.getString("uf");
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return estado;
    }

    public static double calcularFrete(double peso, String estado) {
        if (peso <= 1.0) {
            return estado.equals("PR") ? 10.00 : 12.50;
        } else if (peso >= 1.1 && peso <= 5.0) {
            return estado.equals("PR") ? 15.00 : 19.90;
        } else if (peso >= 5.1 && peso <= 10.0) {
            return estado.equals("PR") ? 22.50 : 29.90;
        } else {
            return estado.equals("PR") ? 37.50 : 49.90;
        }
    }

    //Insere dados no banco
    public static void inserirNoBanco(double peso, String cpf, String cep, double valorFrete) {
        try {
            Connection conexao = DriverManager.getConnection("jdbc:sqlite:bd_frete.db");
            String inserir = "INSERT INTO clientes (peso, cpf, cepDestino, valorFrete) VALUES (?, ?, ?, ?)";
            PreparedStatement comando = conexao.prepareStatement(inserir);
            comando.setDouble(1, peso);
            comando.setString(2, cpf);
            comando.setString(3, cep);
            comando.setDouble(4, valorFrete);
            comando.executeUpdate();
            comando.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Mostra os dados inseridos no banco.
    //Esta parte pode ser excluida depois da criação do arquivo para salvar os dados.
    public static void mostrarDadosDoBanco() {
        try {
            Connection conexao = DriverManager.getConnection("jdbc:sqlite:bd_frete.db");
            Statement comando = conexao.createStatement();
            ResultSet resultado = comando.executeQuery("SELECT * FROM clientes");

            while (resultado.next()) {
                System.out.println("=============================");
                System.out.println("ID: " + resultado.getInt("id"));
                System.out.println("Peso: " + resultado.getDouble("peso"));
                System.out.println("CPF: " + resultado.getString("cpf"));
                System.out.println("CEP de Destino: " + resultado.getString("cepDestino"));
                System.out.println("Valor do Frete: R$ " + resultado.getDouble("valorFrete"));
                System.out.println("=============================");
            }

            resultado.close();
            comando.close();
            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

