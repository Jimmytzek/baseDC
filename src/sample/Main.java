package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
            BorderPane root = new BorderPane();
        primaryStage.setTitle("Hello World");



        HBox BarraSuperior = TitiloPrincipal();
        GridPane tabla = centro();
        root.setTop(BarraSuperior);
        root.setCenter(tabla);

        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();

    }
    public HBox TitiloPrincipal(){
        HBox Barrasuperior = new HBox();
        Barrasuperior.setPadding(new Insets(20));
        Barrasuperior.setSpacing(15);
        Barrasuperior.setStyle("-fx-background-color: cadetblue;");
        Text tex = new Text("Tabla Clientes");
        tex.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD,36));
        tex.setFill(Color.BLACK);
        tex.setStroke(Color.web("#7080A0"));
        tex.getX();
        Barrasuperior.getChildren().add(tex);
        return Barrasuperior;
    }

    public GridPane centro(){

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(20);
        grid.setPadding(new Insets(0,5,0,5));

       Label id = new Label("Id");
        TextField mostar4 = new TextField();
        grid.add(id,0,1);
        grid.add(mostar4,0,2);


        Label Nombre = new Label("Nombre");
        TextField mostar = new TextField();
        grid.add(Nombre,1,1);
        grid.add(mostar,1,2);

        Label apellido = new Label("Apellido");
        TextField mostar2 = new TextField();
        grid.add(apellido,2,1);
        grid.add(mostar2,2,2);

        Label direcion = new Label("Direción");
        TextField mostar3 = new TextField();
        grid.add(direcion,3,1);
        grid.add(mostar3,3,2);

        Button agregar = new Button("Agregar");
        agregar.setPrefSize(70,3);
        grid.add(agregar,1,3);

        Button eliminar = new Button("Eliminar");
        eliminar.setPrefSize(70,3);
        grid.add(eliminar,2,3);

        Button cli = new Button("clientes");
        cli.setPrefSize(70,3);
        grid.add(cli,3,3);

        Button buscar = new Button("Buscar");
        buscar.setPrefSize(70,3);
        grid.add(buscar,0,3);

        Button update = new Button("Actualizar");
        update.setPrefSize(70,3);
        grid.add(update,5,3);


        agregar.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override

            public void handle(MouseEvent mouseEvent){
                String var = mostar.getText().toString();
                String var2 = mostar2.getText().toString();
                String var3 = mostar3.getText().toString();

                ConexionMySQL accesoBD = null;
                try {
                    accesoBD = new ConexionMySQL();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (var.trim().length() > 0) {
                    OperacionesClientes opCliente = new OperacionesClientes(accesoBD.getConnection());
                    opCliente.insertCliente(var, var2, var3);
                }
                else {
                    System.out.println("Nose pudo insertar usuario");
                }
            }
        });
        cli.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                ConexionMySQL accesoBD = null;
                try {
                    accesoBD = new ConexionMySQL();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                OperacionesClientes bus = new OperacionesClientes(accesoBD.getConnection());

                ListView<String> lvList = new ListView<String>();
                ObservableList<String> items = bus.GetAllCliente();

                lvList.setItems(items);
                lvList.setMaxHeight(Control.USE_PREF_SIZE);
                grid.add(lvList,1,4);

            }
        });
        eliminar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                int jml = Integer.parseInt(mostar4.getText());

                ConexionMySQL accesoBD = null;
                try {
                    accesoBD = new ConexionMySQL();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                OperacionesClientes Elimi = new OperacionesClientes(accesoBD.getConnection());
                int s  = Elimi.deleteCliente(jml);

                Elimi.deleteCliente(jml);



            }
        });
        buscar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String var = mostar.getText().toString();

                ConexionMySQL accesoBD = null;
                try {
                    accesoBD = new ConexionMySQL();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (var.trim().length() > 0) {
                    OperacionesClientes buscar = new OperacionesClientes(accesoBD.getConnection());

                    buscar.getCliente(var);
                    Cliente regCliente = buscar.getCliente(var);
                    Label usu = new Label("Nombre: "+ regCliente.getNombre()+" Apellido: "+regCliente.getApellidos()+" Dirección:  "+regCliente.getDireccion());
                    grid.add(usu,1,5,4,5);
                }
                else {
                    System.out.println("No se pudo insertar usuario");
                }
            }
        });
        update.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String var = mostar.getText().toString();
                String var2 = mostar2.getText().toString();
                String var3 = mostar3.getText().toString();
                int jml = Integer.parseInt(mostar4.getText());
                ConexionMySQL accesoBD = null;
                try {
                    accesoBD = new ConexionMySQL();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                OperacionesClientes edit = new OperacionesClientes(accesoBD.getConnection());
                edit.updateCliente(jml,var,var2,var3);


            }
        });


        return grid;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

