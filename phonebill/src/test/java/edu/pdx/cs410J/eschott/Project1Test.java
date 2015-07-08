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
public class Project1Test extends InvokeMainTestCase {

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

    @Test
    public void testGetCallerReturnsCallerNumber() {
        final String callerNumber = "111-111-1111";
        final String calleeNumber = "999-999-9999";
        PhoneCall call = logPhoneCall(callerNumber, calleeNumber);
        assertThat(call.getCaller(), equalTo(callerNumber));
    }

    @Test
    public void testGetCalleeReturnsCalleeNumber() {
        final String callerNumber = "111-111-1111";
        final String calleeNumber = "999-999-9999";
        PhoneCall call = logPhoneCall(callerNumber, calleeNumber);
        assertThat(call.getCallee(), equalTo(calleeNumber));
    }

    @Test
    public void testIsCallerNumberValid() {
        final String callerNumber = "9999";
        final String calleeNumber = "999-999-9999";
        try {
            PhoneCall call = logPhoneCall(callerNumber, calleeNumber);
            fail("Expected exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(equalTo("Phone number is not valid!")));
        }
    }

    @Test
    public void testIsCalleeNumberValid() {
        final String callerNumber = "111-111-1111";
        final String calleeNumber = "1111";
        try {
            PhoneCall call = logPhoneCall(callerNumber, calleeNumber);
            fail("Expected exception");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(equalTo("Phone number is not valid!")));
        }
    }

    @Test
    public void testGetStartTimeStringReturnsStartTime() {

    }

    private PhoneCall logPhoneCall(String caller) {
        return logPhoneCall(caller, "999-999-9999");
    }

    private PhoneCall logPhoneCall(String caller, String callee) {
        return logPhoneCall(caller, callee, "1/1/2000 11:59");
    }

    private PhoneCall logPhoneCall(String caller, String callee, String callStart) {
        return logPhoneCall(caller, callee, callStart, "1/1/2000 12:01");
    }

    private PhoneCall logPhoneCall(String caller, String callee, String callStart, String callEnd) {
        return new PhoneCall(caller, callee, callStart, callEnd);
    }

}