import controller.MainMenuController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.MainMenuView;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates main menu view and main menu controller objects
     */
    @Override
    public void start(Stage primaryStage) {

        MainMenuController mainMenuController = MainMenuController.getInstance();

    }

}
