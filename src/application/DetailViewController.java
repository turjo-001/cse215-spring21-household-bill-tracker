package application;
import dataClasses.InfoOfBillClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DetailViewController {
  @FXML
  private Label fullNameLabel;

  @FXML
  private Label genderLabel;

  @FXML
  private Label dateOfBilling;

  @FXML
  private Label electricityBillLabel;

  @FXML
  private Label waterBillLabel;

  @FXML
  private Label gasBillLabel;

  @FXML
  private Label totalAmountLabel;

  @FXML
  private Button closeButton;
  
  @FXML
  private ImageView userPicture;

  @FXML
  void handleCloseButton(ActionEvent event) {
    resetUI();
    try{
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TrackerView.fxml"));
      Pane root = (Pane)fxmlLoader.load();
      
      Scene trackerViewScene = new Scene(root);
      Stage primaryStage = (Stage) this.closeButton.getScene().getWindow();
      primaryStage.setScene(trackerViewScene);
      primaryStage.setTitle("Household Bill Tracker Application");
      primaryStage.show();
    }catch (Exception exception)
    {
      exception.getMessage();
    }

  }
  public void resetUI() {
    this.fullNameLabel.setText("");
    this.genderLabel.setText("");
    this.dateOfBilling.setText("");
    this.electricityBillLabel.setText("");
    this.waterBillLabel.setText("");
    this.gasBillLabel.setText("");
    this.waterBillLabel.setText("");
    this.userPicture.setImage(null);
  }
  public void transferObject(InfoOfBillClass infoOfBillClass)
  {
    this.fullNameLabel.setText(infoOfBillClass.getFirstName() + " " + infoOfBillClass.getLastName());
    this.genderLabel.setText(infoOfBillClass.getGender());
    LocalDate localDate = infoOfBillClass.getDateOfBilling();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    String formattedDate = localDate.format(formatter);
    this.dateOfBilling.setText(formattedDate);
    this.electricityBillLabel.setText(String.valueOf(infoOfBillClass.getElectricBill()) + " tk");
    this.waterBillLabel.setText(String.valueOf(infoOfBillClass.getWaterBill()) + " tk");
    this.gasBillLabel.setText(String.valueOf(infoOfBillClass.getGasBill()) + " tk");
    this.totalAmountLabel.setText(String.format("%.2f", infoOfBillClass.getTotalAmount()) + " tk");
    String pictureAddress = "file://" + infoOfBillClass.getPathToPicture();
    Image image = new Image(pictureAddress);
    this.userPicture.setImage(image);
    
  }
}
