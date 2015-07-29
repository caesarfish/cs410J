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
        String customer = getParameter( "customer", request );
        PrintWriter pw = response.getWriter();
        PhoneBill bill = this.data.get(customer);
        if (bill == null) {
            pw.println("No records exist for customer: " + customer);
            response.setStatus( HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.setContentType( "text/plain" );
        String start = getParameter( "startTime", request);
        String end = getParameter( "endTime", request);
        if (customer != null) {
            if (start != null) {
                if (end!=null) {
                    System.out.println("Searching calls between " + start + " and " + end);
                    printCallRange(customer, start, end, response);
                } else {
                    missingRequiredParameter( response, "endTime");
                }
            } else {
                printBill(bill, response);
            }
        }

        pw.flush();

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

        //If phone bill exists, adds to it
        //otherwise creates new phone bill

        if (!this.data.containsKey(customer)) {
            PhoneBill bill = new PhoneBill(customer);
            bill.addPhoneCall(call);
            this.data.put(customer, bill);
        } else {
            PhoneBill bill = this.data.get(customer);
            bill.addPhoneCall(call);
            this.data.replace(customer, bill);
        }

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
    private void printBill( PhoneBill bill, HttpServletResponse response ) throws IOException
    {
        PrettyPrinter pp = new PrettyPrinter();
        pp.dump(bill, response);
        response.setStatus(HttpServletResponse.SC_OK);

    }

    private void printCallRange(String customer, String start, String end, HttpServletResponse response) {

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
