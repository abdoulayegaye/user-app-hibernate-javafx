package sn.dev.userapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sn.dev.userapp.entity.User;

import javax.persistence.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = managerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    @FXML
    private TableColumn<User, Integer> idCol;

    @FXML
    private TableColumn<User, String> loginCol;

    @FXML
    private TextField loginTfd;

    @FXML
    private TableColumn<User, String> nomCol;

    @FXML
    private TextField nomTfd;

    @FXML
    private PasswordField passwordTfd;

    @FXML
    private TableColumn<User, String> prenomCol;

    @FXML
    private TextField prenomTfd;

    @FXML
    private Button saveBtn;

    @FXML
    private TableView<User> usersTbl;

    int id;

    @FXML
    void clearFunction(ActionEvent event) {
        viderChamps();
    }

    @FXML
    void deleteFunction(ActionEvent event) {
        try{
            transaction.begin();
            User user = entityManager.find(User.class, id);
            entityManager.remove(user);
            transaction.commit();
            viderChamps();
            loadTable();
            saveBtn.setDisable(false);
        }finally {
            if (transaction.isActive())
                transaction.rollback();
        }
    }

    @FXML
    void generateLogin(ActionEvent event) {
        if(!nomTfd.getText().equals("") && !prenomTfd.getText().equals("")){
            loginTfd.setText("jpa#" + nomTfd.getText().charAt(0) + prenomTfd.getText().charAt(prenomTfd.getText().length()-1) + "@fx");
        }
    }

    @FXML
    void getData(MouseEvent event) {
        User user = usersTbl.getSelectionModel().getSelectedItem();
        id = user.getIdUser();
        nomTfd.setText(user.getNomUser());
        prenomTfd.setText(user.getPrenomUser());
        loginTfd.setText(user.getLoginUser());
        passwordTfd.setText(user.getPasswordUser());
        saveBtn.setDisable(true);
    }

    @FXML
    void refreshFunction(ActionEvent event) {

    }

    @FXML
    void saveFunction(ActionEvent event) {
        try{
            transaction.begin();
            User user = new User();
            user.setNomUser(nomTfd.getText());
            user.setPrenomUser(prenomTfd.getText());
            user.setLoginUser(loginTfd.getText());
            user.setPasswordUser(passwordTfd.getText());
            entityManager.persist(user);
            transaction.commit();
            viderChamps();
            loadTable();
        }finally {
            if (transaction.isActive())
                transaction.rollback();
        }
    }

    @FXML
    void updateFunction(ActionEvent event) {
        try{
            transaction.begin();
            User user = entityManager.find(User.class, id);
            user.setNomUser(nomTfd.getText());
            user.setPrenomUser(prenomTfd.getText());
            user.setLoginUser(loginTfd.getText());
            user.setPasswordUser(passwordTfd.getText());
            entityManager.persist(user);
            transaction.commit();
            viderChamps();
            loadTable();
            saveBtn.setDisable(false);
        }finally {
            if (transaction.isActive())
                transaction.rollback();
        }
    }

    public ObservableList<User> getUsers(){
        ObservableList<User> users = FXCollections.observableArrayList();
        TypedQuery<User> query = entityManager.createNamedQuery("listUsers", User.class);
        users.addAll(query.getResultList());
        return users;
    }

    public void loadTable(){
        usersTbl.setItems(getUsers());
        idCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("idUser"));
        nomCol.setCellValueFactory(new PropertyValueFactory<User, String>("nomUser"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<User, String>("prenomUser"));
        loginCol.setCellValueFactory(new PropertyValueFactory<User, String>("loginUser"));
    }

    public void viderChamps(){
        nomTfd.setText("");
        prenomTfd.setText("");
        loginTfd.setText("");
        passwordTfd.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();
    }
}
