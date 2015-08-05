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

    public static final String USAGE_NOTES = "usage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>\n" +
            "args are (in this order):\n" +
            "   customer :          Person whose phone bill we're modeling\n" +
            "   callerNumber :      Phone number of caller\n" +
            "   calleeNumber :      Phone number of person who was called\n" +
            "   startTime :         Date and time call began (12-hour time)\n" +
            "   endTime :           Date and time call ended (12-hour time)\n" +
            "options are (options may appear in any order):\n" +
            "   -host hostname :    Host computer on which the server runs\n" +
            "   -port port :        Port on which the server is listening\n" +
            "   -search :           Phone calls should be searched for\n" +
            "   -print :            Prints a description of the new phone call\n" +
            "   -README :           Prints a README for this project and exits\n" +
            "The -search option should be followed only by start and end time arguments\n" +
            "Date and time should be in the format: mm/dd/yyyy hh:mm a (ex. 1/1/2015 12:23 pm)\n" +
            "Phone numbers should be in the format: ###-###-####";

    public static final String PROJECT_DESCRIPTION = "Project Description: \n" +
            "   This project records phone call details entered on the command line. Support\n" +
            "   is provided for a phone bill server that provides REST-ful web services to \n" +
            "   a phone bill client";

    public static void main(String... args) {
        ArrayList<String> argList = new ArrayList<>();
        String hostName;
        String portString;
        String callerName;
        String callerNumber = "";
        String calleeNumber = "";
        String startTime = "";
        String endTime = "";


        argList.addAll(Arrays.asList(args));
        CommandLineParser clp = new CommandLineParser(argList);

        hostName = clp.getHostName();
        portString = clp.getPortString();

        if(clp.checkReadMeFlag()){
            printReadMe();
            System.exit(1); //prints README and exits
        } else {
            if (hostName == null) {
                usage( MISSING_ARGS );
            } else if ( portString == null) {
                usage( "Missing port" );
            }
        }

        //process command line args
        //either search or add phone call
        callerName = clp.getArgs().get(0);
        if (clp.checkSearchFlag() && clp.getArgs().size() == 7 ) {
            startTime = clp.getArgs().get(1).concat(" ").concat(clp.getArgs().get(2).concat(" ").concat(clp.getArgs().get(3)));
            endTime = clp.getArgs().get(4).concat(" ").concat(clp.getArgs().get(5).concat(" ").concat(clp.getArgs().get(6)));
        } else if (clp.getArgs().size() == 9) {
            callerNumber = clp.getArgs().get(1);
            calleeNumber = clp.getArgs().get(2);
            startTime = clp.getArgs().get(3).concat(" ").concat(clp.getArgs().get(4).concat(" ").concat(clp.getArgs().get(5)));
            endTime = clp.getArgs().get(6).concat(" ").concat(clp.getArgs().get(7).concat(" ").concat(clp.getArgs().get(8)));

        } else {
            usage(MISSING_ARGS);
            System.exit(0);
        }

        //validates phone numbers
        if (!callerNumber.equals("") || !calleeNumber.equals("")) {
            try {
                Validator.validatePhoneNumber(callerNumber);
                Validator.validatePhoneNumber(calleeNumber);
            } catch (ParserException e) {
                System.err.println("Invalid phone number format");
                System.exit(0);
            }
        }

        //validates start and end time
        try {
            Validator.validateDate(startTime);
            Validator.validateDate(endTime);
        } catch (ParserException e) {
            System.err.println(e.getMessage());
            System.exit(0);
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

        if (clp.checkSearchFlag()){
            try {
                response = client.searchPhoneCalls(callerName, startTime, endTime);
            } catch (IOException e) {
                error("While contacting server: " + e);
                return;
            }
        } else {
            try {
                response = client.addPhoneCall(callerName, callerNumber, calleeNumber, startTime, endTime);
            } catch (IOException e) {
                error("While contacting server: " + e);
                return;
            }
            if(clp.checkPrintFlag()) {
                try {
                    PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
                    System.out.println(call.toString());
                } catch (ParserException e) {
                    e.printStackTrace();
                }
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

    /**
     * Prints error information for this program and exits
     * @param message an error message to print
     */
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
        err.println(USAGE_NOTES);

        System.exit(1);
    }

    /**
     * Prints README
     */
    private static void printReadMe() {
        String readmeText = "README file for PhoneBill v1.4 \n" +
                "Evan Schott \n" +
                "CS410J \n" +
                "Summer 2015 \n" +
                "Project 4 \n";
        System.out.println(readmeText);
        System.out.println(PROJECT_DESCRIPTION);
        System.out.println(USAGE_NOTES);
    }
}