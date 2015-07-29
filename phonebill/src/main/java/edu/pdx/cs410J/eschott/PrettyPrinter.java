package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;
import sun.nio.ch.IOStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Prints a nicely formatted phone bill
 * Created by Evan on 7/22/2015.
 */
public class PrettyPrinter implements PhoneBillDumper {

    public void dump(AbstractPhoneBill bill) throws IOException {

    }

    /**
    * Dumps a phone bill to some destination.
    *
    * @param bill customer PhoneBill
    */
    public void dump(AbstractPhoneBill bill, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        StringBuilder s = new StringBuilder();
        long totalCallTime = 0;
        String separator = System.lineSeparator();
        pw.println("Phone bill for customer: " + bill.getCustomer());
        /*
        s.append("Phone bill for customer: ").append(bill.getCustomer()).append(separator).append(separator);
        s.append("Call to:\t")
                .append("Call from:\t")
                .append("Started at:\t")
                .append("Ended at:\t")
                .append("Call duration:")
                .append(separator);
        for (Object o : bill.getPhoneCalls()) {
          PhoneCall call = (PhoneCall) o;
          long callDuration = call.getEndTime().getTime() - call.getStartTime().getTime();
          totalCallTime += callDuration;
          s.append(call.getCallee()).append("\t")
                  .append(call.getCaller()).append("\t")
                  .append(call.getStartTimeString()).append("\t")
                  .append(call.getEndTimeString()).append("\t")
                  .append(callDuration / 60000).append(" minutes").append(separator);
        }
        s.append(separator).append("\n").append("Total calls: ").append(bill.getPhoneCalls().size()).append(separator);
        s.append("Total call time: ").append(totalCallTime / 60000).append(" minutes").append("\n");*/



        pw.flush();

    //Set destination and output data
    /*if (fileName == null) {
      throw new IOException("File name not set"); //should never be thrown
    } else if(fileName.equals("-")) {
      System.out.print(String.valueOf(s));
    } else {
      Writer writer = new FileWriter(fileName);
      writer.write(String.valueOf(s));
      writer.flush();
      writer.close();
    }*/
  }

}
