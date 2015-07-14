package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.IOException;

/**
 * Created by Evan on 7/13/2015.
 * For dumping phone bill records to text file
 */
public class TextDumper implements PhoneBillDumper {
  /**
   * Dumps a phone bill to some destination.
   *
   * @param bill the phone bill to be printed
   */
  @Override
  public void dump(AbstractPhoneBill bill) throws IOException {

  }
}
