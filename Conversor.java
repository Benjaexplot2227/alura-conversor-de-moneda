import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Conversor {

    public static void main(String[] args) throws IOException, InterruptedException {
        eleccionUserMenu();
    }

    public static void eleccionUserMenu() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
            *** Conversor de Monedas ***
            1) USD -> CLP
            2) CLP -> USD
            3) USD -> ARS
            4) ARS -> USD
            5) USD -> BRL
            6) BRL -> USD
            Seleccione una opción:
            """);

        String opcion = scanner.nextLine();
        String base = "USD";
        String destino = "";

        switch (opcion) {
            case "1" -> destino = "CLP";
            case "2" -> {
                base = "CLP";
                destino = "USD";
            }
            case "3" -> destino = "ARS";
            case "4" -> {
                base = "ARS";
                destino = "USD";
            }
            case "5" -> destino = "BRL";
            case "6" -> {
                base = "BRL";
                destino = "USD";
            }
            default -> {
                System.out.println("Opción inválida.");
                return;
            }
        }

        System.out.print("Ingrese la cantidad a convertir: ");
        double cantidad = scanner.nextDouble();

        double tasa = obtenerTasa(base, destino);
        double resultado = cantidad * tasa;

        System.out.printf("Resultado: %.2f %s%n", resultado, destino);
        scanner.close(); // cierra el Scanner
    }

    public static double obtenerTasa(String base, String destino) throws IOException, InterruptedException {
        String apiKey = "TU_API_KEY"; // Reemplaza esto con tu clave real
        String urlFinal = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + base + "/" + destino;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlFinal))
                .build();

        HttpResponse<String> respuesta = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonElement elemento = JsonParser.parseString(respuesta.body());
        JsonObject objectRoot = elemento.getAsJsonObject();
        return objectRoot.get("conversion_rate").getAsDouble();
    }
}







