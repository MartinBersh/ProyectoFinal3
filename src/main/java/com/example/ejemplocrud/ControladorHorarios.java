package com.example.ejemplocrud;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import library.Horarios;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ControladorHorarios implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    public Button btnActualizar;

    @FXML
    public Button btnBorrar;

    @FXML
    public Button btnGuardar;

    @FXML
    public Button btnLimpiar;

    @FXML
    public TextField tAula;

    @FXML
    public TextField tHorario;

    @FXML
    public TableColumn<Horarios, String> colHorario;

    @FXML
    public TableColumn<Horarios, Integer> colId;

    @FXML
    public TableColumn<Horarios, String> colNprofesor;

    @FXML
    public TableColumn<Horarios, String> colaula;

    @FXML
    public TextField tID;

    @FXML
    public TextField tNombreProfesor;

    @FXML
    public TableView<Horarios> table;
    int id = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showHorarios();

    }

    public ObservableList<Horarios> getHorario(){
        ObservableList<Horarios> horarios = FXCollections.observableArrayList();

        String query = "select* from horarios";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while(rs.next()){
                Horarios st = new Horarios();
                st.setId(rs.getInt("id"));
                st.setNombreprofesor(rs.getString("NombreProfesor"));
                st.setAula(rs.getString("Aula"));
                st.setHorario(rs.getString("Horario"));
                horarios.add(st);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return horarios;
    }

    public void showHorarios(){
        ObservableList<Horarios> list = getHorario();
        table.setItems(list);
        colId.setCellValueFactory(new PropertyValueFactory<Horarios, Integer>("id"));
        colNprofesor.setCellValueFactory(new PropertyValueFactory<Horarios, String>("nombreprofesor"));
        colaula.setCellValueFactory(new PropertyValueFactory<Horarios, String>("aula"));
        colHorario.setCellValueFactory(new PropertyValueFactory<Horarios, String>("horario"));
    }

    @FXML
    void clearField(ActionEvent event) {
        clear();

    }

    @FXML
    void createHorarios(ActionEvent event) {

        String insert = "insert into horarios(NombreProfesor, Aula, Horario) values(?,?,?)" ;
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1,tNombreProfesor.getText());
            st.setString(2,tAula.getText());
            st.setString(3,tHorario.getText());
            st.executeUpdate();
            showHorarios();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void getData(MouseEvent event) {
        Horarios horarios = table.getSelectionModel().getSelectedItem();
        id = horarios.getId();
        tNombreProfesor.setText(horarios.getNombreprofesor());
        tAula.setText(horarios.getAula());
        tHorario.setText(horarios.getHorario());

    }

    void clear(){
        tNombreProfesor.setText(null);
        tAula.setText(null);
        tHorario.setText(null);
    }
    @FXML
    void deleteHorarios(ActionEvent event) {

        String delete = "delete from horarios where id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1,id);
            st.executeUpdate();
            showHorarios();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void updateHorarios(ActionEvent event) {

        String update = "update horarios set NombreProfesor =  ?, Aula = ?, Horario = ? where id = ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(update);
            st.setString(1,tNombreProfesor.getText());
            st.setString(2,tAula.getText());
            st.setString(3,tHorario.getText());
            st.setInt(4,id);
            st.executeUpdate();
            showHorarios();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
