package edu.pdx.cs410J.eschott;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Parses command line arguments
 * Created by Evan on 7/15/2015.
 */
public class CommandLineParser {
  private boolean readMe = false;
  private boolean print = false;
  private boolean search = false;
  private String hostName = null;
  private String portString = null;
  private ArrayList<String> callArgs = new ArrayList<>();

  /**
   * Constructor for CommandLineParser
   * Supports -README, -print, and -search
   * @param args ArrayList of command line args
   */
  public CommandLineParser(ArrayList args){
    Iterator itr = args.iterator();
    while (itr.hasNext()) {
      String arg = (String)itr.next();
      if (arg.startsWith("-")) {
        switch (arg) {
          case "-README":
            readMe = true;
            return; //does not need to process any more arguments
          case "-host":
            try {
              hostName = (String) itr.next();
              if (hostName.startsWith("-")) {
                throw new NoSuchElementException();
              }
              //hostName = System.getProperty("http.port", hostName);
            } catch (NoSuchElementException e) {
              System.err.println("Host name required: -host hostname");
            }
            break;
          case "-port":
            try {
              portString = (String) itr.next();
              if (portString.startsWith("-")) {
                throw new NoSuchElementException();
              }
            } catch (NoSuchElementException e) {
              System.err.println("Port number required: -port port");
            }
            break;
          case "-print":
            print = true;
            break;
          case "-search":
            search = true;
            break;
          default:
            System.err.println("That option is not recognized. Please view README");
            break;
        }
      } else {
        callArgs.add(arg); //Add remaining args to array list
      }

    }
  }

  /**
   * Checks if README option selected
   * @return true if -README flag was set
   */
  public boolean checkReadMeFlag() { return readMe; }

  /**
   * Checks if print option selected
   * @return true if -print flag was set
   */
  public boolean checkPrintFlag() { return print; }

  /**
   * Checks if search option is selected
   * @return true if -search flag is set
   */
  public boolean checkSearchFlag() { return search; }


  public ArrayList<String> getArgs() {
    return callArgs;
  }

  /**
   * Gets host name set by the -host option
   * @return fileName
   */
  public String getHostName() {
    return hostName;
  }

  /**
   * Gets port set by the -port option
   * @return fileName
   */
  public String getPortString() {
    return portString;
  }

}
