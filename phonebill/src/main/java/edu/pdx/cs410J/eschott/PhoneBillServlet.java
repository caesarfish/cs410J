package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.ParserException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple key/value pairs.
 */
public class PhoneBillServlet extends HttpServlet
{
    private final Map<String, PhoneBill> data = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the value of the key
     * specified in the "key" HTTP parameter to the HTTP response.  If the "key"
     * parameter is not specified, all of the key/value pairs are written to the
     * HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter( "customer", request );
        System.out.println("Before passing to writeValue(): " + customer);
        if (customer != null) {
            writeValue(customer, response);

        } else {
           // writeAllMappings(response);
        }
    }

    /**
     * Handles an HTTP POST request by storing the key/value pair specified by the
     * "key" and "value" request parameters.  It writes the key/value pair to the
     * HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter( "customer", request );
        if (customer == null) {
            missingRequiredParameter( response, "customer" );
            return;
        }

        String callerNumber = getParameter( "callerNumber", request );
        if ( callerNumber == null) {
            missingRequiredParameter( response, "callerNumber" );
            return;
        }
        String calleeNumber = getParameter( "calleeNumber", request );
        if ( calleeNumber == null) {
            missingRequiredParameter( response, "calleeNumber" );
            return;
        }
        String startTime = getParameter( "startTime", request );
        if ( startTime == null) {
            missingRequiredParameter( response, "startTime" );
            return;
        }
        String endTime = getParameter( "endTime", request );
        if ( endTime == null) {
            missingRequiredParameter( response, "endTime" );
            return;
        }

        PhoneCall call = new PhoneCall();
        try {
            call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
        } catch (ParserException e) {
            e.printStackTrace();
        }

        PhoneBill bill = new PhoneBill();
        if (!this.data.containsKey(customer)) {
            bill = new PhoneBill(customer);
            bill.addPhoneCall(call);
            this.data.put(customer, bill);
        } else {
            bill = this.data.get(customer);
            bill.addPhoneCall(call);
            this.data.replace(customer, bill);
        }



        //verify data written
        PhoneBill verify = this.data.get(customer);
        if (verify == null) {
            System.out.println("Verify failed");
        } else {
            System.out.println("Verified added");
        }

        PrintWriter pw = response.getWriter();
        pw.println(Messages.mappedKeyValue(customer, bill.toString()));
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println( Messages.missingRequiredParameter(parameterName));
        pw.flush();
        
        response.setStatus( HttpServletResponse.SC_PRECONDITION_FAILED );
    }

    /**
     * Writes the value of the given key to the HTTP response.
     *
     * The text of the message is formatted with {@link Messages#getMappingCount(int)}
     * and {@link Messages#formatKeyValuePair(String, String)}
     */
    private void writeValue( String customer, HttpServletResponse response ) throws IOException
    {

        System.out.println("writeValue has: " + customer);
        PhoneBill bill = this.data.get(customer);
        if (bill == null) {
            System.out.println("Failed to retrieve bill");
        }
        PrintWriter pw = response.getWriter();
        //pw.println("Phonebill generated for " + bill.getCustomer());

        pw.flush();

        PrettyPrinter pp = new PrettyPrinter();
        //pp.dump(bill, response);

        /*PrintWriter pw = response.getWriter();
        pw.println("Phonebill generated for " + customer);

        pw.flush();*/

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Writes all of the key/value pairs to the HTTP response.
     *
     * The text of the message is formatted with
     * {@link Messages#formatKeyValuePair(String, String)}
     */
    private void writeAllMappings( HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        pw.println(Messages.getMappingCount( data.size() ));

        for (Map.Entry<String, PhoneBill> entry : this.data.entrySet()) {
            pw.println(Messages.formatKeyValuePair(entry.getKey(), entry.getValue().toString()));
        }

        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

}
