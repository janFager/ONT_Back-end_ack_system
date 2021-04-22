package ONT.kuittausjarjestelma.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import ONT.kuittausjarjestelma.domain.Sarja;
import ONT.kuittausjarjestelma.domain.SarjaRepository;


@Service
public class ServiceSarja {
	private final static Logger LOGGER = LoggerFactory.getLogger(ServiceSarja.class);
	
	
	private final SarjaRepository sarjaRepository;
	
	@Autowired
	private ServiceValidSeason serviceValidSeason;
	
	
	public ServiceSarja(SarjaRepository sarjaRepository) {
		this.sarjaRepository = sarjaRepository;
	}
	

	public Boolean saveNotification(String msg) {
		
			int sarjaNum = Integer.parseInt(msg);
			LocalDateTime myDateObj = LocalDateTime.now();
		    System.out.println("Before formatting: " + myDateObj);
		    DateTimeFormatter myFormatObjDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		    DateTimeFormatter myFormatObjTime = DateTimeFormatter.ofPattern("HH:mm:ss");
		    String msgDate = myDateObj.format(myFormatObjDate);
		    String msgTime = myDateObj.format(myFormatObjTime);
		    String season = serviceValidSeason.season();
		    System.out.println(season + " season");
			try {
				List<Sarja> sarjat = new ArrayList<>();
				sarjat.addAll(sarjaRepository.findAll());
				for(int i = 0; i<sarjat.size();i++) {
					if(sarjat.get(i).getSarjaNumber()==sarjaNum && sarjat.get(i).getValidSeason().getSeasonName().equals(season)) {
						sarjat.get(i).setStatus(true);
						sarjat.get(i).setMsgSentDate(msgDate); //14-03-2021
						sarjat.get(i).setMsgSentTime(msgTime); //17:28:17
						sarjaRepository.save(sarjat.get(i));
						
						return true;
					} 				
					
				}
			} catch (Exception e) {
				System.out.println("Sarja's status error: " + e);
				return false;
			}
			return false;
	}
	
	public static void resetData(List<Sarja>sarjas) {
		
	}
}
