package edu.pdx.cs410J.eschott.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import edu.pdx.cs410J.ParserException;

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
  public static boolean validatePhoneNumber(String phoneNumber) throws ParserException{
    if (!phoneNumber.matches("^[0-9]{3}-[0-9]{3}-[0-9]{4}$")) {
      throw new ParserException("Invalid phone number format");
    }
    return true;
  }

  /**
   * Method for date validation
   * @param dateToValidate date passed in as string
   * @return true if passes validation
   * @throws ParserException
   */
  public static boolean validateDate(String dateToValidate) throws ParserException {
    DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yy hh:mm a");
    try {
      Date date = dateFormat.parseStrict(dateToValidate);
    } catch (IllegalArgumentException e) {
      throw new ParserException("Invalid date-time format");
    }
    return true;
  }

}
