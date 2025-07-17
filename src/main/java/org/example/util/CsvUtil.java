package org.example.util;

import com.opencsv.bean.CsvToBeanBuilder;
import org.example.model.AthleteData;

import java.io.FileReader;
import java.util.List;

public class CsvUtil {
    public static List<AthleteData> lerCsv(String caminhoCsv) {
        try {
            return new CsvToBeanBuilder<AthleteData>(new FileReader(caminhoCsv))
                    .withType(AthleteData.class)
                    .withSeparator(';') // importante para CSV com ;
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
