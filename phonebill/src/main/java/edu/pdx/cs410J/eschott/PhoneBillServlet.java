package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.ParserException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
     * Handles an HTTP GET request by printing out the bill or calls within
     * the search range specified in the parameters passed in the request
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException
     * @throws IOException
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
                if (end != null) {
                    System.out.println("Searching calls between " + start + " and " + end);
                    printCallRange(bill, start, end, response);
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
     * Handles an HTTP POST request by creating a phoneCall and adding it to the phoneBill record
     * with the given parameters. If the phoneBill does not exist, it is created.
     * @param request HTTP request
     * @param response HTTP response
     * @throws ServletException
     * @throws IOException
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
     * Prints the given phone bill
     * @param bill bill to be printed
     * @param response HttpServletResponse object
     * @throws IOException
     */
    private void printBill( PhoneBill bill, HttpServletResponse response ) throws IOException
    {
        PrettyPrinter pp = new PrettyPrinter();
        pp.dump(bill, response);
        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Prints the phone calls within the given time range
     * @param bill phone bill containing phone call records
     * @param start beginning of time range
     * @param end end of time range
     * @param response response object
     * @throws IOException
     */
    private void printCallRange(PhoneBill bill, String start, String end, HttpServletResponse response) throws IOException {
        Date startTime;
        Date endTime;
        PhoneBill newBill = new PhoneBill(bill.getCustomer());
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
        dateFormat.setLenient(false);
        try {
            startTime = dateFormat.parse(start);
            endTime = dateFormat.parse(end);
        } catch (ParseException e) {
            throw new IOException("Invalid date format");
        }
        for (Object o : bill.getPhoneCalls()) {
            PhoneCall call = (PhoneCall) o;
            if (call.getStartTime().after(startTime) && call.getEndTime().before(endTime)) {
                newBill.addPhoneCall(call);
            }
        }

        if (newBill.getPhoneCalls().size() == 0) {
            PrintWriter pw = response.getWriter();
            pw.println( "No calls found between " + start + " and " + end + " for " + bill.getCustomer());
            pw.flush();
        } else {
            printBill(newBill, response);
        }
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
