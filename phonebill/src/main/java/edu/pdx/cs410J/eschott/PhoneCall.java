package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneCall;
/**
 * Created by Evan on 7/7/2015.
 * Public class PhoneCall extends AbstractPhoneCall
 */
public class PhoneCall extends AbstractPhoneCall  {
    private String callerNumber;
    private String calleeNumber;



    /**
     * Default constructor
     */
    public PhoneCall(){
    }

    public PhoneCall(String c){
        if(c.matches("^[0-9]{3}-[0-9]{3}-[0-9]{4}$")) {
            callerNumber = c;
        } else {
            throw new IllegalArgumentException("Caller's phone number is not valid!");
        }
    }

    public String getCaller() {
        return this.callerNumber;
    }

    public String getCallee() {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public String getStartTimeString () {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public String getEndTimeString() {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

}
