package edu.pdx.cs410J.eschott.client;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Command;
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

  private TextBox customerAddNameField;
  private TextBox callFrom;
  private TextBox callTo;
  private TextBox callStart;
  private TextBox callEnd;
  private DeckPanel deck;
  private TextBox customerLookupNameField;
  private TextBox startTimeLookupField;
  private TextBox endTimeLookupField;

  public void onModuleLoad() {
    deck = new DeckPanel();

    //Create the add call widget and add to deck
    final VerticalPanel vertCallInfo = createVertCallAdd();
    deck.add(vertCallInfo);

    final VerticalPanel vertDisplayCall = createVertDisplayCall();
    deck.add(vertDisplayCall);

    final VerticalPanel vertDisplayHelp = createVertDisplayHelp();
    deck.add(vertDisplayHelp);

    //Create menu bar
    MenuBar taskMenu = new MenuBar();
    //Build Menu commands
    Command cmdShowAddCall = new Command() {
      public void execute() {
        deck.showWidget(deck.getWidgetIndex(vertCallInfo));
      }
    };
    Command cmdShowPhoneBillDisplay = new Command() {
      public void execute() {
        deck.showWidget(deck.getWidgetIndex(vertDisplayCall));
      }
    };


    //Create submenus
    MenuBar helpMenu = new MenuBar(true);
    Command cmdShowHelp = new Command() {
      public void execute() {
        deck.showWidget(deck.getWidgetIndex(vertDisplayHelp));
      }
    };
    helpMenu.addItem("View ReadMe", cmdShowHelp);

    //Add items to menu
    taskMenu.addItem("Add Call", cmdShowAddCall);
    taskMenu.addItem("Display Calls", cmdShowPhoneBillDisplay);
    taskMenu.addItem("Help", helpMenu);


    RootPanel rootPanel = RootPanel.get();
    rootPanel.add(taskMenu);
    rootPanel.add(deck);
  }

  private VerticalPanel createVertDisplayHelp() {
    StringBuilder sb = new StringBuilder();
    sb.append("<h2>README file for PhoneBill v1.5</h2></br>");
    sb.append("Author: Evan Schott</br>");
    sb.append("Created for: CS410J</br>");
    sb.append("</t>Summer 2015</br>");
    sb.append("Project 5</br>");
    sb.append("</br>");
    sb.append("Project Description:</br>");
    sb.append("<p>This project records phone call details entered in the web form. Support is provided</br>");
    sb.append("for a phone bill server using Google Web Toolkit</p>");
    sb.append("Instructions:</br>");
    sb.append("<p>Create a new phone bill or add a call to an existing phone bill by selecting \"Add Call\"</br>");
    sb.append("from the menu. Enter the customer name for the phone bill. If the customer already exists,</br>");
    sb.append("the call will be added to the customer's phone bill. Otherwise, a new bill will be created</br>");
    sb.append("with the customer name and call information provided.</p>");
    sb.append("<p>To lookup a customer's phone bill records, select \"Display Calls\" from the menu. Enter</br>");
    sb.append("the customer name and option search parameters to display the bill.</p></br>");

    HTML readme = new HTML(sb.toString());
    VerticalPanel vert = new VerticalPanel();
    vert.add(readme);
    return vert;
  }

  private VerticalPanel createVertDisplayCall() {
    customerLookupNameField = new TextBox();
    HorizontalPanel hCustName = new HorizontalPanel();
    hCustName.add(new Label("Enter Customer Name: "));
    hCustName.add(customerLookupNameField);

    startTimeLookupField = new TextBox();
    HorizontalPanel hStartTime = new HorizontalPanel();
    hStartTime.add(new Label("Enter Beginning Time Range: "));
    hStartTime.add(startTimeLookupField);
    hStartTime.add(new Label("(optional)"));

    endTimeLookupField = new TextBox();
    HorizontalPanel hEndTime = new HorizontalPanel();
    hEndTime.add(new Label("Enter Ending Time Range: "));
    hEndTime.add(endTimeLookupField);
    hEndTime.add(new Label("(optional)"));

    Button button = new Button("Get Customer Call Records");
    button.addClickHandler(getPhoneCallsFromServer());

    VerticalPanel vert = new VerticalPanel();
    vert.add(hCustName);
    vert.add(hStartTime);
    vert.add(hEndTime);
    vert.add(button);
    return vert;

  }



  private VerticalPanel createVertCallAdd() {
    customerAddNameField = new TextBox();
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
    hCustName.add(customerAddNameField);

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

    Button button = new Button("Add Phone Call Record");
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
    List<PhoneCall> calls = (List) phoneBill.getPhoneCalls();

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
        return phoneCall.getStartTimeString();
      }
    };
    table.addColumn(startTimeColumn, "Start Time:");
    startTimeColumn.setSortable(true);

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

  private ClickHandler getPhoneCallsFromServer() {
    return new ClickHandler() {
      public void onClick( ClickEvent clickEvent) {
        String customerName = customerLookupNameField.getText();
        PingServiceAsync async = GWT.create(PingService.class);
        async.ping(customerName, new AsyncCallback<AbstractPhoneBill>() {

          public void onFailure(Throwable ex) {
            Window.alert(ex.toString());
          }

          public void onSuccess(AbstractPhoneBill phonebill) {
            PhoneBill bill = (PhoneBill) phonebill;
            CellTable<PhoneCall> table = prettyPrint(phonebill);

            //create panel to hold title and table
            Label label = new Label("Displaying calls for: " + bill.getCustomer());
            VerticalPanel vert = new VerticalPanel();
            vert.add(label);
            vert.add(table);

            //add panel to deck and show
            deck.add(vert);
            deck.showWidget(deck.getWidgetIndex(vert));
          }
        });
      }
    };
  }

  private ClickHandler createPhoneCallOnServer() {
    return new ClickHandler() {
      public void onClick( ClickEvent clickEvent )
      {
        String customerName = customerAddNameField.getText();
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
            Window.alert("Added " + bill.toString());
          }
        });
      }
    };
  }


}
