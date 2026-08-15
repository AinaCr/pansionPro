package com.example.demo;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;

public class Me {

    @FXML
    private StackPane page1;

    @FXML
    private  StackPane page2;


    @FXML
    private void setConnexion(){
        page1.setVisible(false);
        page1.setManaged(false);

        page2.setVisible(true);
        page2.setManaged(true);

    }


}