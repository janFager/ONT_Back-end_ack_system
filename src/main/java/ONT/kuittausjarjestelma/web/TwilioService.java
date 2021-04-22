package ONT.kuittausjarjestelma.web;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiMLException;
import ONT.kuittausjarjestelma.web.ServiceSarja;

@Service
public class TwilioService extends HttpServlet {
	private final static Logger LOGGER = LoggerFactory.getLogger(TwilioService.class);
	private final ServiceSarja serviceSarja; 
	
	@Autowired
	public TwilioService (ServiceSarja serviceSarja) {
		this.serviceSarja = serviceSarja;
		
	}
	
  public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String body = request.getParameter("Body");
    String message = "Message";
    if(serviceSarja.saveNotification(body)) {
    	message = "Kiitos! Kuittauksesi on vastaanotettu.";
    } else {
    	message = "Valitettavasti järjestelmässä on tapahtunut virhe. Yritä uudestaan tai soita toimistolle.";
    }
    


    // Create a TwiML response and add our friendly message.
    Body messageBody = new Body.Builder(message).build();
    Message sms = new Message.Builder().body(messageBody).build();
    MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();

    response.setContentType("application/xml");

    try {
      response.getWriter().print(twiml.toXml());
    } catch (TwiMLException e) {
      e.printStackTrace();
    }
  }
}