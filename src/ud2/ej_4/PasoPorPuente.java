package ud2.ej_4;

import java.util.Random;

class Puente {  // Estado, objeto compartido entre los hilos
    private static final int PESO_MAXIMO = 500;
    private static final int MAX_PERSONAS = 4;
    private static final int MAX_PERSONAS_SENTIDO = 3 ;
    private int peso = 0;
    private int numPersonas = 0;
    private int numPersonasSentido1 = 0;
    private int numPersonasSentido2 = 0;

    synchronized public int getPeso() {
        return peso;
    }

    synchronized public int getNumPersonas() {
        return numPersonas;
    }

    synchronized public int getNumPersonasSentido1() {
        return numPersonasSentido1;
    }

    synchronized public int getNumPersonasSentido2() {
        return numPersonasSentido2;
    }

    synchronized public boolean autorizacionPaso(Persona persona) {
        boolean result;
        if (this.peso + persona.getPeso() <= Puente.PESO_MAXIMO && this.numPersonas < Puente.MAX_PERSONAS &&
                ((persona.getSentido() == 1 && getNumPersonasSentido1()<Puente.MAX_PERSONAS_SENTIDO) || (persona.getSentido() == 2 && getNumPersonasSentido2()<Puente.MAX_PERSONAS_SENTIDO))
        ) {

            this.numPersonas++;

            if(persona.getSentido() == 1 ) {
                numPersonasSentido1++;
            }else {
                numPersonasSentido2++;
            }


            this.peso += persona.getPeso();
            result = true;

        } else {
            result = false;
        }
        return result;
    }

    synchronized public void terminaPaso(Persona persona) {

        if (persona.getSentido() == 1){
            this.peso -= persona.getPeso();
            this.numPersonas--;
            this.numPersonasSentido1--;
        }else {
            this.peso -= persona.getPeso();
            this.numPersonas--;
            this.numPersonasSentido2--;
        }

    }
}

class Persona implements Runnable {
    private final String idPersona;
    private final int peso;
    private final int tMinPaso, tMaxPaso;
    private final Puente puente;
    private final int sentido;

    public int getPeso() {
        return peso;
    }

    public int getSentido(){
        return sentido;
    }

    Persona(Puente puente, int peso, int tMinPaso, int tMaxPaso, String idP,int sentido) {
        this.puente = puente;
        this.peso = peso;
        this.tMinPaso = tMinPaso;
        this.tMaxPaso = tMaxPaso;
        this.idPersona = idP;
        this.sentido = sentido;
    }

    @Override
    public void run() {
        System.out.printf("> %s de %d kg quiere cruzar, en puente %d kg, %d persona%s., sentido %d\n", this.idPersona, this.peso, puente.getPeso(), puente.getNumPersonas(),puente.getNumPersonas() != 1 ? "s" : "",this.sentido);

        // Espera para conseguir autorización
        boolean autorizado = false;
        while (!autorizado) {
            synchronized (this.puente) {
                autorizado = this.puente.autorizacionPaso(this);
                if (!autorizado) {
                    try {
                        System.out.printf("# %s debe esperar.\n", this.idPersona);
                        this.puente.wait();
                    } catch (InterruptedException ex) {
                        System.out.printf("Interrupción mientras %s espera.\n", this.idPersona);
                    }
                }
            }
        }

        System.out.printf("> %s con peso %d puede cruzar, peso en puente %d con %d personas.\n", this.idPersona, this.getPeso(), this.puente.getPeso(), puente.getNumPersonas(), this.puente.getNumPersonas() != 1 ? "s" : "");

        Random r = new Random();  // Pasa al puente, y tarda un tiempo en cruzar
        int tiempoPaso = this.tMinPaso + r.nextInt(this.tMaxPaso - this.tMinPaso + 1);
        try {
            System.out.printf("%s va a tardar tiempo %d en cruzar.\n", this.idPersona, tiempoPaso);
            Thread.sleep(1000*tiempoPaso);
        } catch (InterruptedException ex) {
            System.out.printf("Interrupción mientras %s pasa.\n", this.idPersona);
        }

        // Sale del puente
        synchronized (this.puente) {
            this.puente.terminaPaso(this);
            System.out.printf("< %s sale del puente, puente soporta peso %d, %d persona%s.\n", this.idPersona, this.puente.getPeso(), this.puente.getNumPersonas(), this.puente.getNumPersonas() != 1 ? "s" : "");
            puente.notifyAll();
        }
    }
}

public class PasoPorPuente {

    public static void main(String[] args) {
        final Puente puente = new Puente();

        int tMinParaLlegadaPersona = 1;
        int tMaxParaLlegadaPersona = 30;

        int tMinPaso = 10;
        int tMaxPaso = 50;
        int minPesoPersona = 40;
        int maxPesoPersona = 120;

        int sentido1 = 1;
        int sentido2 = 2;

        System.out.println(">>>>>>>>>>>> Comienza simulación.");
        Random r = new Random();
        int idPersona = 1;

        while (true) {
            int tParaLlegadaPersona = tMinParaLlegadaPersona + r.nextInt(tMaxParaLlegadaPersona - tMinParaLlegadaPersona + 1);
            int pesoPersona = minPesoPersona + r.nextInt(maxPesoPersona - minPesoPersona + 1);
            int sentido = sentido1 +r.nextInt(sentido2-sentido1+1);
            System.out.printf("Siguiente persona llega en %d segundos.\n", tParaLlegadaPersona);
            try {
                Thread.sleep(tParaLlegadaPersona);
            } catch (InterruptedException ex) {
                System.out.printf("Interrumpido proceso principal");
            }

            Thread hiloPersona = new Thread(new Persona(puente, pesoPersona, tMinPaso, tMaxPaso, "P" + idPersona,sentido));
            hiloPersona.start();

            idPersona++;
        }
    }
}
