package org.teton_landis.jake.hud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.web.*;
import javafx.stage.Stage;
import javafx.stage.*;

public class Main extends Application {

    static final double fontSize = 70;
    static final double totalWidth = 1200;
    static final double sixteenNine = (9.0 / 16.0);
    static final String[] Copy = {"Ok tv. play", "Breaking Bad", "season", "two",
            "episode", "four", "please."};

    private Text textWithStyle(String text, String... styles) {
        Text t = new Text(text);
        t.getStyleClass().addAll(styles);
        return t;
    }

    private <T extends Node> T nodeWithStyle(Class<T> type, String... styles) throws IllegalAccessException, InstantiationException {
        T thing = type.newInstance();
        thing.getStyleClass().addAll(styles);
        return thing;
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
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Media Hud");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

        // stage setup
        primaryStage.setTitle("HUD");
        primaryStage.initStyle(StageStyle.TRANSPARENT);


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

        // top -- intent identified
        StyledTextFlow intentIdentified = new StyledTextFlow(
                str("♦ Intent:"),
                str("query episode → play" , "action")
        );
        intentIdentified.getStyleClass().addAll("small", "action");


        // bottom -- result
        StyledTextFlow result = new StyledTextFlow(
                str("found"),
                str("Breaking Bad - S02E04 - Thirty-Eight Snub", "entity")
        );
        result.getStyleClass().addAll("small", "result");

        VBox vbox = new VBox();
        vbox.getChildren().addAll(intentIdentified, user_text, result);
        VBox.setMargin(user_text, new Insets(fontSize / 2, 0, fontSize / 2, 0));

        StackPane pane = new StackPane();
        pane.getChildren().add(vbox);
        pane.setId("main");

        StackPane.setAlignment(vbox, Pos.CENTER);
        StackPane.setMargin(vbox, new Insets(fontSize / 2, fontSize, fontSize / 2, fontSize));

        Scene scene = new Scene(pane, totalWidth, totalWidth*sixteenNine);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        scene.setFill(null);

        // Scene scene = new Scene(new Browser(), 750, 500, Color.web("#222"));
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Browser extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    public Browser() {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
        webEngine.load("http://jake.teton-landis.org");
        //add the web view to the scene
        getChildren().add(browser);

    }
    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }

    @Override protected double computePrefWidth(double height) {
        return 750;
    }

    @Override protected double computePrefHeight(double width) {
        return 500;
    }
}

