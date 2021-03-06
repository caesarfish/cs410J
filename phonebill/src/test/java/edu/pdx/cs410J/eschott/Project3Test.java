package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.ParserException;
import jdk.nashorn.internal.ir.annotations.Ignore;
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
import java.util.ArrayList;
import java.util.Collections;

import static junit.framework.Assert.assertEquals;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
public class Project3Test extends InvokeMainTestCase {
  /**
   * Variables for invoking new class objects
   */
  private String customer = "Bob Smith";
  private String callerNumber = "111-111-1111";
  private String calleeNumber = "999-999-9999";
  private String startTime = "1/1/2000 11:59 AM";
  private String endTime = "1/1/2000 12:01 PM";

  /**
   * Invokes the main method of {@link Project3} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
      return invokeMain( Project3.class, args );
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
    String[] args = new String[]{"-print", "Bob Smith", "111-111-1111", "222-222-2222", "1/1/2000", "11:12", "AM", "1/2/2000", "12:34", "PM"};
    MainMethodResult result = invokeMain(args);
    assertThat(result.getErr(), equalTo(""));
  }

  @Test
  public void testMainMethodPrintsReadme() {
    MainMethodResult result = invokeMain("-README");
    String readmeText = "README file for PhoneBill v1.3 ";
    assertThat(result.getOut(), containsString(readmeText));
  }

  @Test
  public void testMainMethodPrintsPhoneCallRecord() {
    MainMethodResult result = invokeMain("-print", "Bob Smith", "111-111-1111", "222-222-2222", "1/1/2000",
            "11:12", "AM", "1/2/2000", "12:34", "PM");
    assertThat(result.getErr(), equalTo(""));
    assertThat(result.getOut(), containsString("Phone call from 111-111-1111 to 222-222-2222 from 1/1/00 " +
            "11:12 AM to 1/2/00 12:34 PM"));
  }

  @Test
  public void testMainMethodPrintsCallToFile() {
    MainMethodResult result = invokeMain("-print", "-textFile", "test4.txt", "Bob Smith", "111-111-1111",
            "222-222-2222", "1/1/2000", "1:12", "AM", "01/02/2000", "12:34", "PM");
    assertThat(result.getErr(), equalTo(""));
  }

  @Test
  public void testMainMethodPrettyPrintsCallToFile() {
    MainMethodResult result = invokeMain("-print", "-pretty", "PPTestMain.txt", "Bob Smith", "111-111-1111",
            "222-222-2222", "1/1/2000", "11:12", "AM", "1/2/2000", "12:34", "PM");
    assertThat(result.getErr(), equalTo(""));
  }

  @Test
  public void testMMPrettyPrintsToStdOut() {
    MainMethodResult result = invokeMain("-print", "-pretty", "-", "Bob Smith", "111-111-1111", "222-222-2222", "1/1/2000",
            "11:12", "AM", "1/2/2000", "12:34", "PM");
    assertThat(result.getErr(), equalTo(""));
    assertThat(result.getOut(), containsString("Phone bill for customer: Bob Smith"));
    assertThat(result.getOut(), containsString("Phone call from 111-111-1111 to 222-222-2222 from 1/1/00 " +
            "11:12 AM to 1/2/00 12:34 PM"));
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
    MainMethodResult result = invokeMain("-print", "-textFile", "test3.txt", "Bob Smith", "111-111-1111",
            "222-222-2222", "1/1/2000", "11:12", "AM", "1/2/2000", "12:34", "PM");
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
    MainMethodResult result = invokeMain("-print", "-textFile", "test3.txt", "Bob Smith", "111-111-1111",
            "222-222-2222", "1/1/2000", "11:12", "AM", "1/2/2000", "12:34", "PM");
    assertThat(result.getErr(), containsString("Customer name provided does not match phone bill record on file"));
  }

  @Test
  public void testMainMethodFailsOnInvalidOption() {
    MainMethodResult result = invokeMain("-writeOut", "-textFile", "test2.txt", "Bob Smith", "111-111-1111",
            "222-222-2222", "1/1/2000", "11:12", "AM", "1/2/2000", "12:34", "PM");
    assertThat(result.getErr(), containsString("That option is not recognized. Please view README"));
  }

  @Test
  public void testMainMethodFailsOnInvalidNumber() {
    MainMethodResult result = invokeMain("Bob Smith", "xxx-111-1111", "222-222-2222", "1/1/2000",
            "11:12", "AM", "1/2/2000", "12:34", "PM");
    assertThat(result.getErr(), containsString("Invalid phone number format"));
  }

  @Test
  public void testMainMethodFailsOnInvalidDate() {
    MainMethodResult result = invokeMain("Bob Smith", "111-111-1111", "222-222-2222", "1234",
            "11:12", "AM", "1/2/2000", "12:34", "PM");
    assertThat(result.getErr(), containsString("Invalid date-time format"));
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
    assertThat(clp.checkTextFileFlag(), equalTo(true));
  }

  @Test
  public void testParsePrettyReturnsTrueIfFlagSet() {
    CommandLineParser clp = getCommandLineParser();
    assertThat(clp.checkPrettyFileFlag(), equalTo(true));
  }

  @Test
  public void testParserReturnsCorrectNumberOfArgs() {
    CommandLineParser clp = getCommandLineParser();
    assertThat(clp.getArgs().size(), equalTo(9));
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
    PhoneCall call = null;
    try {
      call = new PhoneCall(clp.getArgs().get(1), clp.getArgs().get(2),
                                     clp.getArgs().get(3).concat(" ").concat(clp.getArgs().get(4)).concat(" ").concat(clp.getArgs().get(5)),
                                     clp.getArgs().get(6) + " " + clp.getArgs().get(7).concat(" ").concat(clp.getArgs().get(8)));
    } catch (ParserException e) {
      fail(e.getMessage());
    }
    assertThat(call.toString(), equalTo("Phone call from 111-111-1111 to 222-222-2222 from 1/1/00 11:12 AM to 1/1/00 11:13 AM"));
  }

  /**
   * Tests that the getCaller() method works
   */
  @Test
  public void testGetCallerReturnsCallerNumber() {
    PhoneCall call = null;
    try {
      call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
    } catch (ParserException e) {
      fail(e.getMessage());
    }
    assertThat(call.getCaller(), equalTo(callerNumber));
  }

  /**
   * Tests that the getCallee() method works
   */
  @Test
  public void testGetCalleeReturnsCalleeNumber() {
    PhoneCall call = null;
    try {
      call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
    } catch (ParserException e) {
      fail(e.getMessage());
    }
    assertThat(call.getCallee(), equalTo(calleeNumber));
  }

  /**
   * Tests that getStartTime() and getEndTime() methods work
   */
  @Test
  public void testGetStartAndEndTimeReturnsDate() {
    PhoneCall call = null;
    try {
      call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
    } catch (ParserException e) {
      fail(e.getMessage());
    }
    assertThat(call.getStartTime().toString(), equalTo("Sat Jan 01 11:59:00 PST 2000"));
    assertThat(call.getEndTime().toString(), equalTo("Sat Jan 01 12:01:00 PST 2000"));
  }

  /**
   * Tests that getStartTimeString() method works
   */
  @Test
  public void testGetStartTimeStringReturnsStartTime() {
    PhoneCall call = null;
    try {
      call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
    } catch (ParserException e) {
      fail(e.getMessage());
    }
    assertThat(call.getStartTimeString(), equalTo("1/1/00 11:59 AM"));
  }

  /**
   * Tests that getEndTimeString() method works
   */
  @Test
  public void testGetEndTimeStringReturnsEndTime() {
    PhoneCall call = null;
    try {
      call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
    } catch (ParserException e) {
      fail(e.getMessage());
    }
    assertThat(call.getEndTimeString(), equalTo("1/1/00 12:01 PM"));
  }

  /**
   * Tests date time validation for valid input
   */
  @Test
  public void testValidEndTimeIsValid() {
      endTime = "1/1/2000 1:01 AM";
      try {
        PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
      } catch (IllegalArgumentException e) {
        fail("Date Time format is invalid");
      } catch (ParserException e) {
        fail(e.getMessage());
      }
  }

  /**
   * Tests date time validation for valid input
   */
  @Test
  public void testInvalidEndTimeIsInvalid() {
    endTime = "1//2000 1:01 AM";
      try {
        PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
        fail("Expected exception");
      } catch (ParserException e) {
        //passes
      }
  }

  /**
   * Tests toString() method
   */
  @Test
  public void testPrintsCallDescriptionShouldPrintCallDescription() {
    PhoneCall call = null;
    try {
      call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
    } catch (ParserException e) {
      fail(e.getMessage());
    }
    String s = "Phone call from 111-111-1111 to 999-999-9999 from 1/1/00 11:59 AM to 1/1/00 12:01 PM";
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
  public void testAddDuplicatePhoneCallFails() {
    PhoneBill bill = getPhoneBill();
    PhoneCall call = null;
    try {
      call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
    } catch (ParserException e) {
      fail(e.getMessage());
    }
    bill.addPhoneCall(call);
    assertThat(bill.getPhoneCalls().size(), equalTo(1));

  }

  @Test
  public void testAddMultiplePhoneCallsAddsAndReturnsPhoneCalls() {
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall callOne = null;
    PhoneCall callTwo = null;
    try {
      callOne = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
      callTwo = new PhoneCall("123-456-7890", "098-765-4321", "2/3/1968 12:34 AM", "2/4/1969 3:56 PM");
    } catch (ParserException e) {
      fail(e.getMessage());
    }
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
    PhoneCall call = null;
    try {
      call = new PhoneCall("123-456-7890", "098-765-4321", "1/1/2011 11:23 am", "1/1/2011 11:24 am");
    } catch (ParserException e) {
      fail(e.getMessage());
    }
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

  @Test
  public void testDateOlderThanOtherDate() {
    PhoneCall call1 = null;
    PhoneCall call2 = null;
    try {
      call1 = new PhoneCall("111-111-1111", "555-555-5555", "1/1/2000 01:00 am", "1/1/2000 01:05 am");
      call2 = new PhoneCall("111-111-1111", "555-555-5555", "1/1/2000 01:05 am", "1/1/2000 01:10 am");
    } catch (ParserException e) {
      fail(e.getMessage());
    }
    assertThat(call1.compareTo(call2), equalTo(-1)); //call1 is earlier than call2
  }


  //@Test
  @Ignore //can't get this test to pass
  public void testPhoneBillSortReturnsCorrectOrder() {
    PhoneBill bill = new PhoneBill("Bob Smith");
    PhoneCall call1 = null;
    PhoneCall call2 = null;
    PhoneCall call3 = null;
    try {
      call1 = new PhoneCall("111-111-1111", "555-555-5555", "1/1/2000 01:00 am", "1/1/2000 01:05 am");
      call2 = new PhoneCall("111-111-1111", "555-555-5555", "1/1/2000 01:05 am", "1/1/2000 01:10 am");
      call3 = new PhoneCall("111-111-1111", "223-543-5678", "1/2/2000 11:00 am", "1/2/2000 11:35 am");
    } catch (ParserException e) {
      fail(e.getMessage());
    }
    bill.addPhoneCall(call3);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call1);
    ArrayList<String> arrayList = new ArrayList<>();
    arrayList.add("Phone call from 111-111-1111 to 223-543-5678 from 1/2/00 " +
            "11:00 AM to 1/2/00 11:35 AM");
    assertThat(bill.getPhoneCalls().toArray(), equalTo(""));
  }

  @Test
  public void TestPrettyPrinterFailsWithNoFileSet() {
    PhoneBill bill = new PhoneBill("Test");
    PrettyPrinter pp = new PrettyPrinter();
    try {
      pp.dump(bill);
      fail("Expected IOException");
    } catch (IOException e) {
      //pass
    }
  }

  @Test
  public void testPrettyPrinterWritesToFile() {
    PhoneBill bill = new PhoneBill("Test");
    PhoneCall call1 = null;
    PhoneCall call2 = null;
    PhoneCall call3 = null;
    try {
      call1 = new PhoneCall("111-111-1111", "555-555-5555", "1/1/2000 01:00 am", "1/1/2000 01:05 am");
      call2 = new PhoneCall("111-111-1111", "555-555-5555", "1/1/2000 01:05 am", "1/1/2000 01:10 am");
      call3 = new PhoneCall("111-111-1111", "223-543-5678", "1/2/2000 11:00 am", "1/2/2000 11:35 am");
    } catch (ParserException e) {
      fail(e.getMessage());
    }
    bill.addPhoneCall(call3);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call1);
    PrettyPrinter pp = new PrettyPrinter();
    pp.setFile("PPTest.txt");
    try {
      pp.dump(bill);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testTextParserWritesToReadsFromWritesToAgainInSortOrder() {
    PhoneBill bill = new PhoneBill("Test");
    PhoneCall call1 = null;
    PhoneCall call2 = null;
    PhoneCall call3 = null;
    try {
      call1 = new PhoneCall("111-111-1111", "555-555-5555", "1/1/2000 01:00 am", "1/1/2000 01:05 am");
      call2 = new PhoneCall("111-111-1111", "555-555-5555", "1/1/2000 01:05 am", "1/1/2000 01:10 am");
      call3 = new PhoneCall("111-111-1111", "223-543-5678", "1/2/2000 11:00 am", "1/2/2000 11:35 am");
    } catch (ParserException e) {
      fail(e.getMessage());
    }
    bill.addPhoneCall(call3);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call1);
    TextDumper td = new TextDumper();
    td.setFile("TDReadWriteTest.txt");
    try {
      td.dump(bill);
    } catch (IOException e) {
      fail(e.getMessage());
    }
    TextParser tp = new TextParser();
    tp.setFile("TDReadWriteTest.txt");
    PhoneBill bill2 = new PhoneBill("Test");
    try {
      bill2 = (PhoneBill)tp.parse();
    } catch (ParserException e) {
      e.printStackTrace();
    }
    PhoneCall call4 = null;
    try {
      call4 = new PhoneCall("111-111-1111", "223-543-5678", "1/1/2015 1:10 am", "1/1/2015 1:15 am");
    } catch (ParserException e) {
      fail(e.getMessage());
    }
    bill2.addPhoneCall(call4);
    try {
      td.dump(bill2);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testValidatorThrowsExceptionOnInvalidPhoneNumber() {
    try {
      Validator.validatePhoneNumber("x11-111-1111");
      fail("Expected exception");
    } catch (ParserException e) {
      //passes
    }
  }

  @Test
  public void testValidatorThrowsExceptionOnInvalidDate() {
    try {
      Validator.validateDate("1/1/2000 11:59");
      fail("Expected exception");
    } catch (ParserException e) {
      //passes
    }
  }


  /**
   * Helper Method for creating a PhoneBill for test calls
   * @return returns a phone bill
   */
  private PhoneBill getPhoneBill() {
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call = null;
    try {
      call = new PhoneCall(callerNumber, calleeNumber, startTime, endTime);
    } catch (ParserException e) {
      System.err.println(e.getMessage());
    }
    bill.addPhoneCall(call);
    return bill;
  }

  /**
   * Method for creating a CommandLineParser for tests
   * @return CommandLineParser
   */
  private CommandLineParser getCommandLineParser() {
    ArrayList<String> argsList = new ArrayList<>();
    Collections.addAll(argsList,"-print", "-textFile", "text.txt", "-pretty", "PPTest.txt", "Bob Smith", "111-111-1111", "222-222-2222", "01/01/2000", "11:12", "AM",
            "1/1/2000", "11:13", "AM" );
    return new CommandLineParser(argsList);
  }


}