package application;

import dataClasses.InfoOfBillClass;
import exceptionsPackage.InvalidDataChecker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utilitiesPackage.Serializer;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class TrackerViewController {
  
  @FXML
  private TextField firstNameTextField;
  
  @FXML
  private TextField lastNameTextField;
  
  @FXML
  private ComboBox<String> genderComboBox;
  
  @FXML
  private DatePicker dateOfBillDatePicker;
  
  @FXML
  private TextField electricityBillTextField;
  
  @FXML
  private TextField waterBillTextField;
  
  @FXML
  private TextField gasBillTextField;
  
  @FXML
  private ImageView userImageView;
  
  @FXML
  private Button choosePictureButton;
  
  @FXML
  private ListView<InfoOfBillClass> billsListView;
  
  @FXML
  private Button showDetailsButton;
  
  @FXML
  private Button editButton;
  
  @FXML
  private Button deleteButton;
  
  @FXML
  private Button saveButton;
  
  @FXML
  private Button clearFormButton;
  
  private String pathname;
  
  
  
  private String pathToPictureFile = null;
  
  private ArrayList<InfoOfBillClass> listOfBills = null;
  
  private ObservableList<InfoOfBillClass> observableListOfBills = null;
  
  private int indexNumber = -1;
  
  public void resetUI() {
    this.firstNameTextField.setText("");
    this.lastNameTextField.setText("");
    this.genderComboBox.setValue(null);
    this.dateOfBillDatePicker.setValue(null);
    this.electricityBillTextField.setText("");
    this.waterBillTextField.setText("");
    this.gasBillTextField.setText("");
    this.pathToPictureFile = null;
    this.userImageView.setImage(null);
  }
  
  @FXML
  void handleChoosePictureButton(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters()
      .add(
        new FileChooser.ExtensionFilter("JPG, JPEG images",
          "*.jpg",
          "*.jpeg",
          "*.JPG",
          ".JPEG",
          "Portable Network Graphics (PNG)",
          "png")
      );
    Stage primaryStage = (Stage) this.choosePictureButton.getScene().getWindow();
    File chosenFile = fileChooser.showOpenDialog(primaryStage);
    
    if (chosenFile != null) {
      this.pathToPictureFile = chosenFile.toURI().getPath();
      String pictureAddress = "file://" + pathToPictureFile;
      uploadPicture(pictureAddress);
      
    }
  }
  public void uploadPicture(String pictureAddress)
  {
    Image givenImage = new Image(pictureAddress);
    this.userImageView.setImage(givenImage);
  }
  
  @FXML
  void handleSaveButton(ActionEvent event) {
    String firstName = this.firstNameTextField.getText();
    String lastName = this.lastNameTextField.getText();
    String gender = this.genderComboBox.getSelectionModel().getSelectedItem();
    LocalDate dateOfBilling = this.dateOfBillDatePicker.getValue();
    String electricBillText = this.electricityBillTextField.getText();
    String waterBillText = this.waterBillTextField.getText();
    String gasBillText = this.gasBillTextField.getText();
    
    InfoOfBillClass billDataObj = null;
    
    try {
      if (InvalidDataChecker.isValidData(firstName, lastName, gender,
        dateOfBilling, electricBillText, waterBillText, gasBillText, this.pathToPictureFile)) {
        billDataObj = new InfoOfBillClass(firstName, lastName, gender, dateOfBilling,
          Double.parseDouble(electricBillText), Double.parseDouble(waterBillText), Double.parseDouble(gasBillText),
          this.pathToPictureFile);
        if(this.indexNumber != -1)
        {
          this.listOfBills.set(indexNumber,billDataObj);
          this.observableListOfBills.set(indexNumber,billDataObj);
        }
        else
        {
          this.listOfBills.add(billDataObj);
          this.observableListOfBills.add(billDataObj);
        }
      }
      
      
      boolean serializationValidity = Serializer.serialize(pathname, this.listOfBills);
      if (!serializationValidity)
        throw new Exception("Failed to save file.");
      
      this.resetUI();
      billsListView.getSelectionModel().select(-1);
    } catch (Exception exception) {
      Stage stage = (Stage) this.saveButton.getScene().getWindow();
      ErrorViewUtility.showErrorMessageDialogueBox(stage, exception.getMessage());
    }

  }
  
  @FXML
  void handleClearFormButton(ActionEvent event) {
    resetUI();
  }
  
  @FXML
  void handleDeleteButton(ActionEvent event) {
  
    this.indexNumber = billsListView.getSelectionModel().getSelectedIndex();
    
    try{
      if(this.indexNumber != -1) {
       for(int i = 0; i < listOfBills.size(); i++)
       {
         if(i == this.indexNumber)
         {
           listOfBills.remove(i);
           observableListOfBills.remove(i);
           Serializer.serialize(this.pathname,listOfBills);
           billsListView.refresh();
           this.indexNumber = - 1;
           billsListView.getSelectionModel().select(this.indexNumber);
           break;
         }
       }
      }
      else
      {
        throw new Exception("Select Something From The List First.");
      }
      
    }catch (Exception exception)
    {
      Stage stage = (Stage) this.saveButton.getScene().getWindow();
      ErrorViewUtility.showErrorMessageDialogueBox(stage, exception.getMessage());
    }
  
  }
  
  @FXML
  void handleEditButton(ActionEvent event) {
    this.indexNumber = this.billsListView.getSelectionModel().getSelectedIndex();
    try{
      if(indexNumber != -1)
      {
        InfoOfBillClass infoOfBillClass = this.billsListView.getItems().get(indexNumber);
        String firstName = infoOfBillClass.getFirstName();
        String lastName = infoOfBillClass.getLastName();
        String gender = infoOfBillClass.getGender();
        LocalDate localDate = infoOfBillClass.getDateOfBilling();
        double elecBill = infoOfBillClass.getElectricBill();
        double waterBill = infoOfBillClass.getWaterBill();
        double gasBill = infoOfBillClass.getGasBill();
        this.pathToPictureFile = infoOfBillClass.getPathToPicture();
        
        editOrUpdateUi( firstName,  lastName,gender,  localDate, elecBill,
                     waterBill, gasBill);
      }
      else
      {
        throw new Exception("Select Something From The List First.");
      }
    }catch (Exception exception)
    {
      Stage stage = (Stage) this.saveButton.getScene().getWindow();
      ErrorViewUtility.showErrorMessageDialogueBox(stage, exception.getMessage());
    }
    
    
  
  }
  public void editOrUpdateUi(String firstName, String lastName, String gender, LocalDate localDate, double elecBill,
                             double waterBill, double gasBill)
  {
    this.firstNameTextField.setText(firstName);
    this.lastNameTextField.setText(lastName);
    this.genderComboBox.setValue(gender);
    this.dateOfBillDatePicker.setValue(localDate);
    this.electricityBillTextField.setText(String.valueOf(elecBill));
    this.waterBillTextField.setText(String.valueOf(waterBill));
    this.gasBillTextField.setText(String.valueOf(gasBill));
    String pictureAddress = "file://" + this.pathToPictureFile;
    uploadPicture(pictureAddress);
    
  }
  
  @FXML
  void handleShowDetailsButton(ActionEvent event) {
    this.indexNumber = billsListView.getSelectionModel().getSelectedIndex();
    if(indexNumber != -1)
    {
      InfoOfBillClass infoOfBillClass = this.billsListView.getItems().get(this.indexNumber);
      try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DetailView.fxml"));
        Pane root = (Pane)fxmlLoader.load();
        DetailViewController detailViewController = fxmlLoader.getController();
        detailViewController.transferObject(infoOfBillClass);
        Scene detailViewScene = new Scene(root);
        Stage primaryStage = (Stage) this.showDetailsButton.getScene().getWindow();
        primaryStage.setScene(detailViewScene);
        primaryStage.setTitle("Detailed Information Window");
        primaryStage.show();
      }catch (Exception exception)
      {
        Stage stage = (Stage) this.saveButton.getScene().getWindow();
        ErrorViewUtility.showErrorMessageDialogueBox(stage, exception.getMessage());
      }
    }else
    {
      Stage stage = (Stage) this.saveButton.getScene().getWindow();
      ErrorViewUtility.showErrorMessageDialogueBox(stage, "Select Something From The List First.");
    }
  
  }
  
  public void initialize() {
    this.pathname = "./bill_database.bin";
    this.listOfBills = Serializer.deserialize(pathname);
    if (this.listOfBills == null)
      this.listOfBills = new ArrayList<>();
    this.observableListOfBills = FXCollections.observableArrayList(listOfBills);
    this.billsListView.setItems(observableListOfBills);
    
    
    //initializing gender combo box
    ArrayList<String> genderOptions = new ArrayList<>();
    genderOptions.add("Male");
    genderOptions.add("Female");
    genderOptions.add("Others");
    ObservableList<String> genderObservableArrayList = FXCollections.observableArrayList(genderOptions);
    this.genderComboBox.setItems(genderObservableArrayList);
  }
}
