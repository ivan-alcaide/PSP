
/*
Ejercicio 3
Crea un programa que ejecute el comando grep para seleccionar las líneas de un fichero que contengan
un texto, y que las escriba en otro fichero. El texto, el fichero en el que se buscan las líneas que
contienen el texto y el fichero en el que se escriben las líneas que contienen el texto se pasan como
argumentos de línea de comandos. Puedes desarrollar el programa a partir
del primer programa de ejemplo. Solo hay que utilizar adecuadamente los métodos redirectInput y redirectError.

 */

import java.io.File;
import java.io.IOException;

public class PSP_3 {

    public static void main(String[] args) throws InterruptedException{

        String texto="ivan";
        File salida=new File("salida.txt");
        File entrada=new File("origen.txt");
        ProcessBuilder pb=new ProcessBuilder("grep",texto, "origen.txt");
        pb.redirectInput(entrada);
        pb.redirectOutput(salida);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {
            Process p = pb.start();
            p.waitFor();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }catch (InterruptedException e2){
            System.out.println(e2.getMessage());
        }






    }

}
