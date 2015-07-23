package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * PhoneBill class extends AbstractPhoneBill
 * For collecting PhoneCall records
 * Created by Evan on 7/10/2015.
 */
public class PhoneBill extends AbstractPhoneBill {

  private String customerName = "";
  private ArrayList<PhoneCall> callList = new ArrayList<>();

  /**
   * Default Constructor
   */
  public PhoneBill() {
  }

  /**
   * Main constructor
   * @param customer customer name provided as argument to program
   */
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
   * Sets customer name
   * @param name name of customer
   */
  public void setCustomer(String name) {
    customerName = name;
  }

  /**
   * Adds a phone call to this phone bill
   */
  public void addPhoneCall(AbstractPhoneCall call) {
    callList.add((PhoneCall) call);
    Collections.sort(callList);
  }

  /**
   * Returns all of the phone calls (as instances of {@link
   * AbstractPhoneCall}) in this phone bill
   */
  public Collection getPhoneCalls() {
    return callList;

  }


}
