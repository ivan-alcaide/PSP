
/*
Ejercicio 4
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PSP_4 {

    public static void main(String[] args) {

        ProcessBuilder pb1=new ProcessBuilder("grep","video","origen.txt");
        ProcessBuilder pb2=new ProcessBuilder("sed", "\"s/\\t\\t*/:/g\"");

        pb1.redirectInput(ProcessBuilder.Redirect.INHERIT);

        pb2.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        List<ProcessBuilder> pro=new ArrayList<>();
        pro.add(pb1);
        pro.add(pb2);

        try {

            List<Process> secuencias=ProcessBuilder.startPipeline(pro);

            for (Process p : secuencias) {

                p.waitFor();


            }


        }catch (IOException e){
            System.out.println(e.getMessage());
        }catch (InterruptedException e2){
            System.out.println(e2.getMessage());
        }




    }

}
