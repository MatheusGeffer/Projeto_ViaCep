package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SalvarArquivo {
    public static void salvarInformacoes(double peso, String cpf, String cep, double valorFrete) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        String fileName = "informacoes_frete.txt";

        // Monta a string com as informações do frete
        String dadosFrete = String.format("%.1fkg %s %s R$ %.2f %s\n", peso, cpf, cep, valorFrete, formattedDateTime);

        // Grava as informações no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(dadosFrete);
            System.out.println("As informações do frete foram gravadas no arquivo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
