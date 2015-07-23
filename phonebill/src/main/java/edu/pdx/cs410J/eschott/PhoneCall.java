package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneCall;
import edu.pdx.cs410J.ParserException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Override
    public Date getStartTime() {
     return startTime;
    }

    /**
     * Method for returning startTime
     * @return startTime
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
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    //dateFormat.setLenient(false);
    Date date = null;
    try {
      date = dateFormat.parse(stringToConvert);
    } catch (ParseException e) {
      //System.err.println("Invalid date-time format");
      System.err.println("Should not be seeing this message");
    }
    return date;
  }

  /**
   * Method to convert date to string
   */
  private String dateToString(Date dateToConvert) {
      DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
      return dateFormat.format(dateToConvert);
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
    /*Date date1 = stringToDate(dateToString(this.getStartTime())); //necessary for proper YY to YYYY comparison
    Date date2 = stringToDate(dateToString(o.getStartTime()));
    int dateComparison = date1.compareTo(date2);*/
    int dateComparison = this.getStartTime().compareTo(o.getStartTime());
    if (dateComparison == 0) {
      dateComparison = this.getCallee().compareTo(o.getCallee());
    }
    return dateComparison;
  }
}
