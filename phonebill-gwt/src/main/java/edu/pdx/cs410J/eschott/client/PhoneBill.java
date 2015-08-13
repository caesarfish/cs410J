package edu.pdx.cs410J.eschott.client;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PhoneBill extends AbstractPhoneBill
{
  private ArrayList<PhoneCall> calls = new ArrayList<PhoneCall>();
  private String customerName;

  /**
   * Method to create phone bill
   * @param customerName
   */
  public PhoneBill(String customerName) {
    this.customerName = customerName;
  }

  /**
   * Default constructor needed for extending AbstractPhoneBill
   */
  public PhoneBill() {
  }

  @Override
  public String getCustomer() {
    return customerName;
  }

  @Override
  public void addPhoneCall(AbstractPhoneCall call) {
    for(PhoneCall o : calls) {
      if (call.equals(o)){
        throw new PhoneCallAlreadyExistsException("Call already exists in phone bill");
      }
    }
    this.calls.add((PhoneCall)call);
    Collections.sort(calls);

  }

  @Override
  public Collection getPhoneCalls() {
    return this.calls;
  }

  public CellTable<PhoneCall> prettyPrint() {
    List<PhoneCall> calls = (List) this.getPhoneCalls();

    //Create CellTable
    CellTable<PhoneCall> table = new CellTable<>();

    //Add title row

    //Add CallFrom column
    TextColumn<PhoneCall> callFromColumn = new TextColumn<PhoneCall>() {
      @Override
      public String getValue(PhoneCall phoneCall) {
        return phoneCall.getCaller();

      }
    };
    table.addColumn(callFromColumn, "Call From:");

    //Add Call TO column
    TextColumn<PhoneCall> callToColumn = new TextColumn<PhoneCall>() {

      @Override
      public String getValue(PhoneCall phoneCall) {
        return phoneCall.getCallee();
      }
    };
    table.addColumn(callToColumn, "Call To:");

    //Add Call Start column
    TextColumn<PhoneCall> startTimeColumn = new TextColumn<PhoneCall>() {

      @Override
      public String getValue(PhoneCall phoneCall) {
        DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yy hh:mm a");
        return dateFormat.format(phoneCall.getStartTime());
      }
    };
    table.addColumn(startTimeColumn, "Start Time:");
    startTimeColumn.setSortable(true);

    //Add Call End column
    TextColumn<PhoneCall> endTimeColumn = new TextColumn<PhoneCall>() {

      @Override
      public String getValue(PhoneCall phoneCall) {
        DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yy hh:mm a");
        return dateFormat.format(phoneCall.getEndTime());
      }
    };
    table.addColumn(endTimeColumn, "End Time:");

    //Add Call Duration column
    NumberCell numCell = new NumberCell();
    TextColumn<PhoneCall> callDurationColumn = new TextColumn<PhoneCall>() {

      @Override
      public String getValue(PhoneCall phoneCall) {
        Long l = (phoneCall.getEndTime().getTime() - phoneCall.getStartTime().getTime())/60000;
        String s = l.toString();
        s += " minutes";
        return s;
      }
    };
    table.addColumn(callDurationColumn, "Call Duration:");

    table.setRowCount(calls.size(), true);
    table.setRowData(0, calls);

    return table;

  }


}

