package org.example.service;

import org.knowm.xchart.HeatMapChart;
import org.knowm.xchart.HeatMapChartBuilder;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.style.HeatMapStyler;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.IntStream;

@Service
public class GraphService {

    public void generateCorrelationHeatmap(double[][] correlationMatrix,
                                           String[] featureNames,
                                           OutputStream out) throws IOException {

        // 1. Criar o gráfico
        HeatMapChart chart = new HeatMapChartBuilder()
                .width(1000)
                .height(800)
                .title("Matriz de Correlação")
                .build();

        // 2. Converter os dados para o formato esperado (int[])
        int size = featureNames.length;
        int[] xData = IntStream.range(0, size).toArray();
        int[] yData = IntStream.range(0, size).toArray();
        int[][] heatData = new int[size][size];

        // Converter double para int (multiplicando por 100 para manter precisão)
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                heatData[i][j] = (int) (correlationMatrix[i][j] * 100);
            }
        }

        // 3. Adicionar série - formato correto para v3.6.5
        chart.addSeries("correlacao", xData, yData, heatData);

        // 4. Configurar o estilo (métodos disponíveis na v3.6.5)
        HeatMapStyler styler = chart.getStyler();

        // Configurar cores (alternativa para setColorMap)
        styler.setChartBackgroundColor(Color.WHITE);
        styler.setChartFontColor(Color.BLACK);
        styler.setAxisTickLabelsColor(Color.BLACK);

        StringBuilder title = new StringBuilder("Matriz de Correlação\n");
        for (int i = 0; i < featureNames.length; i++) {
            title.append(i).append("=").append(featureNames[i]).append("  ");
        }
        chart.setTitle(title.toString());

        // 5. Exportar
        BitmapEncoder.saveBitmap(chart, out, BitmapEncoder.BitmapFormat.PNG);
    }
}