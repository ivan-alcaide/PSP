package boletin2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Ej_1 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String fichero;

        while (true) {
            System.out.print("cuentapalfich> ");
            fichero = sc.nextLine();

            if (fichero.isEmpty()) {
                break;
            }

            try {
                // Crear el proceso wc
                ProcessBuilder pb = new ProcessBuilder("wc");

                // Redirigir la entrada desde el fichero
                pb.redirectInput(new File(fichero));

                // Redirigir salida y error al proceso padre
                pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                pb.redirectError(ProcessBuilder.Redirect.INHERIT);

                // Ejecutar el proceso
                Process proceso = pb.start();

                // Esperar a que termine
                proceso.waitFor();

            } catch (IOException e) {
                System.out.println("Error al ejecutar wc o abrir el fichero.");
            } catch (InterruptedException e) {
                System.out.println("El proceso ha sido interrumpido.");
                Thread.currentThread().interrupt();
                break;
            }
        }

        sc.close();
    }
}
