package view;

import controller.MainMenuController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainMenuView {

    private Button playButton, editButton, quitButton;
    private ToggleGroup difficultyGroup, mapGroup;
    private final Stage stage;

    /**
     * Initializes the main menu display
     */
    public MainMenuView() {

        VBox vBox1 = createButtonsVBox();
        VBox vBox2 = createMapVBox();
        VBox vBox3 = createDifficultyVBox();

        HBox hBox = createHBox(vBox1, vBox2, vBox3);
        hBox.setPadding(new Insets(100, 100, 100, 100));
        ImageView imageView = new ImageView(new Image("assets/interface/title.png"));

        VBox vBox4 = new VBox();
        vBox4.getChildren().addAll(imageView, hBox);
        vBox4.setAlignment(Pos.CENTER);
        vBox4.setBackground(new Background(new BackgroundImage(new Image("assets/interface/background.png",
                1024, 768, false, false), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, null)));

        stage = new Stage();
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(vBox4, 1024, 768));
        stage.show();

    }

    /**
     * Creates the main menu of horizontal box of vertical boxes
     */
    private HBox createHBox(VBox vBox1, VBox vBox2, VBox vBox3) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(vBox1, vBox2, vBox3);
        return hBox;
    }

    /**
     * Creates the vertical display of main buttons
     */
    private VBox createButtonsVBox() {
        VBox vBox1 = new VBox();
        vBox1.setSpacing(10);
        vBox1.setPadding(new Insets(20, 20, 20, 20));
        vBox1.setAlignment(Pos.CENTER);

        playButton = new Button("Play");
        playButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));
        editButton = new Button("Edit");
        editButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));
        quitButton = new Button("Quit");
        quitButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));

        vBox1.getChildren().addAll(playButton, editButton, quitButton);
        return vBox1;
    }

    /**
     * Creates the vertical display of map choices
     */
    private VBox createMapVBox() {
        VBox vBox2 = new VBox();
        vBox2.setSpacing(10);
        vBox2.setPadding(new Insets(20, 20, 20, 20));
        vBox2.setAlignment(Pos.CENTER_LEFT);

        RadioButton plainsButton = new RadioButton("Plains");
        plainsButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));
        plainsButton.setSelected(true);
        RadioButton waterWorldButton = new RadioButton("Water World");
        waterWorldButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));
        RadioButton savannaButton = new RadioButton("Savanna");
        savannaButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));
        RadioButton shoreLineButton = new RadioButton("Shore Line");
        shoreLineButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));
        RadioButton customMapButton = new RadioButton("Custom Map");
        customMapButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));

        mapGroup = new ToggleGroup();
        plainsButton.setToggleGroup(mapGroup);
        waterWorldButton.setToggleGroup(mapGroup);
        savannaButton.setToggleGroup(mapGroup);
        shoreLineButton.setToggleGroup(mapGroup);
        customMapButton.setToggleGroup(mapGroup);

        vBox2.getChildren().addAll(plainsButton, waterWorldButton, savannaButton, shoreLineButton, customMapButton);
        return vBox2;
    }

    /**
     * Creates the vertical display of difficulty options
     */
    private VBox createDifficultyVBox() {
        VBox vBox3 = new VBox();
        vBox3.setSpacing(10);
        vBox3.setPadding(new Insets(20, 20, 20, 20));
        vBox3.setAlignment(Pos.CENTER_LEFT);

        RadioButton normalButton = new RadioButton("Normal");
        normalButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));
        normalButton.setSelected(true);
        RadioButton hardButton = new RadioButton("Hard");
        hardButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));
        RadioButton expertButton = new RadioButton("Expert");
        expertButton.setFont(Font.font("Verdana", FontWeight.SEMI_BOLD, 24));

        difficultyGroup = new ToggleGroup();
        normalButton.setToggleGroup(difficultyGroup);
        hardButton.setToggleGroup(difficultyGroup);
        expertButton.setToggleGroup(difficultyGroup);

        vBox3.getChildren().addAll(normalButton, hardButton, expertButton);
        return vBox3;
    }

    /**
     * Adds a listener to the play and edit buttons
     */
    public void addListeners(EventHandler<MouseEvent> playListener, EventHandler<MouseEvent> editListener,
                             EventHandler<MouseEvent> quitListener) {
        playButton.setOnMouseClicked(playListener);
        ((MainMenuController.PlayListener)playListener).addMapAndDifficulty(mapGroup, difficultyGroup);
        editButton.setOnMouseClicked(editListener);
        quitButton.setOnMouseClicked(quitListener);
    }

    public Stage getStage() { return stage; }

}
