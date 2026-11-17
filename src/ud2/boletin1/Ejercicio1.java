package ud2.boletin1;

import java.util.Random;

class Colecta {

    private static final int CANTIDAD_MAX = 2000;
    private int cantidad = 0;
    private boolean finalizada = false;

    synchronized public boolean accesoColecta(int cantidadHilo, String nombre) {

        if (finalizada) {
            return false;
        }

        if (cantidad + cantidadHilo <= CANTIDAD_MAX) {

            cantidad += cantidadHilo;

            System.out.printf(
                    "\nEl hilo %s, con cantidad %d se ha sumado, cantidad actual %d\n",
                    nombre, cantidadHilo, cantidad
            );

            if (cantidad == CANTIDAD_MAX) {
                finalizada = true;
                System.out.println("\n¡SE HA ALCANZADO LA CANTIDAD DE 2000!");
            }

            return true;

        } else {
            return false;
        }
    }

    synchronized public int getCantidad() {
        return cantidad;
    }

    synchronized public boolean isFinalizada() {
        return finalizada;
    }
}

class Hilo implements Runnable {

    private final String nombre;
    private final Colecta colecta;
    private final Random random;

    public Hilo(String nombre, Colecta colecta) {
        this.nombre = nombre;
        this.colecta = colecta;
        this.random = new Random();
    }

    @Override
    public void run() {

        while (!colecta.isFinalizada()) {

            int cantidad = random.nextInt(22) + 4;
            int tiempo = random.nextInt(191) + 10;

            try {
                System.out.printf(
                        "\nEl hilo %s, tiene cantidad %d y va a acceder en %d ms\n",
                        this.nombre, cantidad, tiempo
                );

                Thread.sleep(tiempo);

                if (!colecta.accesoColecta(cantidad, nombre)
                        && !colecta.isFinalizada()) {

                    System.out.printf(
                            "\nEl hilo %s, con cantidad %d no se ha podido sumar porque la colecta tiene %d\n",
                            this.nombre, cantidad, colecta.getCantidad()
                    );
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.printf("\nEl hilo %s ha terminado.\n", nombre);
    }
}

public class Ejercicio1 {

    public static void main(String[] args) {

        Colecta colecta = new Colecta();

        Thread[] hs = new Thread[5];

        for (int i = 0; i < hs.length; i++) {
            hs[i] = new Thread(new Hilo("h" + i, colecta));
        }

        for (Thread h : hs) {
            h.start();
        }
    }
}