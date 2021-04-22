package ONT.kuittausjarjestelma.web;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import ONT.kuittausjarjestelma.domain.ValidSeason;
import ONT.kuittausjarjestelma.domain.ValidSeasonRepository;

@Service
public class ServiceValidSeason {
	
	private final ValidSeasonRepository validSeasonRepository;
	
	public ServiceValidSeason(ValidSeasonRepository validSeasonRepository) {
		this.validSeasonRepository = validSeasonRepository;
	}
	
	public String season() {
		LocalDateTime myDateObj = LocalDateTime.now();
		List<ValidSeason> seasons = new ArrayList<>();
		seasons.addAll(validSeasonRepository.findAll());
		DateTimeFormatter myFormatObjDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String msgDate = myDateObj.format(myFormatObjDate);
	    String season = "";
	    SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
	    Date d1;
	    Date d2_from;
	    Date d3_to;
		try {
			d1 = sdformat.parse(msgDate);
			for(int i = 0; i< seasons.size();i++) {
				d2_from = sdformat.parse(seasons.get(i).getValidFrom());
				d3_to = sdformat.parse(seasons.get(i).getValidTo());
				if(d1.compareTo(d2_from) >= 0 && d1.compareTo(d3_to) <= 0) {
					season = seasons.get(i).getSeasonName();
				}
			}
		} catch(Exception e) {
			System.out.println("Valid season error: " + e);
		}
		
		return season;
	}
	
	public Integer weekDay() {
		int index = 0;
		LocalDateTime myDateObj = LocalDateTime.now();
 		DayOfWeek dayOfWeek
        = DayOfWeek.from(myDateObj);
 		 int val = dayOfWeek.getValue();
 		 String name = dayOfWeek.name();
 		System.out.println(val + " " + name);
 		if(val == 1 || val == 2 || val == 3 || val == 4) {
 			return index=1;
 		} else if (val == 5) {
 			return index=2;
 		} else if (val == 6) {
 		return index=3;
 		} else {
 			return index=4;
 		}
 		
	}
}
