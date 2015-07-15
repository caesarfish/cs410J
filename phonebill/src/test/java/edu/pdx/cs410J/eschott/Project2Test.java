package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import edu.pdx.cs410J.InvokeMainTestCase;

import java.io.IOException;
import java.text.ParseException;

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
    TextParser tp = new TextParser();
    tp.setFile("test2.txt");
    try {
      PhoneBill bill = (PhoneBill) tp.parse();
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


}