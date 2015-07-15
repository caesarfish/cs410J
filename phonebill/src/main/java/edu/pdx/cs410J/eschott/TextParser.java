package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

/**
 * Created by Evan on 7/14/2015.
 */
public class TextParser implements PhoneBillParser {

  protected String fileName = null;

  public void setFile(String file) { fileName = file; }

  /**
   * Parses some source and returns a phone bill
   *
   * @throws ParserException If the source cannot be parsed
   */
  @Override
  public AbstractPhoneBill parse() throws ParserException {
    return null;
  }
}
