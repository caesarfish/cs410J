package edu.pdx.cs410J.eschott.client;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.*;

public class PhoneBill extends AbstractPhoneBill
{
  private ArrayList<PhoneCall> calls = new ArrayList<PhoneCall>();
  private String customerName;

  /**
   * Method to create phone bill
   * @param customerName
   */
  public PhoneBill(String customerName) {
    this.customerName = customerName;
  }

  /**
   * Default constructor needed for extending AbstractPhoneBill
   */
  public PhoneBill() {
  }

  @Override
  public String getCustomer() {
    return customerName;
  }

  @Override
  public void addPhoneCall(AbstractPhoneCall call) {
    boolean found = false;
    Iterator itr = calls.iterator();
    while (itr.hasNext()) {
      PhoneCall callInList = (PhoneCall)itr.next();
      /*if (callInList.equals(call)){
        found = true;
        this.calls.add(call);
      }*/
    }
    if (!found){
      this.calls.add((PhoneCall)call);
      Collections.sort(calls);
    }

  }

  @Override
  public Collection getPhoneCalls() {
    return this.calls;
  }


}

