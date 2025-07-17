package org.example.service;

import org.example.model.AthleteData;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FeatureSelectionService {

    // Metodo para seleção de features usando abordagem de filtro
    public List<String> filtro(List<AthleteData> dados, double limiar) {
        // Lista que armazenara os nomes dos atributos selecionados
        List<String> selecionados = new ArrayList<>();

        try {
            // Mapa para armazenar os desvios padrão de cada atributo (não utilizado no retorno final)
            Map<String, Double> desvios = new HashMap<>();

            // Obtém todos os campos declarados na classe AthleteData via reflection
            var campos = AthleteData.class.getDeclaredFields();

            // Itera sobre cada campo da classe
            for (var campo : campos) {
                // Verifica se o campo é do tipo double (atributo numérico)
                if (campo.getType() == double.class) {
                    // Permite acesso a campos privados via reflection
                    campo.setAccessible(true);

                    // Array para armazenar todos os valores deste campo
                    double[] valores = new double[dados.size()];

                    // Preenche o array com os valores do campo para cada registro
                    for (int i = 0; i < dados.size(); i++) {
                        valores[i] = (double) campo.get(dados.get(i));
                    }

                    // Calcula a média dos valores
                    double media = Arrays.stream(valores).average().orElse(0);

                    // Calcula a variância (média dos quadrados das diferenças)
                    double variancia = Arrays.stream(valores)
                            .map(v -> Math.pow(v - media, 2))
                            .average()
                            .orElse(0);

                    // Calcula o desvio padrão (raiz quadrada da variância)
                    double desvio = Math.sqrt(variancia);

                    // Se o desvio padrão for maior que o limiar, seleciona o atributo
                    if (desvio > limiar) {
                        selecionados.add(campo.getName());
                    }
                }
            }
        } catch (Exception e) {
            // Em caso de erro, imprime o stack trace (considerar logging profissional)
            e.printStackTrace();
        }

        // Retorna a lista de atributos selecionados
        return selecionados;
    }

    // Adicionar ao FeatureSelectionService
    public List<String> wrapperRFE(List<AthleteData> dados, int nFeatures) {
        // 1. Converter dados para matriz
        // 2. Usar RFE do Commons Math ou Smile
        // 3. Retornar features selecionadas
        return null;
    }

    public List<String> embeddedLasso(List<AthleteData> dados) {
        // Implementar Lasso via regression
        return null;
    }
}