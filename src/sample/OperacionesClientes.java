package sample;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OperacionesClientes {

    Connection connection;

    public OperacionesClientes(Connection conn){
        this.connection = conn;
    }

    public Cliente getCliente(String nombres){
        IntegerProperty clienteId = new SimpleIntegerProperty(0);
        StringProperty nombre =  new SimpleStringProperty(""), apellidos =  new SimpleStringProperty(""), direccion =  new SimpleStringProperty("");

        String query = "SELECT * "+
                "FROM cliente"+
                " WHERE nombre='"+ nombres +"'" ;

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                clienteId = new SimpleIntegerProperty( rs.getInt("clienteid"));
                nombre = new SimpleStringProperty(rs.getString("nombre"));
                apellidos = new SimpleStringProperty(rs.getString("apellidos"));
                direccion = new SimpleStringProperty(rs.getString("direccion"));
            }

            //System.out.println(clienteId + ", " + nombre + " " + apellidos + ", " + direccion);

            return new Cliente(clienteId, nombre, apellidos, direccion);
        }
        catch (java.sql.SQLException ex){
            ex.printStackTrace();
            System.out.println("SQLException:␣" + ex.getMessage());
            System.out.println("SQLState:␣" + ex.getSQLState());
            System.out.println("VendorError:␣" + ex.getErrorCode());

            return null;
        }
    }

    public ObservableList GetAllCliente(){


        int clienteId = 0;
        String nombre = "", apellidos = "", direccion = "";

        String query = "SELECT clienteid, nombre, apellidos, direccion " +
                "FROM cliente " ;

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ObservableList<String> items = FXCollections.observableArrayList();
            while (rs.next()) {
                clienteId = rs.getInt("clienteid");
                nombre = rs.getString("nombre");
                apellidos = rs.getString("apellidos");

                String full = clienteId+" .- "+nombre + " " + apellidos;
                items.add(full);
            }
            return items;

        }
        catch (java.sql.SQLException ex){
            ex.printStackTrace();
            System.out.println("SQLException:␣" + ex.getMessage());
            System.out.println("SQLState:␣" + ex.getSQLState());
            System.out.println("VendorError:␣" + ex.getErrorCode());

            return null;
        }



    }

    public ArrayList<Cliente> getClientes(String NombresC) {

        String query = "SELECT clienteid,nombre,apellidos,direccion " +
                "FROM cliente " +
                "WHERE nombre= '" + NombresC + "'";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            ArrayList<Cliente> AListCliente = new ArrayList<>();
            while (rs.next()) {
                IntegerProperty clienteId = new SimpleIntegerProperty( rs.getInt("clienteid"));
               StringProperty nombre = new SimpleStringProperty( rs.getString("nombre"));
                StringProperty apellidos = new SimpleStringProperty( rs.getString("apellidos"));
                StringProperty direccion = new SimpleStringProperty( rs.getString("direccion"));
                AListCliente.add(new Cliente(clienteId , nombre, apellidos, direccion));


            }
            return AListCliente;
        }
        catch (java.sql.SQLException ex){

            ex.printStackTrace();
            System.out.println("SQLException:_"+ ex.getMessage());
            System.out.println("SQLState:_" + ex.getSQLState());
            System.out.println("VendorError:_" + ex.getErrorCode());

            return null;
        }


    }

    public int deleteCliente(int id){
        int clienteId;
        String nombre, apellidos, direccion;

        String query = "delete from cliente where clienteID = " + id;

        int numRegs = 0;
        try {
            Statement stmt = connection.createStatement();
            numRegs = stmt.executeUpdate(query);

            System.out.println("Cantidad de registros afectados: " + numRegs);
        }
        catch (java.sql.SQLException ex){
            ex.printStackTrace();
            System.out.println("SQLException:␣" + ex.getMessage());
            System.out.println("SQLState:␣" + ex.getSQLState());
            System.out.println("VendorError:␣" + ex.getErrorCode());
        }

        return numRegs;
    }

    public int insertCliente(String nombre, String apellidos, String direccion){

        String query = "insert into cliente(nombre, apellidos, direccion) " +
                "values ('" + nombre + "', '" + apellidos + "', '" + direccion + "')";

      /*  insert into cliente(nombre, apellidos, direccion
                values ('Jorge', 'Estrada', 'Lázaro Cárdenas 123')  */

        int numRegs = 0;
        try {
            Statement stmt = connection.createStatement();
            numRegs = stmt.executeUpdate(query);

            System.out.println("Cantidad de registros afectados: " + numRegs);

        }
        catch (java.sql.SQLException ex){
            ex.printStackTrace();
            System.out.println("SQLException:␣" + ex.getMessage());
            System.out.println("SQLState:␣" + ex.getSQLState());
            System.out.println("VendorError:␣" + ex.getErrorCode());
        }

        return numRegs;
    }

    public int updateCliente(int id, String nombre,String apellidos, String direccion){
        String query = "update cliente " +
                "set  nombre='"+ nombre +"', apellidos='"+apellidos+"',direccion='"+direccion+"'" +
                "where  clienteID= "+id;
        int RegNum =0;

        try {
            Statement stmt = connection.createStatement();
            RegNum = stmt.executeUpdate(query);
            System.out.println("Registros afectados: " + RegNum);
            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
            alert3.setTitle("Information Dialog");
            alert3.setHeaderText(null);
            alert3.setContentText("Update exitoso!!!");
            alert3.showAndWait();

        }
        catch (java.sql.SQLException ex){
            ex.printStackTrace();
            System.out.println("SQLException:␣" + ex.getMessage());
            System.out.println("SQLState:␣" + ex.getSQLState());
            System.out.println("VendorError:␣" + ex.getErrorCode());
        }
        return RegNum;
    }
}

