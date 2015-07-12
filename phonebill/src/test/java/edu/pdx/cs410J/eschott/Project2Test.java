package edu.pdx.cs410J.eschott;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import edu.pdx.cs410J.InvokeMainTestCase;
import static junit.framework.Assert.assertEquals;

/**
 * Tests the functionality in the {@link Project1} main class.
 */
public class Project2Test extends InvokeMainTestCase {
    /**
     * Variables for invoking new class objects
     */
    private String callerNumber = "111-111-1111";
    private String calleeNumber = "999-999-9999";
    private String startTime = "1/1/2000 11:59";
    private String endTime = "1/1/2000 12:01";

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

    /**
    * Tests that invoking the main method with no arguments issues an error
    */
    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertEquals(new Integer(1), result.getExitCode());
        assertTrue(result.getErr().contains("Missing command line arguments"));
    }

    /**
     * Tests that main method accepts one arguments
     */
    @Test
    public void testOneCommandLineArgument() {
        MainMethodResult result = invokeMain("Argument First");
        assertEquals(new Integer(1), result.getExitCode());
        assertFalse(result.getErr().contains("Missing command line arguments"));
    }

    /**
     * Tests that the getCaller() method works
     */
    @Test
    public void testGetCallerReturnsCallerNumber() {
        PhoneCall call = logPhoneCall(callerNumber);
        assertThat(call.getCaller(), equalTo(callerNumber));
    }

    /**
     * Tests that the getCallee() method works
     */
    @Test
    public void testGetCalleeReturnsCalleeNumber() {
        PhoneCall call = logPhoneCall(callerNumber, calleeNumber);
        assertThat(call.getCallee(), equalTo(calleeNumber));
    }

    /**
     * Tests that the callerNumber validation works
     */
    @Test
    public void testIsCallerNumberValid() {
        callerNumber = "9999";
        try {
            PhoneCall call = logPhoneCall(callerNumber, calleeNumber);
            fail("Expected exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(equalTo("Phone number is not valid! Should be in format ###-###-####")));
        }
    }

    /**
     * Tests that the calleeNumber validation works
     */
    @Test
    public void testIsCalleeNumberValid() {
        calleeNumber = "111-x11-1111";
        try {
            PhoneCall call = logPhoneCall(callerNumber, calleeNumber);
            fail("Expected exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(equalTo("Phone number is not valid! Should be in format ###-###-####")));
        }
    }

    /**
     * Tests that getStartTimeString() method works
     */
    @Test
    public void testGetStartTimeStringReturnsStartTime() {
        PhoneCall call = logPhoneCall(callerNumber, calleeNumber, startTime);
        assertThat(call.getStartTimeString(), equalTo(startTime));
    }

    /**
     * Tests that getEndTimeString() method works
     */
    @Test
    public void testGetEndTimeStringReturnsEndTime() {
        PhoneCall call = logPhoneCall(callerNumber, calleeNumber, startTime, endTime);
        assertThat(call.getEndTimeString(), equalTo(endTime));
    }

    /**
     * Tests date time validation
     */
    @Test
    public void testInvalidStartTimeIsInvalid() {
        startTime = "1234";
        try {
            PhoneCall call = logPhoneCall(callerNumber, calleeNumber, startTime);
            fail("Expected exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(equalTo("Invalid date-time format! Should be in format MM/DD/YYYY HH:MM")));
        }
    }

    /**
     * Tests date time validation for valid input
     */
    @Test
    public void testValidEndTimeIsValid() {
        endTime = "1/1/2000 1:01";
        try {
            PhoneCall call = logPhoneCall(callerNumber, calleeNumber, startTime, endTime);
        } catch (IllegalArgumentException e) {
          fail("Date Time format is invalid");
        }
    }

    /**
     * Tests toString() method
     */
    @Test
    public void testPrintsCallDescriptionShouldPrintCallDescription() {
        PhoneCall call = logPhoneCall(callerNumber, calleeNumber, startTime, endTime);
        String s = "Phone call from " + callerNumber + " to " +
                calleeNumber + " from " + startTime +
                " to " + endTime;
        assertThat(call.toString(), equalTo(s));
    }

    @Test
    public void testGetCustomerReturnsCustomer() {
        PhoneBill bill = new PhoneBill();
        try {
            bill.getCustomer();
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), equalTo("Not yet implemented!"));
        }
    }

    @Test
    public void testAddPhoneCallAddsPhoneCall() {
        PhoneBill bill = new PhoneBill();
        PhoneCall call = new PhoneCall();
        try {
            bill.addPhoneCall(call);
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), equalTo("Not yet implemented!"));
        }
    }

    @Test
    public void testGetPhoneCallsReturnsPhoneCalls() {
        PhoneBill bill = new PhoneBill();
        try {
            bill.getPhoneCalls();
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), equalTo("Not yet implemented!"));
        }
    }

    /**
     * Method for creating a PhoneCall object with 1 param
     * @param caller Number of the person calling
     * @return calls logPhoneCall recursively
     */
    private PhoneCall logPhoneCall(String caller) {
        return logPhoneCall(caller, "999-999-9999");
    }

    /**
     * Method for creating a PhoneCall object with 2 params
     * @param caller Number of the person calling
     * @param callee Number of the person being called
     * @return calls logPhoneCall recursively
     */
    private PhoneCall logPhoneCall(String caller, String callee) {
        return logPhoneCall(caller, callee, "1/1/2000 11:59");
    }

    /**
     * Method for creating a PhoneCall object with 3 params
     * @param caller Number of the person calling
     * @param callee Number of the person being called
     * @param callStart datetime of start time
     * @return call logPhoneCall recursively
     */
    private PhoneCall logPhoneCall(String caller, String callee, String callStart) {
        return logPhoneCall(caller, callee, callStart, "1/1/2000 12:01");
    }

    /**
     * Method for creating a PhoneCall object with all 4 params
     * @param caller Number of the person calling
     * @param callee Number of the person being called
     * @param callStart datetime the call starts
     * @param callEnd datetime the call ends
     * @return call logPhoneCall recursively
     */
    private PhoneCall logPhoneCall(String caller, String callee, String callStart, String callEnd) {
        return new PhoneCall(caller, callee, callStart, callEnd);
    }




}