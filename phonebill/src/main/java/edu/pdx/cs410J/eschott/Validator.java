package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.ParserException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provides data validation methods for PhoneBill project
 * Created by Evan on 7/22/2015.
 */
public class Validator {

  /**
   * Method uses a regex to validate phone number format
   * @param phoneNumber should be in format ###-###-####
  **/
  public static void validatePhoneNumber(String phoneNumber) throws ParserException{
    if (!phoneNumber.matches("^[0-9]{3}-[0-9]{3}-[0-9]{4}$")) {
      throw new ParserException("Invalid phone number format");
    }
  }

  public static void validateDate(String dateToValidate) throws ParserException {
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    dateFormat.setLenient(false);
    try {
      Date date = dateFormat.parse(dateToValidate);
    } catch (ParseException e) {
      throw new ParserException("Invalid date-time format");
    }
  }

}
