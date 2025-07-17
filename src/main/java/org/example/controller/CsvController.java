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

    @Autowired
    private CsvService csvService;

    @Autowired
    private CorrelationService correlationService;

    @Autowired
    private FeatureSelectionService featureSelectionService;

    @GetMapping("/dados")
    public List<AthleteData> dados(@RequestParam String caminho) {
        return csvService.getDados(caminho);
    }

    @GetMapping("/correlacao")
    public double[][] correlacao(@RequestParam String caminho) {
        var dados = csvService.getDados(caminho);
        return correlationService.calcularCorrelacao(dados);
    }

    @GetMapping("/filtro")
    public List<String> filtro(
            @RequestParam String caminho,
            @RequestParam(defaultValue = "0.5") double limiar) {
        var dados = csvService.getDados(caminho);
        return featureSelectionService.filtro(dados, limiar);
    }

    @GetMapping("/wrapper")
    public List<String> wrapper(
            @RequestParam String caminho,
            @RequestParam(defaultValue = "3") int quantidade,
            @RequestParam(defaultValue = "target") String target) {
        var dados = csvService.getDados(caminho);
        //return featureSelectionService.wrapperRFE(dados, target, quantidade);
        return null;
    }

    @GetMapping("/embedded")
    public List<String> embedded(
            @RequestParam String caminho,
            @RequestParam(defaultValue = "target") String target,
            @RequestParam(defaultValue = "0.1") double alpha) {
        var dados = csvService.getDados(caminho);
        return featureSelectionService.embeddedLasso(dados, target, alpha);
    }
}
