package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project3 {

  public static void main(String[] args) {
    ArrayList<String> argList = new ArrayList<>();
    String callerName;
    String callerNumber;
    String calleeNumber;
    String startTime;
    String endTime;

    if (args.length < 1) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    argList.addAll(Arrays.asList(args));

    CommandLineParser clp = new CommandLineParser(argList);

    if(clp.checkReadMeFlag()){
      printReadMe();
      System.exit(1); //prints README and exits
    } else {
      if (clp.getArgs().size() != 9) {
        System.err.println("Wrong number of arguments entered. View README for usage.");
        System.exit(1);
      }

      callerName = clp.getArgs().get(0);
      PhoneBill bill = new PhoneBill(callerName);

      if (clp.checkFileFlag()) {
        TextParser tp = new TextParser();
        tp.setFile(clp.getFileName());
        try {
          PhoneBill parsedBill = (PhoneBill) tp.parse();
          if (parsedBill.getCustomer().equals(bill.getCustomer())){
            bill = parsedBill;
          } else {
            if (!parsedBill.getCustomer().equals("")) {
              System.err.println("Customer name provided does not match phone bill record on file");
              System.exit(1); //Will not continue in this case
            }
          }
        } catch (ParserException | NullPointerException e) {
          if (!e.getMessage().contains("Empty file")) {
            System.err.println(e.getMessage());
            System.exit(1);
          }
        }
      }

      callerNumber = clp.getArgs().get(1);
      calleeNumber = clp.getArgs().get(2);
      startTime = clp.getArgs().get(3).concat(" ").concat(clp.getArgs().get(4).concat(" ").concat(clp.getArgs().get(5)));
      endTime = clp.getArgs().get(6).concat(" ").concat(clp.getArgs().get(7).concat(" ").concat(clp.getArgs().get(8)));

      if(!validatePhoneNumber(callerNumber) || !validatePhoneNumber(calleeNumber)) {
        System.err.println("Phone number is not valid: from " + callerNumber + " to " + calleeNumber + ". Should be in format ###-###-####");
        System.exit(1);
      }

      //Date should validate in PhoneCall object.
      //TODO: consider validating before passing to PhoneCall constructor?
      /*if(!validateDateTime(startTime) || !validateDateTime(endTime)) {
        System.err.println("Invalid date-time format: from " + startTime + " to " + endTime +
                ". Should be in format MM/DD/YYYY HH:MM");
        System.exit(1);
      }*/

      PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
      bill.addPhoneCall(call);


      if (clp.checkPrintFlag()) {
        if (clp.checkFileFlag()) {
          TextDumper td = new TextDumper();
          td.setFile(clp.getFileName());
          try {
            td.dump(bill);
          } catch (IOException e) {
            e.printStackTrace();
          }
        } else {
          System.out.println(call.toString());
        }
      }

    }

    System.exit(1);
  }

  /**
   * Method uses a regex to validate phone number format
   * @param phoneNumber should be in format ###-###-####
   * @return true if phoneNumber matches regex
   */
  public static boolean validatePhoneNumber(String phoneNumber){
    return phoneNumber.matches("^[0-9]{3}-[0-9]{3}-[0-9]{4}$");
  }

  /** TODO: remove block
   * Method uses a regex to validate date format
   * @param dateToValidate should be in format MM/DD/YYYY HH:MM
   * @return true if date matches regex
   *//*
  public static boolean validateDateTime(String dateToValidate) {
    return dateToValidate.matches("^(1[0-2]|0?[1-9])/(3[01]|[12][0-9]|0?[1-9])/([0-9]{4}) ([01]?[0-9]|2[0-3]):([0-5][0-9])$");
  }*/



  /**
   * Prints README
   */
  private static void printReadMe() {
    String readmeText = "README file for PhoneBill v1.1 \n" +
            "Evan Schott \n" +
            "CS410J \n" +
            "Summer 2015 \n" +
            "Project 2 \n" +
            "Project Description: \n" +
            "   This project records phone call details entered on the command line. \n" +
            "usage: java edu.pdx.cs410J.<login-id>.Project3 [options] <args>\n" +
            "args are (in this order):\n" +
            "   customer : Person whose phone bill we’re modeling\n" +
            "   callerNumber : Phone number of caller\n" +
            "   calleeNumber : Phone number of person who was called\n" +
            "   startTime : Date and time call began (24-hour time)\n" +
            "   endTime : Date and time call ended (24-hour time)\n" +
            "options are (options may appear in any order):\n" +
            "   -textFile file : Where to read/write the phone bill\n" +
            "   -print : Prints a description of the new phone call\n" +
            "   -README : Prints a README for this project and exits\n" +
            "Date and time should be in the format: mm/dd/yyyy hh:mm\n" +
            "Phone numbers should be in the format: ###-###-####";

    System.out.println(readmeText);
  }

}