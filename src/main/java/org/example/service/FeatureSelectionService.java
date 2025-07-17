package org.example.service;

import org.example.model.AthleteData;
import org.springframework.stereotype.Service;
import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.data.vector.DoubleVector;
import smile.regression.LASSO;
import smile.regression.OLS;
import smile.validation.Bag;
import smile.validation.CrossValidation;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class FeatureSelectionService {

    public List<String> filtro(List<AthleteData> dados, double limiar) {
        List<String> selecionados = new ArrayList<>();

        try {
            var campos = AthleteData.class.getDeclaredFields();

            for (var campo : campos) {
                if (campo.getType() == double.class) {
                    campo.setAccessible(true);
                    double[] valores = new double[dados.size()];
                    for (int i = 0; i < dados.size(); i++) {
                        valores[i] = (double) campo.get(dados.get(i));
                    }
                    double media = Arrays.stream(valores).average().orElse(0);
                    double variancia = Arrays.stream(valores)
                            .map(v -> Math.pow(v - media, 2))
                            .average()
                            .orElse(0);
                    double desvio = Math.sqrt(variancia);
                    if (desvio > limiar) {
                        selecionados.add(campo.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return selecionados;
    }

    public List<String> wrapperRFE(List<AthleteData> dados, int nFeatures, String targetNome) {
        return null;
    }


    public List<String> embeddedLasso(List<AthleteData> dados, String targetNome, double alpha) {
        try {
            // Lista para armazenar os campos (atributos) numéricos do objeto
            List<Field> atributos = new ArrayList<>();
            Field targetField = null;

            // Percorre todos os campos da classe AthleteData para identificar os atributos e o alvo
            for (Field f : AthleteData.class.getDeclaredFields()) {
                f.setAccessible(true); // Permite acessar campos privados
                if (f.getType() == double.class) { // Considera apenas campos numéricos
                    if (f.getName().equalsIgnoreCase(targetNome))
                        targetField = f; // Identifica o campo alvo
                    else
                        atributos.add(f); // Adiciona à lista de atributos preditores
                }
            }

            // Se o campo alvo não foi encontrado, lança exceção
            if (targetField == null)
                throw new IllegalArgumentException("Atributo alvo nao encontrado: " + targetNome);

            // Criação das matrizes de entrada X e saída y
            double[][] x = new double[dados.size()][atributos.size()];
            double[] y = new double[dados.size()];

            // Preenche as matrizes com os dados extraídos dos objetos AthleteData
            for (int i = 0; i < dados.size(); i++) {
                for (int j = 0; j < atributos.size(); j++) {
                    x[i][j] = (double) atributos.get(j).get(dados.get(i)); // Preenche X
                }
                y[i] = (double) targetField.get(dados.get(i)); // Preenche y
            }

            // Obtém os nomes dos atributos preditores
            String[] nomes = atributos.stream().map(Field::getName).toArray(String[]::new);

            // Cria um DataFrame com os dados e junta com o vetor y (target)
            DataFrame df = DataFrame.of(x, nomes).merge(DoubleVector.of(targetNome, y));
            Formula formula = Formula.lhs(targetNome); // Define a fórmula para regressão

            // Treina o modelo Lasso com a fórmula, dados e parâmetro de regularização alpha
            var modelo = LASSO.fit(formula, df, alpha);
            double[] coefs = modelo.coefficients(); // Obtém os coeficientes do modelo

            // Seleciona os atributos cujo coeficiente não é zero
            List<String> selecionadas = new ArrayList<>();
            for (int i = 0; i < coefs.length; i++) {
                if (coefs[i] != 0.0) {
                    selecionadas.add(nomes[i]);
                }
            }

            // Retorna a lista de atributos selecionados
            return selecionadas;

        } catch (Exception e) {
            e.printStackTrace(); // Loga o erro
            return List.of(); // Retorna lista vazia em caso de exceção
        }
    }

}
