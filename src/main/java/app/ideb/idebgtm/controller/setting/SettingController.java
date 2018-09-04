package app.ideb.idebgtm.controller.setting;

import app.ideb.idebgtm.config.StageManager;
import app.ideb.idebgtm.config.animation.Animate;
import app.ideb.idebgtm.view.FxmlView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class SettingController implements Initializable {

    private static SettingController instance;
    public SettingController() {
        instance = this;
    }
    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private ScrollPane root_setting;
    @FXML
    private FlowPane flow_menu;
    @FXML
    public StackPane main_Setting;

    @Autowired
    Animate animate;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root_setting.widthProperty().addListener((observable, oldValue, newValue) -> {
            if(Integer.valueOf(String.valueOf(newValue).replace(".0",""))> 430.0 &&
                    Integer.valueOf(String.valueOf(newValue).replace(".0",""))< 600.0){
                flow_menu.setAlignment(Pos.CENTER);
                flow_menu.setHgap(15.0);
                flow_menu.getChildren().forEach(node -> {
                    Button button= (Button) node;
                    button.setPrefWidth(200.0);
                    button.setPrefHeight(200.0);
                });
            } else if(Integer.valueOf(String.valueOf(newValue).replace(".0",""))>= 600.0 &&
                    Integer.valueOf(String.valueOf(newValue).replace(".0",""))< 800.0){
                flow_menu.setAlignment(Pos.CENTER);
                flow_menu.setHgap(15.0);
                flow_menu.getChildren().forEach(node -> {
                    Button button= (Button) node;
                    button.setPrefWidth(150.0);
                    button.setPrefHeight(150.0);
                });
            }else if(Integer.valueOf(String.valueOf(newValue).replace(".0",""))>= 800.0 ){
                flow_menu.setAlignment(Pos.CENTER);
                flow_menu.setHgap(15.0);
                flow_menu.getChildren().forEach(node -> {
                    Button button= (Button) node;
                    button.setPrefWidth(200.0);
                    button.setPrefHeight(200.0);
                });
            }
            else{
                flow_menu.setAlignment(Pos.TOP_LEFT);
                flow_menu.setHgap(0.0);
                flow_menu.getChildren().forEach(node -> {
                    Button button= (Button) node;
                    button.setPrefWidth(375.0);
                    button.setPrefHeight(75.0);
                });
            }

        });
    }


    @FXML
    void open_system_setting(ActionEvent event) {
        main_Setting.getChildren().clear();
        Platform.runLater(() -> {
            stageManager.openNode(main_Setting, FxmlView.SETTING_SYSTEM);
        });

    }
    @FXML
    void open_parameter_setting(ActionEvent event) {
        main_Setting.getChildren().clear();
        Platform.runLater(() -> {
            stageManager.openNode(main_Setting, FxmlView.SETTING_PARAMETER);
        });
    }
    @FXML
    void open_bpjs_setting(ActionEvent event) {
        main_Setting.getChildren().clear();
        Platform.runLater(() -> {
            stageManager.openNode(main_Setting, FxmlView.SETTING_BPJS);
        });
    }
    @FXML
    void open_pph21_setting(ActionEvent event) {
        main_Setting.getChildren().clear();
        Platform.runLater(() -> {
            stageManager.openNode(main_Setting, FxmlView.SETTING_PPH21);
        });
    }

    public void home_setting(){
        main_Setting.getChildren().clear();
        main_Setting.getChildren().add(flow_menu);
        animate.setFadeinTransition(flow_menu,0.5);
    }



    public static SettingController getInstance() {
        return instance;

    }


}
