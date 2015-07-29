package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsEqual;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Tests the {@link Project4} class by invoking its main method with various arguments 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Project4Test extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    public void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getErr(), containsString(Project4.MISSING_ARGS));
    }

    @Test
    public void test2EmptyServer() {
        MainMethodResult result = invokeMain(Project4.class, "-host localhost -port 8080");
        //assertThat(result.getErr(), result.getExitCode(), equalTo(0));
        String out = result.getOut();
        assertThat(result.getErr(), containsString(Project4.MISSING_ARGS));
    }

    @Test
    public void testMainMethodPrintsReadme() {
        MainMethodResult result = invokeMain( Project4.class, "-README");
        String readmeText = "README file for PhoneBill v1.4 ";
        assertThat(result.getOut(), CoreMatchers.containsString(readmeText));
    }

    @Test
    public void testPhoneCallAdded() {
        MainMethodResult result = invokeMain( Project4.class, "-host", "localhost", "-port", "8080", "Bob Smith", "111-111-1111", "222-222-2222", "1/1/2000",
                "11:12", "AM", "1/2/2000", "12:34", "PM");
        assertThat(result.getErr(), IsEqual.equalTo(""));
        /*assertThat(result.getOut(), CoreMatchers.containsString("Phone call from 111-111-1111 to 222-222-2222 from 1/1/00 " +
                "11:12 AM to 1/2/00 12:34 PM"));*/
        assertThat(result.getOut(), CoreMatchers.containsString("Bob Smith"));
    }

    @Test
    public void testMainMethodPrintsPhoneCallRecord() {
        MainMethodResult result = invokeMain( Project4.class, "-host", "localhost", "-port", "8080", "-print", "Bob", "111-111-1111", "222-222-2222", "1/1/2000",
                "11:12", "AM", "1/2/2000", "12:34", "PM");
        assertThat(result.getErr(), IsEqual.equalTo(""));
        assertThat(result.getOut(), CoreMatchers.containsString("Phone call from 111-111-1111 to 222-222-2222 from 1/1/00 " +
                "11:12 AM to 1/2/00 12:34 PM"));
    }

    /*@Test
    public void test3NoValues() {
        String key = "KEY";
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, key );
        assertThat(result.getErr(), result.getExitCode(), equalTo(0));
        String out = result.getOut();
        assertThat(out, out, containsString(Messages.getMappingCount(0)));
        assertThat(out, out, containsString(Messages.formatKeyValuePair(key, null)));
    }*/

    @Test
    public void testAddPhoneCall() {

    }

    /*@Test
    public void test4AddValue() {
        String key = "KEY";
        String value = "VALUE";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, key, value );
        assertThat(result.getErr(), result.getExitCode(), equalTo(0));
        String out = result.getOut();
        assertThat(out, out, containsString(Messages.mappedKeyValue(key, value)));

        result = invokeMain( Project4.class, HOSTNAME, PORT, key );
        out = result.getOut();
        assertThat(out, out, containsString(Messages.getMappingCount(1)));
        assertThat(out, out, containsString(Messages.formatKeyValuePair(key, value)));

        result = invokeMain( Project4.class, HOSTNAME, PORT );
        out = result.getOut();
        assertThat(out, out, containsString(Messages.getMappingCount(1)));
        assertThat(out, out, containsString(Messages.formatKeyValuePair(key, value)));
    }*/
}