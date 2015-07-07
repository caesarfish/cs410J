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

    public PhoneCall(String caller, String callee){
        if(caller.matches("^[0-9]{3}-[0-9]{3}-[0-9]{4}$")) {
            callerNumber = caller;
        } else {
            throw new IllegalArgumentException("Caller's phone number is not valid!");
        }

        if(callee.matches("^[0-9]{3}-[0-9]{3}-[0-9]{4}$")) {
            calleeNumber = callee;
        } else {
            throw new IllegalArgumentException("Caller's phone number is not valid!");
        }

    }

    public String getCaller() {
        return this.callerNumber;
    }

    public String getCallee() {
        return this.calleeNumber;
    }

    public String getStartTimeString () {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public String getEndTimeString() {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

}
