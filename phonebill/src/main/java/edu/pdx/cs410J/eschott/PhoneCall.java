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
     */
    public PhoneCall(String caller, String callee, String callStart, String callEnd) {
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
  private Date stringToDate(String stringToConvert)  {
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    Date date = null;
    try {
      date = dateFormat.parse(stringToConvert);
    } catch (ParseException e) {
      System.err.println("Invalid date-time format");
      e.printStackTrace();
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
   * Compares this object with the specified object for order.  Returns a
   * negative integer, zero, or a positive integer as this object is less
   * than, equal to, or greater than the specified object.
   * <p>
   * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
   * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
   * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
   * <tt>y.compareTo(x)</tt> throws an exception.)
   * <p>
   * <p>The implementor must also ensure that the relation is transitive:
   * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
   * <tt>x.compareTo(z)&gt;0</tt>.
   * <p>
   * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
   * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
   * all <tt>z</tt>.
   * <p>
   * <p>It is strongly recommended, but <i>not</i> strictly required that
   * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
   * class that implements the <tt>Comparable</tt> interface and violates
   * this condition should clearly indicate this fact.  The recommended
   * language is "Note: this class has a natural ordering that is
   * inconsistent with equals."
   * <p>
   * <p>In the foregoing description, the notation
   * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
   * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
   * <tt>0</tt>, or <tt>1</tt> according to whether the value of
   * <i>expression</i> is negative, zero or positive.
   *
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
    Date date1 = stringToDate(dateToString(this.getStartTime())); //necessary for proper YY to YYYY comparison
    Date date2 = stringToDate(dateToString(o.getStartTime()));
    int dateComparison = date1.compareTo(date2);
    //int dateComparison = this.getStartTime().compareTo(((PhoneCall)o).getStartTime());
    if (dateComparison == 0) {
      dateComparison = this.getCallee().compareTo(((PhoneCall)o).getCallee());
    }
    return dateComparison;
  }
}
