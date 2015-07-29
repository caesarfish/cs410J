package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        ArrayList<String> argList = new ArrayList<>();
        String hostName = null;
        String portString = null;
        String callerName;
        String callerNumber;
        String calleeNumber;
        String startTime;
        String endTime;


        argList.addAll(Arrays.asList(args));
        CommandLineParser clp = new CommandLineParser(argList);

        hostName = clp.getHostName();
        portString = clp.getPortString();

        if(clp.checkReadMeFlag()){
            printReadMe();
            System.exit(1); //prints README and exits
        }

        if (clp.getArgs().size() != 9) {
                usage(MISSING_ARGS);
                System.exit(0);
        }

        callerName = clp.getArgs().get(0);

        //Assigns attributes from command line parser
        callerNumber = clp.getArgs().get(1);
        calleeNumber = clp.getArgs().get(2);
        startTime = clp.getArgs().get(3).concat(" ").concat(clp.getArgs().get(4).concat(" ").concat(clp.getArgs().get(5)));
        endTime = clp.getArgs().get(6).concat(" ").concat(clp.getArgs().get(7).concat(" ").concat(clp.getArgs().get(8)));

        //validates phone numbers
        try {
            Validator.validatePhoneNumber(callerNumber);
            Validator.validatePhoneNumber(calleeNumber);
        } catch (ParserException e) {
            System.err.println("Invalid phone number format");
            System.exit(0);
        }

        //validates start and end time
        try {
            Validator.validateDate(startTime);
            Validator.validateDate(endTime);
        } catch (ParserException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }


        if (!clp.checkReadMeFlag()) {
            if (hostName == null) {
                usage( MISSING_ARGS );

            } else if ( portString == null) {
                usage( "Missing port" );
            }
        }


        int port;
        try {
            assert portString != null;
            port = Integer.parseInt( portString );
            
        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        HttpRequestHelper.Response response;

        if (callerName == null) {

        }
        try {
            response = client.addPhoneCall(callerName, callerNumber, calleeNumber, startTime, endTime);
        } catch (IOException e) {
            error("While contacting server: " + e);
            return;
        }

        if(clp.checkPrintFlag()) {
            try {
                response = client.printPhoneCall(callerName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        checkResponseCode( HttpURLConnection.HTTP_OK, response);

        System.out.println(response.getContent());

        System.exit(0);
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port");
        err.println("  host    Host of web server");
        err.println("  port    Port of web server");
        err.println();
        err.println("This simple program posts key/value pairs to the server");
        err.println("If no value is specified, then all values are printed");
        err.println("If no key is specified, all key/value pairs are printed");
        err.println();

        System.exit(1);
    }

    /**
     * Prints README
     */
    private static void printReadMe() { //Todo: update readme
        String readmeText = "README file for PhoneBill v1.4 \n" +
                "Evan Schott \n" +
                "CS410J \n" +
                "Summer 2015 \n" +
                "Project 4 \n" +
                "Project Description: \n" +
                "   This project records phone call details entered on the command line. \n" +
                "usage: java edu.pdx.cs410J.<login-id>.Project3 [options] <args>\n" +
                "args are (in this order):\n" +
                "   customer : Person whose phone bill we're modeling\n" +
                "   callerNumber : Phone number of caller\n" +
                "   calleeNumber : Phone number of person who was called\n" +
                "   startTime : Date and time call began (12-hour time)\n" +
                "   endTime : Date and time call ended (12-hour time)\n" +
                "options are (options may appear in any order):\n" +
                "   -pretty : pretty print the phone bill to a text file\n" +
                "             or standard out (file -)\n" +
                "   -textFile file : Where to read/write the phone bill\n" +
                "   -print : Prints a description of the new phone call\n" +
                "   -README : Prints a README for this project and exits\n" +
                "Date and time should be in the format: mm/dd/yyyy hh:mm a (ex. 1/1/2000 12:23 pm)\n" +
                "Phone numbers should be in the format: ###-###-####";

        System.out.println(readmeText);
    }
}