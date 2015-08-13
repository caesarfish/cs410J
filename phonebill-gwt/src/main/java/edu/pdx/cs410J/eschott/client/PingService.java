package edu.pdx.cs410J.eschott.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Date;

/**
 * A GWT remote service that returns a Phone Bill
 */
@RemoteServiceRelativePath("ping")
public interface PingService extends RemoteService {

  /**
   * Adds a phone call to the given customer's bill
   * @param customerName who the phone bill belongs to
   * @param call call to add to the phone bill
   * @return returns phone bill
   * @throws InvalidCustomerNameException
   */
  AbstractPhoneBill ping(String customerName, PhoneCall call) throws InvalidCustomerNameException;

  /**
   * Returns a phone bill with the given parameters
   * @param customerName who the phone bill belongs to
   * @param startTime beginning search time
   * @param endTime end search time
   * @return returns phone bill
   * @throws InvalidCustomerNameException
   */
  AbstractPhoneBill ping(String customerName, Date startTime, Date endTime) throws InvalidCustomerNameException;
}
