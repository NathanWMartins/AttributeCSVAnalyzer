package org.example.controller;

import org.example.model.AthleteData;
import org.example.service.CorrelationService;
import org.example.service.CsvService;
import org.example.service.FeatureSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CsvController {

    // Injeta automaticamente o serviço responsável por ler e processar arquivos CSV
    @Autowired
    private CsvService csvService;

    // Injeta automaticamente o serviço responsável por cálculos de correlação
    @Autowired
    private CorrelationService correlationService;

    // Injeta automaticamente o serviço responsável por seleção de features
    @Autowired
    private FeatureSelectionService featureSelectionService;

    @GetMapping("/dados")
    public List<AthleteData> dados(@RequestParam String caminho) {
        // Chama o serviço de CSV para ler e retornar os dados
        return csvService.getDados(caminho);
    }

    @GetMapping("/correlacao")
    public double[][] correlacao(@RequestParam String caminho) {
        // Obtém os dados do CSV usando o serviço
        var dados = csvService.getDados(caminho);
        // Calcula e retorna a matriz de correlação usando o serviço específico
        return correlationService.calcularCorrelacao(dados);
    }

    @GetMapping("/filtro")
    public List<String> filtro(
            @RequestParam String caminho,
            @RequestParam(defaultValue = "10") double limiar) {
        // Obtém os dados do CSV
        var dados = csvService.getDados(caminho);
        // Aplica o metodo de filtro para selecionar features com base no limiar
        return featureSelectionService.filtro(dados, limiar);
    }
}