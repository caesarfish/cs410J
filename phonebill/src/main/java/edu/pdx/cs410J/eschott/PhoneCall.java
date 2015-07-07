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
        if(validatePhoneNumber(caller)) {
            callerNumber = caller;
        }
        if(validatePhoneNumber(callee)) {
            calleeNumber = callee;
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

    protected boolean validatePhoneNumber(String phoneNumber){
        if(phoneNumber.matches("^[0-9]{3}-[0-9]{3}-[0-9]{4}$"))
            return true;
        else
            throw new IllegalArgumentException("Phone number is not valid!");
    }

}
