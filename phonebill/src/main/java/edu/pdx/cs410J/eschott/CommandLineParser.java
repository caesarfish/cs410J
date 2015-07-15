package edu.pdx.cs410J.eschott;

import java.util.ArrayList;

/**
 * Parses command line arguments
 * Created by Evan on 7/15/2015.
 */
public class CommandLineParser {
  private boolean readMe = false;
  private boolean print = false;
  private String fileName = null;
  ArrayList callArgs = new ArrayList();

  public CommandLineParser(ArrayList args){
    args.forEach(arg -> {
      if (arg == "-README") {
        readMe = true;
        //TODO: add logic to stop processing remaining args
      }
      if (arg == "-print") {
        print = true;
      }
    });
  }

  public boolean checkReadMeFlag() { return readMe; }

  public boolean checkPrintFlag() { return print; }
}
