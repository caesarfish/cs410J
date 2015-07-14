package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by Evan on 7/13/2015.
 * For dumping phone bill records to text file
 */
public class TextDumper implements PhoneBillDumper {

  public String fileName = null;

  public boolean fileExists(String fileName){
    File f = new File(fileName);
    if(f.exists() && !f.isDirectory()) {
      return true;
    } else {
      return false;
    }
  }

  public void setFile(String file) {
    fileName = file;
  }

  /**
   * Dumps a phone bill to some destination.
   *
   * @param bill the phone bill to be printed
   */
  @Override
  public void dump(AbstractPhoneBill bill) throws IOException {
    if (fileName == null) {
      throw new IOException("File name is invalid");
    }
    Writer writer = new FileWriter(fileName);
    writer.write(bill.toString());
    writer.flush();
    writer.close();
  }
}
