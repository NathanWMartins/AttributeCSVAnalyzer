package org.example.service;

import org.example.model.AthleteData;
import org.example.util.CsvUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CsvService {
    public List<AthleteData> getDados(String caminhoCsv) {
        return CsvUtil.lerCsv(caminhoCsv);
    }
}
