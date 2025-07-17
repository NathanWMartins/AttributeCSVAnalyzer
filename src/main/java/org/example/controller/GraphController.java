package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

public class GraphController {
    @GetMapping("/graphs/heatmap")
    public void getHeatmap(@RequestParam String caminho, HttpServletResponse response) {
        // Gerar e retornar imagem
    }

    // Em CsvController (adicionar)
    @GetMapping("/features/wrapper")
    public List<String> wrapperFeatures(@RequestParam String caminho,
                                        @RequestParam int numFeatures) {
        // Chamar wrapperRFE
        return null;
    }

    @GetMapping("/features/embedded")
    public List<String> embeddedFeatures(@RequestParam String caminho) {
        // Chamar embeddedLasso
        return null;
    }

}
