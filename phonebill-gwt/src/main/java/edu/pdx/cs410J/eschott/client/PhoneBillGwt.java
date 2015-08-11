package edu.pdx.cs410J.eschott.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.Collection;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {

  private TextBox customerNameField;
  private TextBox callFrom;
  private TextBox callTo;
  private TextBox callStart;
  private TextBox callEnd;

  public void onModuleLoad() {

    VerticalPanel v2 = createVertCallInfo();

    TabPanel tabAddPhoneCall = new TabPanel();
    tabAddPhoneCall.add(v2, "Call Information");
    tabAddPhoneCall.selectTab(0);

    RootPanel rootPanel = RootPanel.get();
    rootPanel.add(tabAddPhoneCall);
  }

  private VerticalPanel createVertCallInfo() {
    customerNameField = new TextBox();
    callFrom = new TextBox();
    callTo = new TextBox();
    callStart = new TextBox();
    callEnd = new TextBox();

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

  private ClickHandler createPhoneCallOnServer() {
    return new ClickHandler() {
      public void onClick( ClickEvent clickEvent )
      {
        String customerName = customerNameField.getText();
        PingServiceAsync async = GWT.create(PingService.class);
        async.ping(customerName, new AsyncCallback<AbstractPhoneBill>() {

          public void onFailure(Throwable ex) {
            Window.alert(ex.toString());
          }

          public void onSuccess(AbstractPhoneBill phonebill) {
            StringBuilder sb = new StringBuilder(phonebill.toString());
            Collection<AbstractPhoneCall> calls = phonebill.getPhoneCalls();
            for (AbstractPhoneCall call : calls) {
              sb.append(call);
              sb.append("\n");
            }
            Window.alert(sb.toString());
          }
        });
        }
    };
  }
}
