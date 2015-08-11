package edu.pdx.cs410J.eschott.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractPhoneBill;

/**
 * The client-side interface to the ping service
 */
public interface PingServiceAsync {

  /**
   * Returns the a dummy Phone Bill
   */
  void ping(String customerName, AsyncCallback<AbstractPhoneBill> async);

  /**
   * Returns a phone bill
   *
   * @param customerName who the phone bill belongs to
   * @return
   */
  void ping(String customerName, PhoneCall call, AsyncCallback<AbstractPhoneBill> async);
}
