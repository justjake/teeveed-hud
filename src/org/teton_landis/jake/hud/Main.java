package org.teton_landis.jake.hud;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.*;
import javafx.util.Duration;

public class Main extends Application {

    static final double fontSize = 70;
    static final double totalWidth = 1200;
    static final double sixteenNine = (9.0 / 16.0);
    static final Duration FadeTime = Duration.millis(300);

    private static class Delta { double x, y; }


    /**
     * Make a whole stage draggable by a node. Useful for moving an undecorated
     * window around.
     * https://stackoverflow.com/questions/11780115/moving-an-undecorated-stage-in-javafx-2
     * @param stage to be made draggable
     * @param node drag handle
     */
    static private void dragStageByNode(final Stage stage, Node node) {
        final Delta dragDelta = new Delta();
        node.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = stage.getX() - mouseEvent.getScreenX();
                dragDelta.y = stage.getY() - mouseEvent.getScreenY();
            }
        });
        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                stage.setY(mouseEvent.getScreenY() + dragDelta.y);
            }
        });
    }

    /**
     * what the fuck am i doing java?
     * not typing "new String[] { ... }" every time i want an array, that's what
     * #drunkCoding
     * @param derp some strings in a raw array
     * @return derp
     */
    private String[] str(String... derp) { return derp; }

    @Override
    public void start(final Stage primaryStage) throws Exception{
        // first off - mock content creation
        // top -- intent identified
        StyledTextFlow intentIdentified = new StyledTextFlow(
                str("♦ Intent:"),
                str("query episode → play" , "action")
        );
        intentIdentified.getStyleClass().addAll("small", "action");

        // main body contents -- what the user put into the webui system
        StyledTextFlow user_text = new StyledTextFlow(
                str("Ok tv. Play"),
                str("Breaking Bad", "entity"),
                str("season"),
                str("two", "entity"),
                str("episode"),
                str("four", "entity"),
                str("please")
        );
        user_text.getStyleClass().addAll("large");

        // bottom -- result
        StyledTextFlow result = new StyledTextFlow(
                str("found"),
                str("Breaking Bad - S02E04 - Thirty-Eight Snub", "entity")
        );
        result.getStyleClass().addAll("small", "result");



        // set up vertical sections
        VBox vbox = new VBox();
        vbox.getChildren().addAll(intentIdentified, user_text, result);
        VBox.setMargin(user_text, new Insets(fontSize / 2, 0, fontSize / 2, 0));

        // one additional layout wrapper
        // maybe so we can fade the whole thing in?
        StackPane pane = new StackPane();
        pane.getChildren().add(vbox);
        pane.setId("main");

        // center all our texts together in the window
        StackPane.setMargin(vbox, new Insets(fontSize / 2, fontSize, fontSize / 2, fontSize));
        StackPane.setAlignment(vbox, Pos.CENTER);

        // drag window from anywhere
        dragStageByNode(primaryStage, pane);

        // fade in animation - looks gross on Linux, may be suffering from a lack of compositing
        pane.setOpacity(0.0);
        FadeTransition hud_in = new FadeTransition(FadeTime, pane);
        hud_in.setFromValue(0.0);
        hud_in.setToValue(1.0);

        // scene setup
        Scene scene = new Scene(pane, totalWidth, totalWidth*sixteenNine);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        scene.setFill(null); // transparent

        // stage setup
        primaryStage.setTitle("HUD");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        hud_in.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
