package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;

/**
 * PhoneBill class extends AbstractPhoneBill
 * For collecting PhoneCall records
 * Created by Evan on 7/10/2015.
 */
public class PhoneBill extends AbstractPhoneBill {

  private String customerName = null;
  private ArrayList<PhoneCall> callList = new ArrayList<>();

  /**
   * Default Constructor
   */
  public PhoneBill() {
  }

  public PhoneBill(String customer){
    customerName = customer;
  }

  /**
   * Returns the name of the customer whose phone bill this is
   */
  public String getCustomer() {
    return customerName;
  }

  /**
   * Adds a phone call to this phone bill
   */
  public void addPhoneCall(AbstractPhoneCall call) {
    callList.add((PhoneCall) call);
  }

  /**
   * Returns all of the phone calls (as instances of {@link
   * AbstractPhoneCall}) in this phone bill
   */
  public Collection getPhoneCalls() {
    return callList;

  }


}
