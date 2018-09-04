package app.ideb.idebgtm.config;

import app.ideb.idebgtm.config.customestage.Undecorator;
import app.ideb.idebgtm.view.FxmlView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;

import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {

    private static final Logger LOG = getLogger(StageManager.class);
    private final Stage primaryStage;
    private final SpringFXMLLoader springFXMLLoader;

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    public void switchScene(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        show(viewRootNodeHierarchy, view.getTitle());
    }
    public void openNode(StackPane stackPane, final FxmlView view){
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        stackPane.getChildren().clear();
        stackPane.getChildren().add(viewRootNodeHierarchy);
    }

    public void addLoader(StackPane stackPane, final FxmlView view){
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        viewRootNodeHierarchy.toBack();
        stackPane.getChildren().add(viewRootNodeHierarchy);

    }

    public void openLoader(StackPane stackPane,boolean open){
        Node node=stackPane.lookup("#root_loader");
        node.visibleProperty().setValue(open);
    }

    public void openNode(StackPane stackPane, Label title, final FxmlView view, boolean clear){
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        viewRootNodeHierarchy.toFront();
        if(clear==true){
            stackPane.getChildren().clear();
        }
        stackPane.getChildren().add(viewRootNodeHierarchy);
        Platform.runLater(() -> {
            if(title !=null){
                title.setText(view.getTitle());
            }
        });

    }

    private void show(final Parent rootnode, String title) {
       /* Scene scene = prepareScene(rootnode);
        //scene.getStylesheets().add("/styles/Styles.css");
        
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        
        try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + title,  exception);
        }*/

        final Undecorator undecorator = new Undecorator(primaryStage, (Region) rootnode);
        undecorator.getStylesheets().addAll("skin/undecorator.css");
        Scene scene = prepareScene(undecorator);
        scene.setFill(Color.valueOf("black"));
        scene.getStylesheets().clear();
        scene.setFill(Color.TRANSPARENT);


        primaryStage.setTitle(title);
        primaryStage.setScene(scene);


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                we.consume();   // Do not hide
                undecorator.setFadeOutTransition();
            }
        });
        try {
            primaryStage.show();

        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + title,  exception);
        }
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.setMinWidth(undecorator.getMinWidth());
        primaryStage.setMinHeight(undecorator.getMinHeight());

    }
    
    private Scene prepareScene(Parent rootnode){
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);
        return scene;
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */
    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
        }
        return rootNode;
    }
    
    
    private void logAndExit(String errorMsg, Exception exception) {
        LOG.error(errorMsg, exception, exception.getCause());
        Platform.exit();
    }

}
