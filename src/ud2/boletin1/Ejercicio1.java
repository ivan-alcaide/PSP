package ud2.boletin1;



class colecta{
    private static final int CANTIDAD_MAX=2000;
    private int cantidad=0;


    synchronized public int getCantidadMax(){
        return CANTIDAD_MAX;
    }


    synchronized public int getCantidad() {
        return cantidad;
    }

    synchronized public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    synchronized public boolean accesoColecta(Hilo h){
        Boolean result = (this.cantidad<this.CANTIDAD_MAX || this.cantidad+h.getCantidad()<CANTIDAD_MAX) ? true : false;

        if (result){
            this.cantidad+=h.getCantidad();
        }

        return result;
    }
}


class Hilo implements Runnable{

    private int cantidad;

    public Hilo(int cantidad){
        this.cantidad=cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    @Override
    public void run() {

    }
}

public class Ejercicio1 {



}
