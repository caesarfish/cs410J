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
    int argIndex = 0;
    String callerName = null;
    String callerNumber = null;
    String calleeNumber = null;
    String startTime = "";
    String endTime = "";


    if (args.length < 1) {
      System.err.println("Missing command line arguments");
    } else {
      optPrint = parseOptions(args, argsList);
    }

    if (argsList.size() != 7) {
      System.out.println("Wrong number of arguments entered: Expected (5)");
      System.exit(1);
    }

    for (int i = 1; i <= argsList.size(); i++) {
      switch (i) {
        case 0:
          //callerName = argsList.get(i);
          break;
        case 1:
          callerNumber = argsList.get(i);
          break;
        case 2:
          calleeNumber = argsList.get(i);
          break;
        case 3:
          startTime = argsList.get(i);
          break;
        case 4:
          startTime = startTime + " " + argsList.get(i);
          break;
        case 5:
          endTime = argsList.get(i);
          break;
        case 6:
          endTime = endTime + " " + argsList.get(i);
      }
    }

    PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
    if (optPrint) { System.out.println(call.toString()); }





    System.exit(1);
  }

  /**
   * Parses the command line args for "-" options
   * @param args array of command line ars
   * @param argsList list to write args to
   * @return returns print option boolean
   */
  private static boolean parseOptions(String[] args, List<String> argsList) {
    boolean optPrint = false;
    String readmeText = "PhoneBill v1.0 \n" +
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

    for (String arg : args) {
      if (arg.contains("-README")) {
        System.out.println(readmeText);
        System.exit(1);
      } else if (arg.contains("-print")) {
        optPrint = true;
      } else {
          argsList.add(arg);
      }
    }
    return optPrint;
  }

}