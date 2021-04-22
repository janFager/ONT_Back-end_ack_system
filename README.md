# ONT_Back-end

This is a Backend for an acknowlegment system for bus company to acknowledge driving shifts. 
The system uses PostrgeSQL-database and Twilio messaging service.
Twilio is used for receiving text messages(SMS) from bus drivers and sending replies. The system receives the message and marks the shift with green color, that means that everything is OK. If the system doesn't receive the sms from a driver, it will give the alarm. 

