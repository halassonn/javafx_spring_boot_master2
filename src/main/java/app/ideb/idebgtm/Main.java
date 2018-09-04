package app.ideb.idebgtm;

import app.ideb.idebgtm.config.AppUtil;
import app.ideb.idebgtm.config.StageManager;
import app.ideb.idebgtm.view.FxmlView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends Application implements CommandLineRunner {

    protected ConfigurableApplicationContext springContext;
    protected StageManager stageManager;
    @Autowired
    AppUtil appUtil;

    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        springContext = springBootApplicationContext();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stageManager = springContext.getBean(StageManager.class, stage);
        displayInitialScene();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }

    /**
     * Useful to override this method by sub-classes wishing to change the first
     * Scene to be displayed on startup. Example: Functional tests on main
     * window.
     */
    protected void displayInitialScene() {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    
    private ConfigurableApplicationContext springBootApplicationContext() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
        String[] args = getParameters().getRaw().stream().toArray(String[]::new);
        return builder.run(args);
    }

    @Override
    public void run(String... strings) throws Exception {

        appUtil.Copy_App_conf();
    }
}
