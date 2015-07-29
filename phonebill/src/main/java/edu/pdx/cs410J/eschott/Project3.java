package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

      //Assigns attributes from command line parser
      callerNumber = clp.getArgs().get(1);
      calleeNumber = clp.getArgs().get(2);
      startTime = clp.getArgs().get(3).concat(" ").concat(clp.getArgs().get(4).concat(" ").concat(clp.getArgs().get(5)));
      endTime = clp.getArgs().get(6).concat(" ").concat(clp.getArgs().get(7).concat(" ").concat(clp.getArgs().get(8)));

      //validates phone numbers
      try {
        Validator.validatePhoneNumber(callerNumber);
        Validator.validatePhoneNumber(calleeNumber);
      } catch (ParserException e) {
        System.err.println("Invalid phone number format");
        System.exit(1);
      }

      //validates start and end time
      try {
        Validator.validateDate(startTime);
        Validator.validateDate(endTime);
      } catch (ParserException e) {
        System.err.println(e.getMessage());
        System.exit(1);
      }

      //creates phone call and adds to bill
      PhoneCall call = null;
      try {
        call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
      } catch (ParserException e) {
        System.err.println(e.getMessage());
        System.exit(1);
      }
      bill.addPhoneCall(call);

      /**
       * If print option set prints to command line
       */
      if (clp.checkPrintFlag()) {
        System.out.println(call.toString());
      }
    }

    System.exit(1);
  }


  /**
   * Prints README
   */
  private static void printReadMe() { //Todo: update readme
    String readmeText = "README file for PhoneBill v1.4 \n" +
            "Evan Schott \n" +
            "CS410J \n" +
            "Summer 2015 \n" +
            "Project 4 \n" +
            "Project Description: \n" +
            "   This project records phone call details entered on the command line. \n" +
            "usage: java edu.pdx.cs410J.<login-id>.Project3 [options] <args>\n" +
            "args are (in this order):\n" +
            "   customer : Person whose phone bill we're modeling\n" +
            "   callerNumber : Phone number of caller\n" +
            "   calleeNumber : Phone number of person who was called\n" +
            "   startTime : Date and time call began (12-hour time)\n" +
            "   endTime : Date and time call ended (12-hour time)\n" +
            "options are (options may appear in any order):\n" +
            "   -pretty : pretty print the phone bill to a text file\n" +
            "             or standard out (file -)\n" +
            "   -textFile file : Where to read/write the phone bill\n" +
            "   -print : Prints a description of the new phone call\n" +
            "   -README : Prints a README for this project and exits\n" +
            "Date and time should be in the format: mm/dd/yyyy hh:mm a (ex. 1/1/2000 12:23 pm)\n" +
            "Phone numbers should be in the format: ###-###-####";

    System.out.println(readmeText);
  }

}