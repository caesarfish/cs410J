package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Evan on 7/7/2015.
 * Public class PhoneCall extends AbstractPhoneCall
 */
public class PhoneCall extends AbstractPhoneCall  {
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
     */
    public PhoneCall(String caller, String callee, String callStart, String callEnd){
        callerNumber = caller;
        calleeNumber = callee;
        startTime = stringToDate(callStart);
        endTime = stringToDate(callEnd);
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
  private Date stringToDate(String stringToConvert) {
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Date date = null;
    try {
      date = dateFormat.parse(stringToConvert);
    } catch (ParseException e) {
      System.err.println("Invalid date-time format");
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




}
