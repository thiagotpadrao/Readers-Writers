package estrutura.dados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private final List<String> wordsList = new ArrayList<>();

    public Data(String fileName) {
        lerArquivo(fileName);
    }

    public void lerArquivo(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("[\\p{Punct}]", " ");
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        wordsList.add(word);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para registrar o conteúdo atual do array no logfile
    public void logData(String logFileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName))) {
            for (String word : wordsList) {
                writer.write(word);
                writer.newLine();
            }
            System.out.println("Logfile criado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao criar o logfile: " + e.getMessage());
        }
    }

    public void logTime(int id, long avgTime) {
        int numReaders = id;
        int numWriters = 100 - id;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("timeLog.txt", true))) {
            writer.write("(Readers: " + numReaders + " | " + "Writers: " + numWriters + ") >> Time: " + avgTime + "ms");
            writer.newLine(); // Adiciona uma nova linha após cada registro
        } catch (IOException e) {
            System.out.println("Erro ao criar o logfile: " + e.getMessage());
        }
    }    

    public String readData(int index) {
        return wordsList.get(index);
    }

    public void writeData(int index, String newData) {
        wordsList.set(index, newData);
    }

    public int getSize() {
        return wordsList.size();
    }
}
