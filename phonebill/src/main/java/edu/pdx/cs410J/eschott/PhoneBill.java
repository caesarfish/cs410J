package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Collection;

/**
 * PhoneBill class extends AbstractPhoneBill
 * For collecting PhoneCall records
 * Created by Evan on 7/10/2015.
 */
public class PhoneBill extends AbstractPhoneBill {

  /**
   * Returns the name of the customer whose phone bill this is
   */
  public String getCustomer() {
    throw new UnsupportedOperationException("Not yet implemented!");
  }

  /**
   * Adds a phone call to this phone bill
   */
  public void addPhoneCall(AbstractPhoneCall call) {
    throw new UnsupportedOperationException("Not yet implemented!");

  }

  /**
   * Returns all of the phone calls (as instances of {@link
   * AbstractPhoneCall}) in this phone bill
   */
  public Collection getPhoneCalls() {
    throw new UnsupportedOperationException("Not yet implemented!");

  }

  /**
   * Returns a brief textual description of this phone bill
   */
}
