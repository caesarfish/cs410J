package edu.pdx.cs410J.eschott.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import edu.pdx.cs410J.AbstractPhoneBill;

/**
 * A GWT remote service that returns a Phone Bill
 */
@RemoteServiceRelativePath("ping")
public interface PingService extends RemoteService {

  /**
   * Returns a phone bill
   * @param customerName who the phone bill belongs to
   * @return
   */
  public AbstractPhoneBill ping(String customerName, PhoneCall call);

  /**
   * Returns the a dummy Phone Bill
   */
  AbstractPhoneBill ping(String customerName);
}
