package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;

/**
 * Parses text file for phone call data
 * Created by Evan on 7/14/2015.
 */
public class TextParser implements PhoneBillParser {

  protected String fileName = null;

  /**
   * TextParser requires a file name to write to
   * @param file file name to write to
   */
  public void setFile(String file) { fileName = file; }

  /**
   * Parses some source and returns a phone bill
   *
   * @throws ParserException If the source cannot be parsed
   */
  @Override
  public AbstractPhoneBill parse() throws ParserException {

    String customerName;

    if (fileName == null) {
      throw new ParserException("File name not set"); //should never be thrown
    }

    try {
      FileReader f = new FileReader(fileName);
      BufferedReader reader = new BufferedReader(f);
      try {
        customerName = reader.readLine();
        if (customerName == null) {
          throw new ParserException("Empty file");
        }
        PhoneBill bill = new PhoneBill(customerName);
        while (reader.ready()) {
          String[] s = reader.readLine().split(";");
          if (s.length != 4) {
            throw new ParserException("File is not a properly formatted phone bill record");
          }
          PhoneCall call = new PhoneCall(s[0], s[1], s[2], s[3]);
          bill.addPhoneCall(call);
        }
        return bill;
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      File f = new File(fileName);
      try {
        f.createNewFile();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }
    return new PhoneBill();
  }
}
