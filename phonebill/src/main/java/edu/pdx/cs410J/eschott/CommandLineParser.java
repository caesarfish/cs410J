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
  private String fileName = null;
  private ArrayList<String> callArgs = new ArrayList<>();

  public CommandLineParser(ArrayList args){
    Iterator itr = args.iterator();
    while (itr.hasNext()) {
      String arg = (String)itr.next();
      if (arg.startsWith("-")) {
        if (arg.equals("-README")) {
          readMe = true;
          return; //does not need to process any more arguments
        }
        if (arg.equals("-print")) {
          print = true;
        }
        if (arg.equals("-textFile")) {
          try {
            fileName = (String)itr.next();
            itr.remove();
          } catch (NoSuchElementException e) {
            System.out.println("textFile option requires file name: -textFile file");
          }
        }
      } else {
        callArgs.add(arg); //Add remaining args to array list
      }

    }
  }

  public boolean checkReadMeFlag() { return readMe; }

  public boolean checkPrintFlag() { return print; }

  public boolean checkFileFlag() { return fileName == null; }

  public ArrayList<String> getArgs() {
    return callArgs;
  }

}
