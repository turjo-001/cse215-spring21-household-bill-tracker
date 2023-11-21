package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorViewUtility {

  public static void showErrorMessageDialogueBox(Stage primaryStage, String errorMessage) {

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(ErrorViewUtility.class.getResource("ErrorView.fxml"));
      Pane root = (Pane) fxmlLoader.load();
      ErrorViewController errorViewController = fxmlLoader.getController();
      errorViewController.setErrorMessageLabel(errorMessage);

      Scene scene = new Scene(root);
      Stage stage = new Stage();

      stage.setScene(scene);
      stage.initOwner(primaryStage);
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setTitle("Error occurred");
      stage.showAndWait();
    } catch (Exception exception) {

      exception.printStackTrace();
    }

  }
}
