package dataClasses;

import java.io.Serializable;
import java.time.LocalDate;

public class InfoOfBillClass implements Serializable {
  private String firstName;
  private String lastName;
  private String gender;
  private LocalDate dateOfBilling;
  private double electricBill;
  private double waterBill;
  private double gasBill;
  private String pathToPicture;
  private double totalAmount;

  public InfoOfBillClass(String firstName, String lastName, String gender, LocalDate dateOfBilling,
                         double electricBill, double waterBill, double gasBill, String pathToPicture) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.dateOfBilling = dateOfBilling;
    this.electricBill = electricBill;
    this.waterBill = waterBill;
    this.gasBill = gasBill;
    this.pathToPicture = pathToPicture;
    this.totalAmount = electricBill + waterBill + gasBill;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getGender() {
    return gender;
  }

  public LocalDate getDateOfBilling() {
    return dateOfBilling;
  }

  public double getElectricBill() {
    return electricBill;
  }

  public double getWaterBill() {
    return waterBill;
  }

  public double getGasBill() {
    return gasBill;
  }

  public String getPathToPicture() {
    return pathToPicture;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  @Override
  public String toString() {
    return String.format("%s %s ; %s, %s",
      this.firstName, this.lastName, this.dateOfBilling.getMonth(), this.dateOfBilling.getYear());
  }
}
