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

  private String fileName = null;

   /**
   * Sets the file to be written to
   * @param file name of file as String
   */
  public void setFile(String file) {
    fileName = file;
  }

  /**
   * Dumps a phone bill to some destination.
   *
   * @param bill the phone bill to be printed
    *@throws IOException
   */
  @SuppressWarnings("unchecked")
  @Override
  public void dump(AbstractPhoneBill bill) throws IOException {
    if (fileName == null) {
      throw new IOException("File name not set"); //should never be thrown
    }
    Writer writer = new FileWriter(fileName);
    StringBuilder s = new StringBuilder(bill.getCustomer());
    s.append("\n");
    for (Object o : bill.getPhoneCalls()) {
      PhoneCall call = (PhoneCall) o;
      s.append(call.getCaller()).append(";").append(call.getCallee()).append(";").append(call.getStartTimeString()).append(";").append(call.getEndTimeString());
      s.append("\n");
    }
    writer.write(String.valueOf(s));
    writer.flush();
    writer.close();
  }
}
