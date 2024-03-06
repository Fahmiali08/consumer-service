package fahmi.ifg;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
public class CsvFileProcessor {

    public void processCsvFile(byte[] fileContent) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(fileContent), StandardCharsets.UTF_8))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // Proses baris file CSV
                processCsvRow(nextLine);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Tangani pengecualian sesuai kebutuhan
        }
    }

    private void processCsvRow(String[] csvRow) {
        // Lakukan pemrosesan untuk setiap baris file CSV
        // Misalnya, Anda dapat memasukkan data ke database, melakukan transformasi, atau melakukan tindakan lainnya.
        // Contoh:
        System.out.println("CSV Row: " + String.join(", ", csvRow));
    }
}
