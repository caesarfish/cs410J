package edu.pdx.cs410J.eschott.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.eschott.client.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The server-side implementation of the Phone Bill service
 */
public class PingServiceImpl extends RemoteServiceServlet implements PingService
{
  private final Map<String, PhoneBill> data = new HashMap<>();


  /**
   * Adds call to PhoneBill
   * @param customerName who the phone bill belongs to
   * @param call call information
   * @return PhoneBill
   */
  @Override
  public AbstractPhoneBill ping(String customerName, PhoneCall call) throws InvalidCustomerNameException, PhoneCallAlreadyExistsException {
    //If phone bill exists, adds to it
    //otherwise creates new phone bill
    if (customerName == null || "".equals(customerName)) {
      throw new InvalidCustomerNameException("Customer name can not be blank");
    } else if (customerName.length() < 3) {
      throw new InvalidCustomerNameException("Customer name should be at least 3 letters");
    }
    if (!this.data.containsKey(customerName)) {
      PhoneBill bill = new PhoneBill(customerName);
      bill.addPhoneCall(call);
      this.data.put(customerName, bill);
    } else {
      PhoneBill bill = this.data.get(customerName);
      bill.addPhoneCall(call);
      this.data.replace(customerName, bill);
    }
    return this.data.get(customerName);
  }

  /**
   * Returns the the Phone Bill for the given customer   *
   * @param customerName who the phone bill belongs to
   * @param startTime beginning of search range
   * @param endTime end of search range
   */
  @Override
  public AbstractPhoneBill ping(String customerName, Date startTime, Date endTime) throws InvalidCustomerNameException {
    if (customerName == null || "".equals(customerName)) {
      throw new InvalidCustomerNameException("Customer name can not be blank");
    }
    PhoneBill bill = this.data.get(customerName);
    PhoneBill newBill = new PhoneBill(customerName);
    if (bill == null) {
      throw new InvalidCustomerNameException("No data exists for customer: " + customerName);
    }
    if (startTime != null && endTime != null) {
      for (Object o : bill.getPhoneCalls()) {
        PhoneCall call = (PhoneCall) o;
        if (call.getStartTime().after(startTime) && call.getEndTime().before(endTime)) {
          newBill.addPhoneCall(call);
        }
      }
      return newBill;
    } else {
      return bill;
    }
  }

  /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn't handled
   */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }
}
