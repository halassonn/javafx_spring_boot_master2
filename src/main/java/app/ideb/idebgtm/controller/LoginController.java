package app.ideb.idebgtm.controller;


import app.ideb.idebgtm.config.AppUtil;
import app.ideb.idebgtm.config.StageManager;
import app.ideb.idebgtm.controller.loader.LoaderController;
import app.ideb.idebgtm.view.FxmlView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Controller
public class LoginController implements Initializable{

	private static LoginController instance;
	public LoginController() {
		instance = this;
	}
	private Stage stage;
	@FXML
	public StackPane root_login;

    @Lazy
    @Autowired
    private StageManager stageManager;
    @Autowired
	AppUtil appUtil;



	@FXML
	void login(ActionEvent event) {
		String Proses=appUtil.getStringFromResourceBundle("proses.login");
		LoaderController.getInstance().pProperty().setValue(Proses);
		Platform.runLater(() -> {
			//stageManager.openNode(root_login,null,FxmlView.LOADER,false);
			stageManager.openLoader(root_login,true);
		});
	}
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Platform.runLater(() -> {
			appUtil.getThema(root_login);
			stageManager.addLoader(root_login,FxmlView.LOADER);
		});

	}

	public void openHome(){
		stage= (Stage) (root_login).getScene().getWindow();
		stage.setResizable(true);
		stage.close();
		stage.setTitle(System.getProperty("namakantor"));
		stageManager.switchScene(FxmlView.HOME);
	}
	public static LoginController getInstance() {
		return instance;
	}
}
