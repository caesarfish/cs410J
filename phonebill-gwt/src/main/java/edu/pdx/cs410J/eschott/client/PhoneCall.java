package edu.pdx.cs410J.eschott.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.ParserException;

import java.util.Date;

/**
 * Created by Evan on 7/7/2015.
 * Public class PhoneCall extends AbstractPhoneCall
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall>  {
  private String callerNumber;
  private String calleeNumber;
  private Date startTime;
  private Date endTime;

  /**
   * Default constructor
   */
  public PhoneCall(){
  }

  /**
   * Primary constructor for PhoneCall object
   * @param caller - the number of the person calling
   * @param callee - the number being called
   * @param callStart - datetime call begins
   * @param callEnd - datetime call ends
   * @throws ParserException from Validator class for caller to handle
   */
  public PhoneCall(String caller, String callee, String callStart, String callEnd) throws ParserException {
    if (Validator.validatePhoneNumber(caller) && Validator.validatePhoneNumber(callee)) {
      callerNumber = caller;
      calleeNumber = callee;
    }
    if (Validator.validateDate(callStart) && Validator.validateDate(callEnd)) {
      startTime = stringToDate(callStart);
      endTime = stringToDate(callEnd);
    }
  }

  /**
   * Method for returning callerNumber
   * @return callerNumber
   */
  public String getCaller() {
    return this.callerNumber;
  }

  /**
   * Method for returning calleeNumber
   * @return calleeNumber
   */
  public String getCallee() {
    return this.calleeNumber;
  }

  /**
   * Method for return start time of call
   * @return start time in Date format
   */
  @Override
  public Date getStartTime() {
    return startTime;
  }

  /**
   * Method for returning startTime
   * @return startTime as string
   */
  public String getStartTimeString () {
    return dateToString(getStartTime());
  }

  @Override
  public Date getEndTime() {
    return endTime;
  }

  /**
   * Method for returning endTime
   * @return endTime
   */
  public String getEndTimeString() {
    return dateToString(getEndTime());
  }

  /**
   * Method to convert strings to date
   */
  private Date stringToDate(String stringToConvert)  {
    DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yy hh:mm a");
    return dateFormat.parse(stringToConvert);
  }

  /**
   * Method to convert date to string
   */
  private String dateToString(Date dateToConvert) {
    return dateToConvert.toString();
  }


  /**
   * @param o the object to be compared.
   * @return a negative integer, zero, or a positive integer as this object
   * is less than, equal to, or greater than the specified object.
   * @throws NullPointerException if the specified object is null
   * @throws ClassCastException   if the specified object's type prevents it
   *                              from being compared to this object.
   */
  @SuppressWarnings("NullableProblems")
  @Override
  public int compareTo(PhoneCall o) {
    long time1 = this.getStartTime().getTime();
    long time2 = o.getStartTime().getTime();
    if (time1 == time2) {
      return this.getCallee().compareToIgnoreCase(o.getCallee());
    } else if (time1 < time2) {
      return -1;
    } else {
      return 1;
    }

  }

  /**
   * Overrides equals
   * checks for equality of calls based on startTime and callee number
   * @param o object to be compared to for equality
   * @return true if phone calls are equal
   */
  @Override
  public boolean equals(Object o) {
    return (o instanceof PhoneCall) && ((PhoneCall) o).getStartTimeString().equals(this.getStartTimeString()) &&
            ((PhoneCall) o).getCallee().equals(this.getCallee());
  }

  /**
   * Overrides hashCode()
   * @return hash
   */
  @Override
  public int hashCode() {
    int hash = Integer.parseInt(this.calleeNumber.substring(0, 2));
    hash = (hash * 7)/3;
    return hash;
  }
}
