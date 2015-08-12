package edu.pdx.cs410J.eschott.client;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;

import java.util.List;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {

  private TextBox customerNameField;
  private TextBox callFrom;
  private TextBox callTo;
  private TextBox callStart;
  private TextBox callEnd;
  private RichTextArea callText;

  public void onModuleLoad() {
    callText = new RichTextArea();
    callText.setWidth("1200px");
    callText.setHeight("800px");
    DockPanel dock = new DockPanel();

    VerticalPanel v2 = createVertCallInfo();

    TabPanel tabs = new TabPanel();
    tabs.setWidth("600px");
    tabs.add(v2, "Call Information");
    tabs.selectTab(0);

    dock.add(tabs, DockPanel.NORTH);

    RootPanel rootPanel = RootPanel.get();
    rootPanel.add(dock);
  }

  private VerticalPanel createVertCallInfo() {
    customerNameField = new TextBox();
    callFrom = new TextBox();
    callTo = new TextBox();
    callStart = new TextBox();
    callEnd = new TextBox();

    //Set default values for testing
    //Todo: remove default values
    callFrom.setText("111-111-1111");
    callTo.setText("222-222-2222");
    callStart.setText("1/1/2015 1:00 pm");
    callEnd.setText("1/1/2015 2:00 pm");

    HorizontalPanel hCustName = new HorizontalPanel();
    hCustName.add(new Label("Enter Customer Name: "));
    hCustName.add(customerNameField);

    HorizontalPanel hCallFrom = new HorizontalPanel();
    hCallFrom.add(new Label("Call from: "));
    hCallFrom.add(callFrom);

    HorizontalPanel hCallTo = new HorizontalPanel();
    hCallTo.add(new Label("Call to: "));
    hCallTo.add(callTo);

    HorizontalPanel hCallStart = new HorizontalPanel();
    hCallStart.add(new Label("Call start: "));
    hCallStart.add(callStart);

    HorizontalPanel hCallEnd = new HorizontalPanel();
    hCallEnd.add(new Label("Call end: "));
    hCallEnd.add(callEnd);

    Button button = new Button("Get Customer phone bill");
    button.addClickHandler(createPhoneCallOnServer());

    VerticalPanel vert = new VerticalPanel();
    vert.add(hCustName);
    vert.add(hCallFrom);
    vert.add(hCallTo);
    vert.add(hCallStart);
    vert.add(hCallEnd);
    vert.add(button);

    return vert;
  }

  public CellTable<PhoneCall> prettyPrint(final AbstractPhoneBill phoneBill) {
    PhoneBill bill = (PhoneBill) phoneBill;
    List<PhoneCall> calls = (List) phoneBill.getPhoneCalls();

    //Create CellTable
    CellTable<PhoneCall> table = new CellTable<>();

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
        return phoneCall.getStartTimeString();
      }
    };
    table.addColumn(startTimeColumn, "Start Time:");

    //Add Call End column
    TextColumn<PhoneCall> endTimeColumn = new TextColumn<PhoneCall>() {

      @Override
      public String getValue(PhoneCall phoneCall) {
        return phoneCall.getEndTimeString();
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

  private ClickHandler createPhoneCallOnServer() {
    return new ClickHandler() {
      public void onClick( ClickEvent clickEvent )
      {
        String customerName = customerNameField.getText();
        PhoneCall call = new PhoneCall();
        try {
          call = new PhoneCall(callFrom.getText(), callTo.getText(), callStart.getText(), callEnd.getText());
        } catch (ParserException e) {
          Window.alert(e.getMessage());
        }

        PingServiceAsync async = GWT.create(PingService.class);
        async.ping(customerName, call, new AsyncCallback<AbstractPhoneBill>() {

          public void onFailure(Throwable ex) {
            Window.alert(ex.toString());
          }

          public void onSuccess(AbstractPhoneBill phonebill) {
            PhoneBill bill = (PhoneBill) phonebill;
            CellTable<PhoneCall> table = prettyPrint(phonebill);
            RootPanel.get().add(table);
          }
        });
        }
    };
  }


}
