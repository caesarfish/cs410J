package edu.pdx.cs410J.eschott.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;

import java.util.Date;

/**
 * A GWT class to create and retrieve phone bills from the server
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
    helpMenu.addItem("README", cmdShowHelp);

    //Add items to menu
    taskMenu.addItem("Add Call", cmdShowAddCall);
    taskMenu.addItem("Display Calls", cmdShowPhoneBillDisplay);
    taskMenu.addItem("Help", helpMenu);


    RootPanel rootPanel = RootPanel.get();
    rootPanel.add(taskMenu);
    rootPanel.add(deck);
  }


  /**
   * Creates a vertical panel for displaying calls
   * @return VerticalPanel
   */
  private VerticalPanel createVertDisplayCall() {
    customerLookupNameField = new TextBox();
    HorizontalPanel hCustName = new HorizontalPanel();
    hCustName.setSpacing(5);
    hCustName.setWidth("480px");
    hCustName.add(new Label("Enter Customer Name:"));
    hCustName.add(customerLookupNameField);

    HorizontalPanel hLabel = new HorizontalPanel();
    hLabel.setSpacing(5);
    hLabel.add(new Label("Select only calls that start between (optional):"));

    startTimeLookupField = new TextBox();
    HorizontalPanel hStartTime = new HorizontalPanel();
    hStartTime.setSpacing(5);
    hStartTime.setWidth("300px");
    hStartTime.add(new Label("Time range start: "));
    hStartTime.add(startTimeLookupField);

    endTimeLookupField = new TextBox();
    HorizontalPanel hEndTime = new HorizontalPanel();
    hEndTime.setSpacing(5);
    hEndTime.setWidth("312px");
    hEndTime.add(new Label("Time range end: "));
    hEndTime.add(endTimeLookupField);

    Button button = new Button("Get Customer Call Records");
    button.addClickHandler(getPhoneCallsFromServer());

    VerticalPanel vert = new VerticalPanel();
    vert.add(hCustName);
    vert.add(hLabel);
    vert.add(hStartTime);
    vert.add(hEndTime);
    vert.add(button);
    return vert;

  }

  /**
   * Creates a vertical panel for adding calls to phone bill
   * @return VerticalPanel
   */
  private VerticalPanel createVertCallAdd() {
    customerAddNameField = new TextBox();
    callFrom = new TextBox();
    callTo = new TextBox();
    callStart = new TextBox();
    callEnd = new TextBox();

    //Set default values for testing
    /*callFrom.setText("111-111-1111");
    callTo.setText("222-222-2222");
    callStart.setText("1/1/2015 1:00 pm");
    callEnd.setText("1/1/2015 2:00 pm");
    */

    HorizontalPanel hCustName = new HorizontalPanel();
    hCustName.setSpacing(5);
    hCustName.setWidth("400px");
    hCustName.add(new Label("Enter Customer Name: "));
    hCustName.add(customerAddNameField);

    HorizontalPanel hCallFrom = new HorizontalPanel();
    hCallFrom.setSpacing(5);
    hCallFrom.setWidth("700px");
    hCallFrom.add(new Label("Call from: "));
    hCallFrom.add(callFrom);

    HorizontalPanel hCallTo = new HorizontalPanel();
    hCallTo.setSpacing(5);
    hCallTo.setWidth("890px");
    hCallTo.add(new Label("Call to: "));
    hCallTo.add(callTo);

    HorizontalPanel hCallStart = new HorizontalPanel();
    hCallStart.setSpacing(5);
    hCallStart.setWidth("710px");
    hCallStart.add(new Label("Call start: "));
    hCallStart.add(callStart);

    HorizontalPanel hCallEnd = new HorizontalPanel();
    hCallEnd.setSpacing(5);
    hCallEnd.setWidth("740px");
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

  /**
   * Creates a vertical panel for help display
   * @return VerticalPanel
   */
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
    sb.append("the customer name and optional search range to display the bill.</p>");
    sb.append("<p>Phone numbers must be entered in the format: ###-###-####</p>");
    sb.append("<p>Dates must be intered in the format: \"1/1/2001 12:00 pm\".</p>");

    HTML readme = new HTML(sb.toString());
    VerticalPanel vert = new VerticalPanel();
    vert.add(readme);
    return vert;
  }


  /**
   * ClickHandler for retrieving phone calls from the server
   * @return
   */
  private ClickHandler getPhoneCallsFromServer() {
    return new ClickHandler() {
      public void onClick( ClickEvent clickEvent) {
        String customerName = customerLookupNameField.getText();
        String startTime = startTimeLookupField.getText();
        String endTime = endTimeLookupField.getText();
        Date start = null;
        Date end = null;
        if (!"".equals(startTime) && !"".equals(endTime)) {
          DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yy hh:mm a");
          try {
            start = dateFormat.parse(startTime);
            end = dateFormat.parse(endTime);
          } catch (IllegalArgumentException e) {
            Window.alert("Please enter a valid date in the format: \"1/1/2001 12:00 pm\"");
            return;
          }
        }
        PingServiceAsync async = GWT.create(PingService.class);
        async.ping(customerName, start, end, new AsyncCallback<AbstractPhoneBill>() {

          public void onFailure(Throwable ex) {
            Window.alert(ex.getMessage());
          }

          public void onSuccess(AbstractPhoneBill phonebill) {
            PhoneBill bill = (PhoneBill) phonebill;
            CellTable<PhoneCall> table = bill.prettyPrint();

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

  /**
   * ClickHandler to create phone calls on the server
   * @return
   */
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
          return;
        }

        PingServiceAsync async = GWT.create(PingService.class);
        async.ping(customerName, call, new AsyncCallback<AbstractPhoneBill>() {

          public void onFailure(Throwable ex) {
            Window.alert(ex.getMessage());
          }

          public void onSuccess(AbstractPhoneBill phonebill) {
            PhoneBill bill = (PhoneBill) phonebill;
            Window.alert("Call was added to " + bill.getCustomer() + "'s phone bill");
          }
        });
      }
    };
  }


}
