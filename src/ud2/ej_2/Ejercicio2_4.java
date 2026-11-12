package ud2.ej_2;


import java.util.Random;

class NumeroOculto {

    private int numero;
    private boolean terminado = false;
    private Integer partida;

    public NumeroOculto(int numero) {
        this.numero = numero;
        this.partida=0;
    }




    public synchronized int propuestaNumero(int num) {

        // Si el juego ya ha terminado, devuelve -1
        if (terminado) {
            return -1;
        }

        // Si el hilo acierta, termina el juego
        if (num == numero) {
            terminado = true;
            partida++;
            return 1;

        }

        // Si no acierta, devuelve 0
        return 0;
    }


}

class HiloAdivinador extends Thread {

    private ud2.ej_2.NumeroOculto juego;
    private Random random = new Random();

    public HiloAdivinador(ud2.ej_2.NumeroOculto juego, int id) {
        super("Hilo-" + id);
        this.juego = juego;
    }

    @Override
    public void run() {

        while (true) {

            int num = random.nextInt(101);

            int resultado = juego.propuestaNumero(num);

            if (resultado == 1) {
                System.out.println(
                        getName() + " ha acertado el número: " + num
                );
                break;

            } else if (resultado == -1) {
                System.out.println(
                        getName() + " termina porque otro hilo ha ganado."
                );
                break;

            } else {
                System.out.println(
                        getName() + " propone " + num + " y no acierta."
                );
            }
        }
    }
}

public class Ejercicio2_4 {

    public static void main(String[] args) {

        Random random = new Random();

        // El hilo principal genera el número oculto
        int numero = random.nextInt(101);

        ud2.ej_2.NumeroOculto juego = new ud2.ej_2.NumeroOculto(numero);

        // Crear y arrancar diez hilos adivinadores
        for (int i = 1; i <= 10; i++) {
            ud2.ej_2.HiloAdivinador hilo = new ud2.ej_2.HiloAdivinador(juego, i);
            hilo.start();
        }
    }
}
