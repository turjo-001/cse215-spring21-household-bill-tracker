package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorViewController {

  @FXML
  private Label errorMessageLabel;

  @FXML
  private Button closeButton;

  @FXML
  void handleCloseButton(ActionEvent event) {

    Stage stage = (Stage) this.closeButton.getScene().getWindow();
    stage.close();
  }

  public void setErrorMessageLabel(String errorMessage){

    this.errorMessageLabel.setText(errorMessage);
  }
}
