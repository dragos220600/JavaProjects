import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        try (
                InputStream is = System.in;
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr)
                ) {
            System.out.println("Introduceti locala dorita");
            String inputLocale = br.readLine();

            Path path = Paths.get("input_" + inputLocale + ".txt");
            Path pathDest = Paths.get("trad_" + inputLocale + ".txt");
            Files.createFile(pathDest);
            if (Files.exists(path)) {
                Locale locale = new Locale(inputLocale);
                ResourceBundle rb = ResourceBundle.getBundle("hello", locale);
                BufferedReader br1 = Files.newBufferedReader(path);

                String linie1 = br1.readLine();
                String [] cuvinte = linie1.split("\\s+");
                List<String> finale = new ArrayList<>(Arrays.asList(cuvinte));

                String result = finale.stream()
                        .map(rb::getString)
                        .collect(Collectors.joining(" "));

                Files.writeString(pathDest, result);
            } else {
                System.out.println("Locala introdusa nu exista! Se va afisa traducerea default..");
                Locale locale = new Locale("en_US");
                ResourceBundle rb = ResourceBundle.getBundle("hello", locale);
                BufferedReader br1 = Files.newBufferedReader(Paths.get("input_en_US.txt"));

                String linie1 = br1.readLine();
                String [] cuvinte = linie1.split("\\s+");
                List<String> finale = new ArrayList<>(Arrays.asList(cuvinte));

                String result = finale.stream()
                        .map(rb::getString)
                        .collect(Collectors.joining(" "));

                Files.writeString(pathDest, result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
