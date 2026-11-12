import java.io.File;

public class PSP_1 {
    public static void main(String[] args){
        System.out.println("Hola");

        ProcessBuilder pb=new ProcessBuilder("ls");

        System.out.println(pb.directory().getName());




    }
}
