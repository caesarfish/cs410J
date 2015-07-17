package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import org.hamcrest.CoreMatchers;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import edu.pdx.cs410J.InvokeMainTestCase;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static junit.framework.Assert.assertEquals;

/**
 * Tests the functionality in the {@link Project2} main class.
 */
public class Project2Test extends InvokeMainTestCase {
  /**
   * Variables for invoking new class objects
   */
  private String customer = "Bob Smith";
  private String callerNumber = "111-111-1111";
  private String calleeNumber = "999-999-9999";
  private String startTime = "1/1/2000 11:59";
  private String endTime = "1/1/2000 12:01";

  /**
   * Invokes the main method of {@link Project2} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
      return invokeMain( Project2.class, args );
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
  public void testCorrectNumberOfArgsEnteredForPhoneCall() {
    String[] args = new String[]{"-print", "Bob Smith", "111-111-1111", "222-222-2222", "1/1/2000", "11:12", "1/2/2000", "12:34"};
    MainMethodResult result = invokeMain(args);
    assertThat(result.getErr(), equalTo(""));
  }

  @Test
  public void testMainMethodPrintsReadme() {
    MainMethodResult result = invokeMain("-README");
    String readmeText = "README file for PhoneBill v1.0 \n" +
            "Evan Schott \n" +
            "CS410J \n" +
            "Summer 2015 \n" +
            "Project 2 \n" +
            "Project Description: \n" +
            "   This project records phone call details entered on the command line. \n" +
            "usage: java edu.pdx.cs410J.<login-id>.Project2 [options] <args>\n" +
            "args are (in this order):\n" +
            "   customer : Person whose phone bill we’re modeling\n" +
            "   callerNumber : Phone number of caller\n" +
            "   calleeNumber : Phone number of person who was called\n" +
            "   startTime : Date and time call began (24-hour time)\n" +
            "   endTime : Date and time call ended (24-hour time)\n" +
            "options are (options may appear in any order):\n" +
            "   -print : Prints a description of the new phone call\n" +
            "   -README : Prints a README for this project and exits\n" +
            "Date and time should be in the format: mm/dd/yyyy hh:mm";
    assertThat(result.getOut(), containsString(readmeText));
  }

  @Test
  public void testMainMethodPrintsPhoneCallRecord() {
    MainMethodResult result = invokeMain("-print", "Bob Smith", "111-111-1111", "222-222-2222", "1/1/2000", "11:12", "1/2/2000", "12:34");
    assertThat(result.getErr(), equalTo(""));
    assertThat(result.getOut(), containsString("Phone call from 111-111-1111 to 222-222-2222 from 1/1/2000 11:12 to 1/2/2000 12:34"));
  }

  @Test
  public void testMainMethodPrintsCallToFile() {
    MainMethodResult result = invokeMain("-print", "-textFile", "test4.txt", "Bob Smith", "111-111-1111", "222-222-2222", "1/1/2000", "11:12", "1/2/2000", "12:34");
    assertThat(result.getErr(), equalTo(""));
  }

  @Test
  public void testMainMethodFailsWithInvalidTextFileData() {
    try {
      Writer w = new FileWriter("test3.txt");
      w.write("Some bad text\n");
      w.write("Some more bad text\n");
      w.flush();
      w.close();
    } catch (IOException e) {
      e.printStackTrace();
      fail("Failed to write file");
    }
    MainMethodResult result = invokeMain("-print", "-textFile", "test3.txt", "Bob Smith", "111-111-1111", "222-222-2222", "1/1/2000", "11:12", "1/2/2000", "12:34");
    assertThat(result.getErr(), containsString("File is not a properly formatted phone bill record"));
  }

  @Test
  public void testFailsIfFileDoesNotMatchCustomerName() {
    try {
      Writer w = new FileWriter("test3.txt");
      w.write("Some bad text\n");
      w.flush();
      w.close();
    } catch (IOException e) {
      e.printStackTrace();
      fail("Failed to write file");
    }
    MainMethodResult result = invokeMain("-print", "-textFile", "test3.txt", "Bob Smith", "111-111-1111", "222-222-2222", "1/1/2000", "11:12", "1/2/2000", "12:34");
    assertThat(result.getErr(), containsString("Customer name provided does not match phone bill record on file"));
  }

  @Test
  public void testMainMethodFailsOnInvalidOption() {
    MainMethodResult result = invokeMain("-writeOut", "-textFile", "test2.txt", "Bob Smith", "111-111-1111", "222-222-2222", "1/1/2000", "11:12", "1/2/2000", "12:34");
    assertThat(result.getErr(), containsString("That option is not recognized. Please view README"));
  }



  @Test
  public void testMainMethod() {
    MainMethodResult result = invokeMain(); //put args here
    //result can return getExitCode(), getOut(), getErr()
  }

  @Test
  public void testParseReadMeReturnsTrueIfFlagSet() {
    ArrayList<String> argsList = new ArrayList<>();
    argsList.add("-README");
    CommandLineParser clp = new CommandLineParser(argsList);
    assertThat(clp.checkReadMeFlag(), equalTo(true));
  }

  @Test
  public void testParsePrintReturnsTrueIfFlagSet() {
    CommandLineParser clp = getCommandLineParser();
    assertThat(clp.checkPrintFlag(), equalTo(true));
  }

  @Test
  public void testParseFileReturnsTrueIfFlagSet() {
    CommandLineParser clp = getCommandLineParser();
    assertThat(clp.checkFileFlag(), equalTo(true));
  }

  @Test
  public void testParserReturnsCorrectNumberOfArgs() {
    CommandLineParser clp = getCommandLineParser();
    assertThat(clp.getArgs().size(), equalTo(7));
  }

  @Test
  public void testParserReturnCanCreatePhoneBill() {
    CommandLineParser clp = getCommandLineParser();
    PhoneBill bill = new PhoneBill(clp.getArgs().get(0));
    assertThat(bill.getCustomer(), equalTo("Bob Smith"));
  }

  @Test
  public void testParserReturnCanCreatePhoneCall() {
    CommandLineParser clp = getCommandLineParser();
    PhoneCall call = new PhoneCall(clp.getArgs().get(1), clp.getArgs().get(2),
                                   clp.getArgs().get(3).concat(" ").concat(clp.getArgs().get(4)),
                                   clp.getArgs().get(5) + " " + clp.getArgs().get(6));
    assertThat(call.toString(), equalTo("Phone call from 111-111-1111 to 222-222-2222 from 01/01/2000 11:12 to 1/1/2000 11:13"));
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
          assertThat(e.getMessage(), is(equalTo("Invalid date-time format: from 1234 to 1/1/2000 12:01. Should be in format MM/DD/YYYY HH:MM")));
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
      //
      PhoneBill bill = new PhoneBill(customer);
      assertThat(bill.getCustomer(), equalTo("Bob Smith"));
  }

  @Test
  public void testAddPhoneCallAddsAndReturnsPhoneCall() { //only need 1 test for both functions
    PhoneBill bill = getPhoneBill();
    assertThat(bill.getPhoneCalls().size(), equalTo(1));
  }

  @Test
  public void testAddMultiplePhoneCallsAddsAndReturnsPhoneCalls() {
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall callOne = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
    PhoneCall callTwo = new PhoneCall("123-456-7890", "098-765-4321", "2/3/1968 12:34", "2/4/1969 15:56");
    bill.addPhoneCall(callOne);
    bill.addPhoneCall(callTwo);
    assertThat(bill.getPhoneCalls().size(), equalTo(2));
  }

  @Test
  public void testPrintPhoneBillDescriptionShouldPrintPhoneBillDescription() {
    PhoneBill bill = getPhoneBill();
    String s = "Bob Smith's phone bill with 1 phone calls";
    assertThat(bill.toString(), equalTo(s));
  }


  @Test
  public void testDumpThrowsIOException() {
    PhoneBill bill = getPhoneBill();
    TextDumper td = new TextDumper();
    try {
      td.dump(bill);
    } catch (IOException e) {
      assertThat(e.getMessage(), is(equalTo("File name not set")));
    }
  }

  @Test
  public void testDumpWritesToFile() {
    PhoneBill bill = getPhoneBill();
    TextDumper td = new TextDumper();
    td.setFile("test1.txt");
    try {
      td.dump(bill);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  //dependency: testTextParserReadsFileData
  public void testDumpWritesMultipleCallPhoneBill() {
    PhoneBill bill = getPhoneBill();
    PhoneCall call = new PhoneCall("123-456-7890", "098-765-4321", "1/1/1911 11:23", "1/1/1911 11:24");
    bill.addPhoneCall(call);
    TextDumper td = new TextDumper();
    td.setFile("test2.txt");
    try {
      td.dump(bill);
    } catch(IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testTextParserSetFileNameSetsFileName() {
    TextParser tp = new TextParser();
    tp.setFile("test2.txt");
    assertThat(tp.fileName, equalTo("test2.txt"));
  }

  @Test
  public void testTextParserReadsFileData() {
    testDumpWritesMultipleCallPhoneBill(); //creates call text file with 2 calls
    TextParser tp = new TextParser();
    tp.setFile("test2.txt");
    try {
      PhoneBill bill = (PhoneBill) tp.parse();
      assertThat(bill.toString(), equalTo("Bob Smith's phone bill with 2 phone calls"));
    } catch (ParserException e) {
      fail(e.getMessage());
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

  /**
   * Method for created a PhoneBill for test calls
   * @return returns a phone bill
   */
  private PhoneBill getPhoneBill() {
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
    bill.addPhoneCall(call);
    return bill;
  }

  /**
   * Method for creating a CommandLineParser for tests
   * @return CommandLineParser
   */
  private CommandLineParser getCommandLineParser() {
    ArrayList<String> argsList = new ArrayList<>();
    Collections.addAll(argsList,"-print", "-textFile", "text.txt", "Bob Smith", "111-111-1111", "222-222-2222", "01/01/2000", "11:12",
            "1/1/2000", "11:13" );
    //String args = "-print -textFile text.txt Bob Smith 111-111-1111 222-222-2222 01/01/2000 11:12 1/1/2000 11:13";
    //Collections.addAll(argsList, args.split(" "));
    return new CommandLineParser(argsList);
  }


}