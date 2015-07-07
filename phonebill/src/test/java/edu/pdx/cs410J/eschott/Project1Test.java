package edu.pdx.cs410J.eschott;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.startsWith;
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
    public void testGetCallerReturnsCallerName() {
        final String c = "Bob Smith";
        PhoneCall call = new PhoneCall(c);
        assertThat(call.getCaller(), equalTo(c));
    }



}