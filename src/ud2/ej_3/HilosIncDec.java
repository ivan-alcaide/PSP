package ud2.ej_3;

class Contador {
    private int cuenta = 0;

    Contador(int valorInicial) {
        this.cuenta = valorInicial;
    }

    synchronized public int getCuenta() {
        return cuenta;
    }

    synchronized public int incrementa() {
        this.cuenta++;
        return cuenta;
    }

    synchronized public int decrementa() {
        this.cuenta--;
        return cuenta;
    }
}

class HiloIncr implements Runnable {
    private final String id;
    private final Contador cont;
    private final int NUM_MAX=45;
    HiloIncr(String id, Contador c) {
        this.id = id;
        this.cont = c;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this.cont) {
                while (this.cont.getCuenta()>=this.NUM_MAX){
                    System.out.println("MAximo alcanzado");
                    try {
                        this.cont.wait();
                    } catch (InterruptedException e) {

                    }
                }

                this.cont.incrementa();
                this.cont.notify();
                System.out.printf("Hilo %s incrementa, valor contador: %d\n", this.id, this.cont.getCuenta());
            }
        }
    }
}

class HiloDecr implements Runnable {
    private final String id;
    private final Contador cont;

    HiloDecr(String id, Contador c) {
        this.id = id;
        this.cont = c;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this.cont) {
                while (this.cont.getCuenta() < 1) {
                    System.out.printf("!!! Hilo %s no puede decrementar, valor contador: %d\n", this.id, this.cont.getCuenta());
                    try {
                        this.cont.wait();
                    } catch (InterruptedException ex) {
                    }
                }
                this.cont.decrementa();
                this.cont.notify();
                System.out.printf("Hilo %s decrementa, valor contador: %d\n", this.id, this.cont.getCuenta());
            }
        }
    }
}

class HilosIncDec {
    private static final int NUM_HILOS_INC = 10;
    private static final int NUM_HILOS_DEC = 10;


    public static void main(String[] args) {
        Contador c = new Contador(45);
        Thread[] hilosInc = new Thread[NUM_HILOS_INC];

        for (int i = 0; i < NUM_HILOS_INC; i++) {
            Thread th = new Thread(new HiloIncr("INC" + i, c));
            hilosInc[i] = th;
        }

        Thread[] hilosDec = new Thread[NUM_HILOS_DEC];

        for (int i = 0; i < NUM_HILOS_DEC; i++) {
            Thread th = new Thread(new HiloDecr("DEC" + i, c));
            hilosDec[i] = th;
        }

        for (int i = 0; i < NUM_HILOS_INC; i++) {
            hilosInc[i].start();
        }

        for (int i = 0; i < NUM_HILOS_DEC; i++) {
            hilosDec[i].start();
        }
    }
}
