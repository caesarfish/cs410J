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
    private String startTime;
    private String endTime;



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
        startTime = callStart;
        endTime = callEnd;
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
      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
      Date date = null;
      try {
        date = dateFormat.parse(this.startTime);
      } catch (ParseException e) {
        e.printStackTrace();
      }
      return date;
    }

    /**
     * Method for returning startTime
     * @return startTime
     */
    public String getStartTimeString () {
        return this.startTime;
    }

    @Override
    public Date getEndTime() {
      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
      Date date = null;
      try {
        date = dateFormat.parse(this.endTime);
      } catch (ParseException e) {
        e.printStackTrace();
      }
      return date;
    }

    /**
     * Method for returning endTime
     * @return endTime
     */
    public String getEndTimeString() {
        return this.endTime;
    }






}
