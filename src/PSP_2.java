import java.net.StandardSocketOptions;

public class PSP_2 {

    public static void main(String[] args){

        ProcessBuilder pb=new ProcessBuilder("cd","..");

        for(int i=0;i<pb.environment().size();i++){
            System.out.println(pb.environment().toString());
        }


    }
}
