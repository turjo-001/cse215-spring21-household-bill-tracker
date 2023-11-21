package exceptionsPackage;

import java.time.LocalDate;

public class InvalidDataChecker {
  public static boolean isValidData(String firstName, String lastName, String gender, LocalDate dateOfBilling,
                                    String electricBill, String waterBill, String gasBill, String pathToPicture) throws Exception {
    if (firstName == null || firstName.length() < 3)
      throw new Exception("First Name cannot be less than three characters.");
    else {
      for (int i=0; i<firstName.length(); i++) {
        if (Character.isLetter(firstName.charAt(i)) || Character.isSpaceChar(firstName.charAt(i)))
          continue;
        else
          throw new Exception("First name cannot contain numbers or special characters.");
      }
    }
    if (lastName == null || lastName.length() < 3)
      throw new Exception("Last Name cannot be less than three characters.");
    else {
      for (int i=0; i<lastName.length(); i++) {
        if (Character.isLetter(lastName.charAt(i)) || Character.isSpaceChar(lastName.charAt(i)))
          continue;
        else
          throw new Exception("Last name cannot contain numbers or special characters.");
      }
    }
    if (gender == null)
      throw new Exception("Gender selection empty.");
    if (dateOfBilling == null)
      throw new Exception("Date of billing unspecified.");
    if (!isStringADouble(electricBill))
      throw new Exception("Input Electricity bill not valid.");
    if (!isStringADouble(waterBill))
      throw new Exception("Input Water bill not valid.");
    if (!isStringADouble(gasBill))
      throw new Exception("Input Gas bill not valid.");
    if (pathToPicture == null)
      throw new Exception("Picture not selected.");
    return true;
  }

  public static boolean isStringADouble(String doubleString) {
    try {
      double number = Double.parseDouble(doubleString);
      if (number < 0)
        return false;
    } catch (Exception exception) {
      return false;
    }
    return true;
  }
}
