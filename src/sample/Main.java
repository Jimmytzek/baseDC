package sample;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

import javax.security.auth.callback.Callback;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {
//atributo de conexión
    private ConexionMySQL accesoBD = null;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    @Override
    public void start(Stage primaryStage) throws Exception{
            BorderPane root = new BorderPane();
        primaryStage.setTitle("Hello World");

        //conexion a la base de datos
        this.accesoBD = null;
        try {
            this.accesoBD = new ConexionMySQL();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Conexión exitosa a la base de datos!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }


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
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Information Dialog");
                alert2.setHeaderText(null);


                if (var.trim().length() > 0) {
                    OperacionesClientes opCliente = new OperacionesClientes(accesoBD.getConnection());
                    opCliente.insertCliente(var, var2, var3);
                    alert2.setContentText("Opereción Exitosa!");
                    alert2.showAndWait();
                }
                else {
                    System.out.println("No se pudo insertar usuario");
                    alert2.setContentText("No se realizo la inserción!");
                    alert2.showAndWait();
                }
            }
        });
        cli.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {



                OperacionesClientes bus = new OperacionesClientes(accesoBD.getConnection());

                ListView<String> lvList = new ListView<String>();
                ObservableList<String> items = bus.GetAllCliente();

                lvList.setItems(items);
                lvList.setMaxHeight(Control.USE_PREF_SIZE);
                grid.add(lvList,1,4,4,4);

            }
        });
        eliminar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if(mostar4.getText().trim().length() > 0) {
                    int jml = Integer.parseInt(mostar4.getText());
                    OperacionesClientes Elimi = new OperacionesClientes(accesoBD.getConnection());
                    int s = Elimi.deleteCliente(jml);

                    Elimi.deleteCliente(jml);
                    alert.setTitle("Eliminar");
                    alert.setHeaderText("Eliminación Exitosa!!");
                    alert.showAndWait();
                }
                else {
                    alert.setTitle("Eliminar");
                    alert.setHeaderText("Eliminación Fallida!!");
                    alert.showAndWait();

                }



            }
        });
        buscar.setOnMouseClicked( new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String var = mostar.getText();


                if (var.trim().length() > 0) {
                    OperacionesClientes buscar = new OperacionesClientes(accesoBD.getConnection());
                    ArrayList<Cliente> arrayList;
                    arrayList = buscar.getClientes(var);
                    TableView tab = new TableView();
                    TableColumn idNombre = new TableColumn("clienteId");
                    idNombre.setCellValueFactory(new PropertyValueFactory<Cliente,String>("clienteId"));
                    TableColumn colNombre = new TableColumn("Nombre");
                    colNombre.setCellValueFactory(new PropertyValueFactory<Cliente,String>("Nombre"));
                    TableColumn colApellido = new TableColumn ("apellidos");
                    colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellidos"));
                    TableColumn colDireccion = new TableColumn ("Direccion");
                    colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente,String>("direccion"));

                    tab.getColumns().addAll(idNombre,colNombre,colApellido,colDireccion);


                    ObservableList<Cliente> list = FXCollections.observableArrayList();
                    list.removeAll();
                    list.addAll(arrayList);
                    tab.setItems(list);
                    grid.add(tab,1,4,4,4);

                    tab.getSelectionModel().selectedItemProperty().addListener(
                            new ChangeListener<Cliente>(){
                                @Override
                                public void changed(ObservableValue<? extends Cliente> observableValue, Cliente s, Cliente t1) {
                                    mostar4.setText(String.valueOf(t1.getClienteId()));
                                    mostar.setText(String.valueOf(t1.getNombre()));
                                    mostar2.setText(String.valueOf(t1.getApellidos()));
                                    mostar3.setText(String.valueOf(t1.getDireccion()));

                                }
                            });


                   /* buscar.getCliente(var);
                    Cliente regCliente = buscar.getCliente(var);
                    mostar2.setText(regCliente.getApellidos());
                    mostar3.setText(regCliente.getDireccion());
                    mostar4.setText(String.valueOf(regCliente.getClienteId()));*/
                    alert.setTitle("Busqueda");
                    alert.setHeaderText("Busqueda Exitosa!!");
                    alert.showAndWait();


                }
                else {
                    alert.setTitle("Busqueda");
                    alert.setHeaderText("Busqueda Fallida!!");
                    alert.showAndWait();
                }


            }
        });
        update.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (mostar.getText().trim().length() > 0) {
                    String var = mostar.getText().toString();
                    String var2 = mostar2.getText().toString();
                    String var3 = mostar3.getText().toString();
                    int jml = Integer.parseInt(mostar4.getText());

                    OperacionesClientes edit = new OperacionesClientes(accesoBD.getConnection());
                    edit.updateCliente(jml, var, var2, var3);
                    alert.setTitle("Update");
                    alert.setHeaderText("Update Exitoso!!");
                    alert.showAndWait();
                }
                else {
                    alert.setTitle("Update");
                    alert.setHeaderText("Update Fallido!!");
                    alert.showAndWait();

                }

            }
        });


        return grid;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

