package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Prints a nicely formatted phone bill
 * Created by Evan on 7/22/2015.
 */
public class PrettyPrinter implements PhoneBillDumper {

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
   * @param bill customer PhoneBill
   */
  @Override
  public void dump(AbstractPhoneBill bill) throws IOException {
    StringBuilder s = new StringBuilder();
    long totalCallTime = 0;
    s.append("Phone bill for customer: ").append(bill.getCustomer()).append("\n\n");
    s.append("Call to:\t")
            .append("Call from:\t")
            .append("Started at:\t")
            .append("Ended at:\t")
            .append("Call duration:")
            .append("\r\n");
    for (Object o : bill.getPhoneCalls()) {
      PhoneCall call = (PhoneCall) o;
      long callDuration = call.getEndTime().getTime() - call.getStartTime().getTime();
      totalCallTime += callDuration;
      s.append(call.getCallee()).append("\t")
              .append(call.getCaller()).append("\t")
              .append(call.getStartTimeString()).append("\t")
              .append(call.getEndTimeString()).append("\t")
              .append(callDuration/60000).append(" minutes\r\n");
    }
    s.append("\n").append("Total calls: ").append(bill.getPhoneCalls().size()).append("\n");
    s.append("Total call time: ").append(totalCallTime / 60000).append(" minutes").append("\n");

    //Set destination and output data
    if (fileName == null) {
      throw new IOException("File name not set"); //should never be thrown
    } else if(fileName.equals("-")) {
      System.out.print(String.valueOf(s));
    } else {
      Writer writer = new FileWriter(fileName);
      writer.write(String.valueOf(s));
      writer.flush();
      writer.close();
    }
  }

}
