package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Cliente {
    private IntegerProperty clienteId;
    private  StringProperty nombre;
    private  StringProperty apellidos;
    private StringProperty direccion;

    public Cliente(IntegerProperty clienteId, StringProperty nombre, StringProperty apellidos, StringProperty direccion) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
    }
    public int getClienteId() {
        return clienteId.get();
    }

    public IntegerProperty clienteIdProperty() {
        return clienteId;
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos.get();
    }

    public StringProperty apellidosProperty() {
        return apellidos;
    }

    public String getDireccion() {
        return direccion.get();
    }

    public StringProperty direccionProperty() {
        return direccion;
    }

    public void setClienteId(int clienteId) {
        this.clienteId.set(clienteId);
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public void setApellidos(String apellidos) {
        this.apellidos.set(apellidos);
    }

    public void setDireccion(String direccion) {
        this.direccion.set(direccion);
    }


    @Override
    public String toString() {
        return "Cliente{" +
                "clienteId=" + clienteId +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
