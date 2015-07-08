package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneBill;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public static void main(String[] args) {
    Class c = AbstractPhoneBill.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    List<String> argsList = new ArrayList<>();
    boolean optPrint = false;
    boolean optReadMe = false;
    String callerName = null;
    String callerNumber = null;
    String calleeNumber = null;
    String startTime = "";
    String endTime = "";

    /**
     * Parse Command line args
     */
    if (args.length < 1) {
      System.err.println("Missing command line arguments");
    } else {
      if(parseReadme(args)){
        System.exit(1);
      }
      optPrint = parsePrint(args);
    }


    /**
     * Process Command line args
     */
    /*if (args.length != 7) {
      System.out.println("Wrong number of arguments entered: Expected (5)");
      System.exit(1);
    }*/

    for (int i = optPrint? 0:1; i <= args.length; i++) {
      switch (i) {
        case 0:
          //callerName = args[i];
          break;
        case 1:
          callerNumber = args[i];
          break;
        case 2:
          calleeNumber = args[i];
          break;
        case 3:
          startTime = args[i];
          break;
        case 4:
          startTime = startTime + " " + args[i];
          break;
        case 5:
          endTime = args[i];
          break;
        case 6:
          endTime = endTime + " " + args[i];
      }
    }

    PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
    if (optPrint) { System.out.println(call.toString()); }

    System.exit(1);
  }

  /**
   * Parses command line args for readme flag
   * Prints README if set
   * @param args command line args
   * @return true if readme flag set
   */
  private static boolean parseReadme(String[] args) {
    String readmeText = "README file for PhoneBill v1.0 \n" +
            "Evan Schott \n" +
            "CS410J \n" +
            "Summer 2015 \n" +
            "Project 1 \n" +
            "Project Description: \n" +
            "   This project records phone call details entered on the command line. \n" +
            "usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
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

    for (String arg : args)
      if (arg.contains("-README")) {
        System.out.println(readmeText);
        return true;
      }

    return false;
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