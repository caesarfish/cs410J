package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send key/value pairs.
 */
public class PhoneBillRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";

    private final String url;


    /**
     * Creates a client to the Phone Bill REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public PhoneBillRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }


    /**
     * Returns phone bill for the given customer
     * @param customer Customer whose phone bill should print
     * @return Returns response object
     * @throws IOException
     */
    public Response printPhoneBill( String customer ) throws IOException
    {
        return get(this.url, "customer", customer);
    }

    /**
     * Posts a phone call add to the server
     * @param customer Customer name
     * @param callerNumber Originating phone number
     * @param calleeNumber Destination phone number
     * @param startTime Time call begins
     * @param endTime Time call ends
     * @return returns response object
     * @throws IOException
     */
    public Response addPhoneCall( String customer, String callerNumber, String calleeNumber,
                                  String startTime, String endTime) throws IOException
    {
        return post(this.url, "customer", customer, "callerNumber", callerNumber, "calleeNumber", calleeNumber,
                "startTime", startTime, "endTime", endTime);
    }

    /**
     * Searches phone bill for calls with the given parameters
     * @param customer Customer whose calls will be searched
     * @param startTime Beginning of search time period
     * @param endTime End of search time period
     * @return Sends GET to server
     * @throws IOException
     */
    public Response searchPhoneCalls(String customer, String startTime, String endTime) throws IOException {
        return get(this.url, "customer", customer, "startTime", startTime, "endTime", endTime);
    }
}
