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
      for (String arg : args) {
        if (arg.contains("-README")) {
          System.out.println("print README");
        } else if (arg.contains("-print")) {
          optPrint = true;
        } else {
            argsList.add(arg);
        }
      }
    }

    if (argsList.size() != 7) {
      System.out.println("Wrong number of arguments entered: Expected (5)");
      System.exit(1);
    }

    for (int i = 1; i <= argsList.size(); i++) {
      switch (i) {
        case 0:
          callerName = argsList.get(i);
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

}