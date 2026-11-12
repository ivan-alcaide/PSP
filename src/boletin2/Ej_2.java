package boletin2;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Ej_2 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String fichero;

        while (true) {
            System.out.print("cuentapalfich> ");
            fichero = sc.nextLine();

            if (fichero.isEmpty()) {
                break;
            }

            String comando = "$t = Get-Content -Raw '" + fichero + "'; " + "$lineas = (Get-Content '" + fichero
                    + "').Count; " + "$palabras = ($t -split '\\s+' | Where-Object {$_}).Count; "
                    + "$caracteres = $t.Length; " + "Write-Output \"$lineas $palabras $caracteres\"";

            try {
                // Crear el proceso wc
                ProcessBuilder pb = new ProcessBuilder("cmd.exe","-NoProfile","-Command",comando);


                // Ejecutar el proceso
                Process proceso = pb.start();

                // Esperar a que termine
                proceso.waitFor();



            } catch (IOException e) {
                System.out.println("Error al ejecutar wc o abrir el fichero.");
                break;
            } catch (InterruptedException e) {
                System.out.println("El proceso ha sido interrumpido.");
                Thread.currentThread().interrupt();
                break;
            }
        }

        sc.close();
    }
}
