package com.exemple.batch.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import com.exemple.batch.dto.Acteur;

public class CSVUtils {
	
    private static final char DEFAULT_SEPARATOR = ',';
    
    public static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    } 
    
    private static String followCVSformat(String value) {
        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;
    }

    private static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {
        boolean first = true;

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());
    }
    
    public static void traceNoValidActor(Acteur acteur) throws Exception {
        String csvFile ="src/main/resources/actors-not-valid.csv";
        FileWriter writer = new FileWriter(csvFile, true);
        
        List<String> values = Arrays.asList(
		        								acteur.getId(), 
		        								String.valueOf(acteur.getAge()), 
		        								acteur.getNomComplet(), 
		        								acteur.getFilm()
        									);       
        writeLine(writer, values);

        writer.flush();
        writer.close();
    }
}


