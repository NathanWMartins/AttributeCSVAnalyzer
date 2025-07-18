package org.example.service;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.example.model.AthleteData;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

// Classe Service Spring, permite injeção em outros componentes
@Service
public class CorrelationService {

    // Metodo para calcular a matriz de correlação entre os atributos numéricos dos dados dos atletas
    public double[][] calcularCorrelacao(List<AthleteData> dados) {
        try {
            // Obtém todos os campos (atributos) da classe AthleteData usando reflection
            Field[] campos = AthleteData.class.getDeclaredFields();

            // Lista para armazenar os valores de cada coluna/atributo numérico
            List<double[]> valores = new java.util.ArrayList<>();

            // Itera sobre todos os campos da classe AthleteData
            for (Field campo : campos) {
                // Verifica se o campo é do tipo double (atributo numérico)
                if (campo.getType() == double.class) {
                    // Permite acesso a campos privados via reflection
                    campo.setAccessible(true);

                    // Array para armazenar os valores deste campo para todos os registros
                    double[] coluna = new double[dados.size()];

                    // Preenche o array com os valores do campo para cada atleta
                    for (int i = 0; i < dados.size(); i++) {
                        coluna[i] = (double) campo.get(dados.get(i));
                    }

                    // Adiciona a coluna completa à lista de valores
                    valores.add(coluna);
                }
            }

            // Converte a lista de arrays em uma matriz bidimensional
            double[][] matriz = new double[valores.size()][dados.size()];
            for (int i = 0; i < valores.size(); i++) {
                matriz[i] = valores.get(i);
            }

            // Calcula e retorna a matriz de correlação de Pearson
            return new PearsonsCorrelation().computeCorrelationMatrix(matriz).getData();

        } catch (Exception e) {
            // Em caso de erro, imprime o stack trace e retorna null
            e.printStackTrace();
            return null;
        }
    }
    public List<String> getNomesDosAtributos() {
        return Arrays.stream(AthleteData.class.getDeclaredFields())
                .filter(f -> f.getType() == double.class)
                .map(Field::getName)
                .toList();
    }
}