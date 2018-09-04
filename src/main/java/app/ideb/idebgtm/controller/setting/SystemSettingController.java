package app.ideb.idebgtm.controller.setting;

import app.ideb.idebgtm.config.AppUtil;
import app.ideb.idebgtm.controller.HomeController;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.commons.configuration.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class SystemSettingController implements Initializable {
    private static SystemSettingController instance;

    public SystemSettingController() {
        instance = this;
    }

    @Autowired
    AppUtil appUtil;
    @FXML
    public Label title_page;

    @FXML
    private JFXComboBox<String> cmb_thema;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title_page.setText(appUtil.getStringFromResourceBundle("system.setting.title"));
        Platform.runLater(() -> {
            List<String> strings = new ArrayList<String>(Arrays.asList(appUtil.getStringFromResourceBundlethema("theme").split(",")));
            strings.forEach(s -> {
                cmb_thema.getItems().add(s);
            });
            //cmb_thema.getSelectionModel().select(appUtil.getThema());
        });

        cmb_thema.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            try {
                appUtil.settheme(HomeController.getInstance().Home_root,cmb_thema.getSelectionModel().getSelectedItem());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void back_to_main_setting(ActionEvent event) {
        SettingController.getInstance().home_setting();

    }


    public static SystemSettingController getInstance() {
        return instance;

    }
}
