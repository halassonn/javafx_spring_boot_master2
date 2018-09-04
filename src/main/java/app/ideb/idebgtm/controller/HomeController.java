package app.ideb.idebgtm.controller;

import app.ideb.idebgtm.config.AppUtil;
import app.ideb.idebgtm.config.StageManager;
import app.ideb.idebgtm.config.animation.Animate;
import app.ideb.idebgtm.controller.loader.LoaderController;
import app.ideb.idebgtm.view.FxmlView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class HomeController implements Initializable {
    private Stage stage;
    private static HomeController instance;
    public HomeController() {
        instance = this;
    }

    @Lazy
    @Autowired
    StageManager stageManager;
    @Autowired
    Animate animate;
    @Autowired
    AppUtil appUtil;
    @FXML
    public StackPane Home_root;
    @FXML
    public StackPane content;
    @FXML
    private Button open_user;
    @FXML
    public VBox side_menu;
    @FXML
    private HBox menu;
    @FXML
    private HBox main_layout;

    @FXML
    private StackPane icon_pane;

    @FXML
    private Label menu_title;

    @FXML
    private AnchorPane main_content;
    @FXML
    private AnchorPane side_over_flow;
    @FXML
    public Label page_title;
    @FXML
    private HBox menu_Home;
    @FXML
    private HBox menu_pegawai;
    @FXML
    private HBox menu_penggajian;
    @FXML
    private HBox menu_laporan;
    @FXML
    private HBox menu_setting;
    @FXML
    private HBox menu_logout;
    @FXML
    private Label user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {
            appUtil.getThema(HomeController.getInstance().Home_root);
            stageManager.openNode(content,FxmlView.DASHBOARD);
            stageManager.addLoader(content,FxmlView.LOADER);
        });

        initSideMenu();


    }

    public static HomeController getInstance() {
        return instance;

    }

    @FXML
    void open_menu_side(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)){
            if(side_menu.getPrefWidth() < 250 && side_menu.getPrefWidth() ==50 ){
                side_menu.setPrefWidth(250);
                AnchorPane.setLeftAnchor(main_content,250.0);
                animate.setFadeinTransition(side_menu,0.5);
                main_layout.getChildren().remove(side_over_flow);
                Home_root.getChildren().add(side_over_flow);
                side_over_flow.toFront();
            }else{
                devault_position_sidemenu();
            }
        }
    }
    public void initSideMenu(){
        menu_Home.setOnMouseClicked(event -> {
            LoaderController.getInstance().pProperty().setValue(appUtil.getStringFromResourceBundle("dashboard.title"));
            Platform.runLater(() -> {
               // stageManager.openNode(content,null,FxmlView.LOADER,false);
                stageManager.openLoader(content,true);
            });
            devault_position_sidemenu();
            //nodeManajer.switchNode(content,page_title,FxmlView.DASHBOARD);

        });
        menu_pegawai.setOnMouseClicked(event -> {

            Platform.runLater(() -> {
                LoaderController.getInstance().pProperty().setValue(appUtil.getStringFromResourceBundle("pegawai.title"));
               // stageManager.openNode(content,null,FxmlView.LOADER,false);
                stageManager.openLoader(content,true);
            });
            devault_position_sidemenu();
        });
        menu_laporan.setOnMouseClicked(event -> {
            LoaderController.getInstance().pProperty().setValue(appUtil.getStringFromResourceBundle("laporan.title"));
            Platform.runLater(() -> {
               // stageManager.openNode(content,null,FxmlView.LOADER,false);
                stageManager.openLoader(content,true);
            });
            devault_position_sidemenu();
        });
        menu_setting.setOnMouseClicked(event -> {
            LoaderController.getInstance().pProperty().setValue(appUtil.getStringFromResourceBundle("setting.title"));
            Platform.runLater(() -> {
                //stageManager.openNode(content,null,FxmlView.LOADER,false);
                stageManager.openLoader(content,true);
            });
            devault_position_sidemenu();
        });
        menu_penggajian.setOnMouseClicked(event -> {
            LoaderController.getInstance().pProperty().setValue(appUtil.getStringFromResourceBundle("penggajian.title"));
            Platform.runLater(() -> {
               // stageManager.openNode(content,null,FxmlView.LOADER,false);
                stageManager.openLoader(content,true);
            });
            devault_position_sidemenu();
        });
        menu_logout.setOnMouseClicked(event -> {
            stage= (Stage) (Home_root).getScene().getWindow();
            stage.close();
            stage.setResizable(false);
            stage.setTitle("LOGIN APP");
            stageManager.switchScene(FxmlView.LOGIN);
        });
    }

    private void devault_position_sidemenu(){
        Home_root.getChildren().remove(side_over_flow);
        main_layout.getChildren().remove(side_over_flow);
        main_layout.getChildren().add(side_over_flow);
        side_over_flow.toBack();
        side_menu.setPrefWidth(50);
        AnchorPane.setLeftAnchor(main_content,50.0);
    }

}
