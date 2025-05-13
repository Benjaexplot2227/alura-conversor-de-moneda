import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);

        //Repetir el menú hasta que el usuario decida salir
        while (true) {
            System.out.println("""
                     *****************************************
                              Conversor de Monedas
                    
                      1) Dólar >>> Peso chileno
                      2) Peso chileno >>> Dólar        
                      3) Dólar >>> Peso argentino
                      4) Peso argentino >>> Dólar
                      5) Dólar >>> Real brasileño
                      6) Real brasileño >>> Dólar 
                      7) Salir                 
                    
                      Ingrese el número de la opción deseada:
                    """);
            String opcion = lectura.nextLine();

            if (opcion.equals("7")) {
                System.out.println("Hasta luego!");
                break; // Salir
            }

            // Llamada a la API
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://v6.exchangerate-api.com/v6/b4aea2914d71bdc48f602bc2/latest/USD"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parseo de JSON
            JsonElement elemento = JsonParser.parseString(response.body());
            JsonObject json = elemento.getAsJsonObject();
            JsonObject rates = json.getAsJsonObject("conversion_rates");

            switch (opcion) {
                case "1" -> {
                    System.out.print("Ingrese la cantidad en USD: ");
                    double usd = lectura.nextDouble();
                    double clpRate = rates.get("CLP").getAsDouble();
                    System.out.println("Equivale a " + (usd * clpRate) + " Pesos chilenos.");
                }
                case "2" -> {
                    System.out.print("Ingrese la cantidad en CLP: ");
                    double clp = lectura.nextDouble();
                    double clpRate = rates.get("CLP").getAsDouble();
                    System.out.println("Equivale a " + (clp / clpRate) + " Dólares.");
                }
                case "3" -> {
                    System.out.print("Ingrese la cantidad en USD: ");
                    double usd = lectura.nextDouble();
                    double arsRate = rates.get("ARS").getAsDouble();
                    System.out.println("Equivale a " + (usd * arsRate) + " Pesos argentinos.");
                }
                case "4" -> {
                    System.out.print("Ingrese la cantidad en ARS: ");
                    double ars = lectura.nextDouble();
                    double arsRate = rates.get("ARS").getAsDouble();
                    System.out.println("Equivale a " + (ars / arsRate) + " Dólares.");
                }
                case "5" -> {
                    System.out.print("Ingrese la cantidad en USD: ");
                    double usd = lectura.nextDouble();
                    double brlRate = rates.get("BRL").getAsDouble();
                    System.out.println("Equivale a " + (usd * brlRate) + " Reales brasileños.");
                }
                case "6" -> {
                    System.out.print("Ingrese la cantidad en BRL: ");
                    double brl = lectura.nextDouble();
                    double brlRate = rates.get("BRL").getAsDouble();
                    System.out.println("Equivale a " + (brl / brlRate) + " Dólares.");
                }
                default -> System.out.println("Opción no válida.");
            }

            //Limpiar el buffer
            lectura.nextLine();
        }

        lectura.close();
    }
}
