package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project2 {

  public static void main(String[] args) {
    ArrayList<String> argList = new ArrayList<>();
    String callerName;
    String callerNumber;
    String calleeNumber;
    String startTime;
    String endTime;

    /**
     * Parse Command line args
     */
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
      if (clp.getArgs().size() != 7) { //TODO: check if assert needed here
        System.err.println("Wrong number of arguments entered. View README for usage.");
        System.exit(1);
      }

      callerName = clp.getArgs().get(0);
      PhoneBill bill = new PhoneBill(callerName);

      if (clp.checkFileFlag()) {
        TextParser tp = new TextParser();
        tp.setFile(clp.returnFileName());
        try {
          PhoneBill parsedBill = (PhoneBill) tp.parse();
          if ((parsedBill.getCustomer() == null) || !parsedBill.getCustomer().equals(bill.getCustomer())){
            System.err.println("Customer name provided does not match phone bill record on file");
          } else {
            bill = parsedBill;
          }
        } catch (ParserException | NullPointerException e) {
          System.err.println(e.getMessage());
        }
      }

      callerNumber = clp.getArgs().get(1);
      calleeNumber = clp.getArgs().get(2);
      startTime = clp.getArgs().get(3).concat(" ").concat(clp.getArgs().get(4));
      endTime = clp.getArgs().get(5).concat(" ").concat(clp.getArgs().get(6));
      PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
      bill.addPhoneCall(call);


      if (clp.checkPrintFlag()) {
        if (clp.checkFileFlag()) {
          TextDumper td = new TextDumper();
          td.setFile(clp.returnFileName());
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



    /**
     * Process Command line args
     */
    /*if (!(optPrint && args.length == 8) && !(!optPrint && args.length == 7)) {
      System.err.println("Wrong number of arguments entered: Expected (5) but was " + args.length);
      System.exit(1);
    }
    for (int i = optPrint?1:0; i <= args.length; i++) {
      if (callerName == null) callerName = args[i];
      else if (callerNumber == null) callerNumber = args[i];
      else if (calleeNumber == null) calleeNumber = args[i];
      else if (startTime == null) {
        startTime = args[i] + " " + args[i+1];
        i++;
      }
      else if (endTime == null) {
          endTime = args[i] + " " + args[i+1];
      }
    }*/

    /**
     * Creates PhoneCall object and prints if requested
     */
    /*try {
        PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
        if (optPrint) { System.out.println(call.toString()); }
    } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }*/

    System.exit(1);
  }



  /**
   * Prints README
   */
  private static void printReadMe() {
    String readmeText = "README file for PhoneBill v1.0 \n" +
            "Evan Schott \n" +
            "CS410J \n" +
            "Summer 2015 \n" +
            "Project 2 \n" +
            "Project Description: \n" +
            "   This project records phone call details entered on the command line. \n" +
            "usage: java edu.pdx.cs410J.<login-id>.Project2 [options] <args>\n" +
            "args are (in this order):\n" +
            "   customer : Person whose phone bill we’re modeling\n" +
            "   callerNumber : Phone number of caller\n" +
            "   calleeNumber : Phone number of person who was called\n" +
            "   startTime : Date and time call began (24-hour time)\n" +
            "   endTime : Date and time call ended (24-hour time)\n" +
            "options are (options may appear in any order):\n" +
            "   -print : Prints a description of the new phone call\n" +
            "   -README : Prints a README for this project and exits\n" +
            "Date and time should be in the format: mm/dd/yyyy hh:mm";

    System.out.println(readmeText);
  }

  /**
   * Parses the command line args for print option
   * @param args array of command line args
   * @return returns true if print flag set
   */
  private static boolean parsePrint(String[] args) {

    for (String arg : args) {
      if (arg.contains("-print")) {
        return true;
      }
    }
    return false;
  }

}