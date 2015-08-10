package edu.pdx.cs410J.eschott.client;

import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.lang.Override;
import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill
{
  private Collection<AbstractPhoneCall> calls = new ArrayList<AbstractPhoneCall>();
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
    this.calls.add(call);
  }

  @Override
  public Collection getPhoneCalls() {
    return this.calls;
  }
}
