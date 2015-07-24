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
  private String textFileName = null;
  private String prettyFileName = null;
  private ArrayList<String> callArgs = new ArrayList<>();

  /**
   * Constructor for CommandLineParser
   * Supports -README, -print, and -textFile file
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
          case "-print":
            print = true;
            break;
          case "-pretty":
            try {
              prettyFileName = (String) itr.next();
              if (prettyFileName.startsWith("-") && prettyFileName.length() > 1) {
                throw new NoSuchElementException(); //either no filename or no more args
              }
            } catch (NoSuchElementException e) {
              System.err.println("pretty print option requires file name or \"-\"");
            }
            break;
          case "-textFile":
            try {
              textFileName = (String) itr.next();
              if (textFileName.startsWith("-")) {
                throw new NoSuchElementException(); //either no filename or no more args
              }
            } catch (NoSuchElementException e) {
              System.err.println("textFile option requires file name: -textFile file");
            }
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
   * Checks if textFile option selected
   * @return true if -textFile flag was set with file name
   */
  public boolean checkTextFileFlag() { return textFileName != null; }

  /**
   * Checks if textFile option selected
   * @return true if -textFile flag was set with file name
   */
  public boolean checkPrettyFileFlag() { return prettyFileName != null; }

  /**
   * Gets array list of non-option flag args
   * @return array list of args
   */
  public ArrayList<String> getArgs() {
    return callArgs;
  }

  /**
   * Gets file name set by the -textFile option
   * @return fileName
   */
  public String getTextFileName() {
    return textFileName;
  }

  /**
   * Gets file name set by the -textFile option
   * @return fileName
   */
  public String getPrettyFileName() {
    return prettyFileName;
  }

}
