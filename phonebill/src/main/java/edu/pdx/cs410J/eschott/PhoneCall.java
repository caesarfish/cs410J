package edu.pdx.cs410J.eschott;

import edu.pdx.cs410J.AbstractPhoneCall;
/**
 * Created by Evan on 7/7/2015.
 * Public class PhoneCall extends AbstractPhoneCall
 */
public class PhoneCall extends AbstractPhoneCall  {
    private String caller;

    /**
     * Default constructor
     */
    public PhoneCall(){
    }

    public PhoneCall(String c){
        caller = c;
    }

    public String getCaller() {
        return this.caller;
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
