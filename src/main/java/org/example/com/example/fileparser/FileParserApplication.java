package org.example.com.example.fileparser;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileParserApplication {

    public static void main(String[] args) {

        System.out.println("File Parser Application Started!");

        Path inputFile = Path.of("src/main/resources/input.txt");
        Path outputFile = Path.of("src/main/resources/output.json");

        try {

            // 1. Read input file
            String content = Files.readString(inputFile);

            // 2. Split content into lines
            String[] lines = content.split("\\R");

            // 3. Store parsed data
            Map<String, String> data = new LinkedHashMap<>();

            for (String line : lines) {

                // Ignore empty lines
                if (line.isBlank()) {
                    continue;
                }

                // Check whether line contains =
                if (!line.contains("=")) {
                    System.out.println("Invalid line skipped: " + line);
                    continue;
                }

                // 4. Split key and value
                String[] parts = line.split("=", 2);

                String key = parts[0].trim();
                String value = parts[1].trim();

                // Ignore empty keys
                if (key.isEmpty()) {
                    System.out.println("Empty key skipped: " + line);
                    continue;
                }

                // 5. Store in Map
                data.put(key, value);
            }

            // 6. Convert Map to JSON
            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(data);

            // 7. Write JSON to output.json
            Files.writeString(outputFile, json);

            // 8. Display JSON in console
            System.out.println();
            System.out.println("JSON Output:");
            System.out.println(json);

            System.out.println();
            System.out.println("JSON file created successfully!");
            System.out.println("Output: " + outputFile);

        } catch (IOException e) {

            System.out.println("File processing failed!");
            System.out.println("Reason: " + e.getMessage());
        }
    }
}