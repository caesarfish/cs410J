package edu.pdx.cs410J.eschott.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.eschott.client.PhoneBill;
import edu.pdx.cs410J.eschott.client.PingService;

/**
 * The server-side implementation of the Phone Bill service
 */
public class PingServiceImpl extends RemoteServiceServlet implements PingService
{

  @Override
  public AbstractPhoneBill ping(String customerName) {
    PhoneBill phonebill = new PhoneBill(customerName);
    return phonebill;
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
