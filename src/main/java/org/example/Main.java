package org.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        try {
            // Tenta acessar o CSV dentro de resources/data
            InputStream is = Main.class.getClassLoader().getResourceAsStream("data/TabelaoPartidas.csv");

            if (is == null) {
                System.out.println("❌ CSV não encontrado!");
                return;
            }

            System.out.println("✅ CSV encontrado! Iniciando leitura...\n");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String linha;
            int contador = 0;

            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
                contador++;
                if (contador >= 5) break; // só exibe as 5 primeiras linhas
            }

            System.out.println("\nLeitura básica concluída!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
