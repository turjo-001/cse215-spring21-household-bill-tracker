package utilitiesPackage;

import dataClasses.InfoOfBillClass;

import java.io.*;
import java.util.ArrayList;

public class Serializer {
  public static boolean serialize(String pathToFile, ArrayList<InfoOfBillClass> listOfBills) {
    File file = new File(pathToFile);
    try (FileOutputStream fileOutputStream = new FileOutputStream(pathToFile);
         ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream)) {
      objectOutputStream.writeObject(listOfBills);
      return true;
    } catch (Exception exception) {
      return false;
    }
  }

  public static ArrayList<InfoOfBillClass> deserialize(String pathToFile) {
    File file = new File(pathToFile);
    ArrayList<InfoOfBillClass> listOfBills = null;
    try (FileInputStream fileInputStream = new FileInputStream(file);
         ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
      listOfBills = (ArrayList<InfoOfBillClass>) objectInputStream.readObject();
    } catch (Exception exception) {
      System.err.println(exception.getMessage());
    }
    return listOfBills;
  }
}
