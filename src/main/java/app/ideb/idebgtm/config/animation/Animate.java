package app.ideb.idebgtm.config.animation;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import org.springframework.stereotype.Component;

@Component
public class Animate {
    public void setFadeinTransition(Node node, double duration){
        node.setOpacity(0);
        node.visibleProperty().setValue(true);
        node.setDisable(true);
        node.toFront();
        FadeTransition fadeTransition = new FadeTransition(javafx.util.Duration.seconds(duration), node);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            node.setDisable(false);
        });
    }
    public void setFadeoutTransition(Node node, double duration){
        node.setOpacity(1);
        FadeTransition fadeTransition = new FadeTransition(javafx.util.Duration.seconds(duration), node);
        fadeTransition.setToValue(0);
        node.setVisible(false);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            node.visibleProperty().setValue(false);
        });
        node.toBack();
    }

    public FadeTransition FadeoutTransition(Node node,double duration){
        node.setOpacity(1);
        FadeTransition fadeTransition = new FadeTransition(javafx.util.Duration.seconds(duration), node);
        fadeTransition.setToValue(0);
        node.setVisible(false);
        return fadeTransition;
    }
    public FadeTransition FadeintTransition(Node node,double duration){
        node.setOpacity(0);
        node.setVisible(true);
        node.setDisable(true);
        FadeTransition fadeTransition = new FadeTransition(javafx.util.Duration.seconds(duration), node);
        fadeTransition.setToValue(1);
        return fadeTransition;
    }
}
