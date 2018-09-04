package app.ideb.idebgtm.controller.loader;

import app.ideb.idebgtm.config.StageManager;
import app.ideb.idebgtm.config.animation.Animate;
import app.ideb.idebgtm.controller.HomeController;
import app.ideb.idebgtm.controller.LoginController;
import app.ideb.idebgtm.service.LoginService;
import app.ideb.idebgtm.view.FxmlView;
import com.jfoenix.controls.JFXProgressBar;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class LoaderController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;
    @Autowired
    LoginService loginService;
    @Autowired
    Animate animate;

    private SimpleStringProperty task = new SimpleStringProperty(this, "");
    public SimpleStringProperty P = new SimpleStringProperty(this, "");
    private Task OpenLoader;
    private static LoaderController instance;
    private StackPane content;
    private JSONObject jsonObject = null;
    private Label page_title;
    public LoaderController() {
        instance = this;
    }
    Node node=null;
    @FXML
    private StackPane root_loader;

    @FXML
    private VBox loader_pane;

    @FXML
    private JFXProgressBar progress;

    @FXML
    private Label lbl_task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progress.progressProperty().setValue(0);
        Platform.runLater(() -> {
            root_loader.visibleProperty().addListener((observable, oldValue, newValue) -> {
                if(Boolean.valueOf(newValue)){
                    root_loader.toFront();
                    //animate.setFadeinTransition(root_loader,0.5);
                    start_Task_loader();
                }else{
                    //
                    //animate.setFadeoutTransition(root_loader,0.2);
                    root_loader.toBack();
                }
            });


        });


    }

    private void start_Task_loader(){

        content = HomeController.getInstance().content;
        page_title = HomeController.getInstance().page_title;
        OpenLoader = createWorker(pProperty().getValue());
        progress.progressProperty().unbind();
        progress.progressProperty().bind(OpenLoader.progressProperty());
       // lbl_task.textProperty().setValue("");


        OpenLoader.setOnRunning(event -> {
            lbl_task.textProperty().bind(OpenLoader.messageProperty());
        });
        OpenLoader.setOnSucceeded(event -> {
            taskProperty().setValue(pProperty().getValue());
            if(pProperty().getValue().equals("PROSES_LOGIN")){
                node=LoginController.getInstance().root_login.lookup("#root_loader");
            }else {
                node=HomeController.getInstance().content.lookup("#root_loader");
            }
            node.visibleProperty().setValue(false);
            node=null;

            dotask(taskProperty().getValue());

        });

        new Thread(OpenLoader).start();
    }

    public Task createWorker(String p) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                updateMessage("Please wait....");
                for (int i = 0; i < 15; i++) {
                    Thread.sleep(100);
                    if (i == 5) {
                        //updateMessage("Init Data ".concat(taskProperty().getValue()));
                    }
                    updateProgress(i + 1, 15);
                }
                return true;
            }
        };
    }

    private void dotask(String task) {
        switch (task) {
            case "DASHBOARD":
                stageManager.openNode(content,null,  FxmlView.DASHBOARD,false);
                break;
            case "KONFIGURASI":
                stageManager.openNode(content,null,  FxmlView.SETTING,false);
                break;
            case "LAPORAN":
                stageManager.openNode(content, null, FxmlView.LAPORAN,false);
                break;
            case "PEGAWAI":
                stageManager.openNode(content,null,  FxmlView.PEGAWAI,false);
                break;
            case "PENGGAJIAN":
                stageManager.openNode(content, null, FxmlView.PENGGAJIAN,false);
                break;
            case "PROSES_LOGIN":
                prosesLogin();
                break;
            case "REPORT_VIEWER" :
                stageManager.openNode(HomeController.getInstance().Home_root,null,FxmlView.REPORT_VIEWER,false);
                    break;
        }
    }

    private void prosesLogin() {
        try {
            jsonObject = new JSONObject();
            jsonObject.put("email", "halas86gtm@gmail.com");
            jsonObject.put("password", "12345678");
            String res = loginService.login("/hrd_signin", jsonObject);
            jsonObject = (JSONObject) new JSONParser().parse(res);
            if (jsonObject.containsKey("token")) {
                System.setProperty("token", jsonObject.get("token").toString());
                System.setProperty("username", jsonObject.get("username").toString());
                System.setProperty("kodekantor", jsonObject.get("kodekantor").toString());
                System.setProperty("useras", jsonObject.get("useras").toString());
                System.setProperty("namakantor", jsonObject.get("namakantor").toString());
                Platform.runLater(() -> {
                    LoginController.getInstance().openHome();
                });
            }
        } catch (ParseException e) {

        }

    }

    public String getTask() {
        return task.get();
    }

    public SimpleStringProperty taskProperty() {
        return task;
    }

    public void setTask(String task) {
        this.task.set(task);
    }
    public SimpleStringProperty pProperty() {
        return P;
    }


    public static LoaderController getInstance() {
        return instance;
    }
}
