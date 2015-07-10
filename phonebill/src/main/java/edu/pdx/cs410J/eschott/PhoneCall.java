package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneCall;

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
        if(!validatePhoneNumber(caller) || !validatePhoneNumber(callee))
            throw new IllegalArgumentException("Phone number is not valid!");

        if(!validateDateTime(callStart) || !validateDateTime(callEnd))
            throw new IllegalArgumentException("Invalid date-time format!");

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

    /**
     * Method for returning startTime
     * @return startTime
     */
    public String getStartTimeString () {
        return this.startTime;
    }

    /**
     * Method for returning endTime
     * @return endTime
     */
    public String getEndTimeString() {
        return this.endTime;
    }

    /**
     * Method uses a regex to validate phone number format
     * @param phoneNumber should be in format ###-###-####
     * @return true if phoneNumber matches regex
     */
    public boolean validatePhoneNumber(String phoneNumber){
        return phoneNumber.matches("^[0-9]{3}-[0-9]{3}-[0-9]{4}$");
    }

    public boolean validateDateTime(String dateToValidate) {
        SimpleDateFormat s = new SimpleDateFormat("mm/dd/yyyy hh:mm");
        s.setLenient(false);
        try {
            Date date = s.parse(dateToValidate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }




}
