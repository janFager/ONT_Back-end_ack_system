package ONT.kuittausjarjestelma.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ONT.kuittausjarjestelma.domain.Sarja;
import ONT.kuittausjarjestelma.domain.SarjaRepository;
import ONT.kuittausjarjestelma.domain.User;
import ONT.kuittausjarjestelma.domain.ValidSeason;
import ONT.kuittausjarjestelma.domain.ValidSeasonRepository;
import ONT.kuittausjarjestelma.domain.ValidWeekDay;
import ONT.kuittausjarjestelma.domain.ValidWeekDayRepository;
import ONT.kuittausjarjestelma.domain.UserRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class Controller {
	

	private final static Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	private SarjaRepository repository; 
	
	@Autowired
	private ValidSeasonRepository vrepository;
	
	@Autowired 
	private ValidWeekDayRepository wrepository;
	
	@Autowired
	private UserRepository urepository;
	
	@Autowired
	private TwilioService twilioService;
	
	@Autowired
	private ServiceSarja serviceSarja;
	
	@Autowired
	private ServiceValidSeason serviceSeason;
	

	
	@RequestMapping(value = "/replyMessage", method = RequestMethod.POST)
	public void replyMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		twilioService.service(request, response);
	  
	}
	
	
	@RequestMapping(value = "/seasonSarjas", method = RequestMethod.GET)
	public CollectionModel<Sarja> getSeasonSarjas(){
		String season = serviceSeason.season();
		int wekDay = serviceSeason.weekDay();
        List<Sarja> sarjas = repository.findAll();
        List<Sarja> seasonSarjas = new ArrayList<Sarja>();
        for (int i = 0; i<sarjas.size();i++) {
        	String name = sarjas.get(i).getValidSeason().getSeasonName();
        	if(name.equals(season) && sarjas.get(i).getValidWeekDay().getId()==wekDay){
        		seasonSarjas.add(sarjas.get(i));
        		
        	}
        }
        
       var dateObj = new Date();
       int hours = dateObj.getHours();
       int minutes = dateObj.getMinutes();
       int sec = dateObj.getSeconds();
       if (hours == 23 && minutes==59 && sec>85) {
    	   for(final Sarja seasonSarja : seasonSarjas) {
    		   seasonSarja.setStatus(false);
    		   seasonSarja.setMsgSentTime("");
    		   seasonSarja.setMsgSentTime("");
    	   }
       }
        
        for(final Sarja seasonSarja : seasonSarjas) {

        	seasonSarja.add(linkTo(methodOn(Controller.class).getsarjaById(seasonSarja.getId())).withSelfRel());
        	seasonSarja.add(linkTo(methodOn(Controller.class).getseasonById(seasonSarja.getValidSeason().getId())).withRel("valid_season"));
        	seasonSarja.add(linkTo(methodOn(Controller.class).getweekDaysById(seasonSarja.getValidWeekDay().getId())).withRel("valid_weekday"));

        }

        Link link = linkTo(methodOn(Controller.class).getSarjas()).withSelfRel();
        Link link2 = linkTo(methodOn(Controller.class).getValidSeasons()).withRel("seasons");
        Link link3 = linkTo(methodOn(Controller.class).getweekDays()).withRel("weekdays");
        return new CollectionModel<>(seasonSarjas, link, link2, link3);
    }
	
	@RequestMapping(value = "/sarjas", method = RequestMethod.GET)
    public CollectionModel<Sarja> getSarjas(){

        List<Sarja> sarjas = repository.findAll();

        for(final Sarja sarja : sarjas) {

            sarja.add(linkTo(methodOn(Controller.class).getsarjaById(sarja.getId())).withSelfRel());
            sarja.add(linkTo(methodOn(Controller.class).getseasonById(sarja.getValidSeason().getId())).withRel("valid_season"));
            sarja.add(linkTo(methodOn(Controller.class).getweekDaysById(sarja.getValidWeekDay().getId())).withRel("valid_weekday"));

        }

        Link link = linkTo(methodOn(Controller.class).getSarjas()).withSelfRel();
        Link link2 = linkTo(methodOn(Controller.class).getValidSeasons()).withRel("seasons");
        Link link3 = linkTo(methodOn(Controller.class).getweekDays()).withRel("weekdays");
        return new CollectionModel<>(sarjas, link, link2, link3);
    }
	
	@PostMapping(value="/getRole")
	public List<User> getRole() {
		List<User> users = urepository.findAll();
		
		for(int i = 0; i<users.size();i++) {
			users.get(i).setPasswordHash("");
		}
				
		    return users;		
	    
	  }
	
	// RESTful service to add sarjas
	@PostMapping(value="/sarjas")
	public void newSarja(@RequestBody Sarja newSarja1) {
		newSarja1.setStatus(false);
	    repository.save(newSarja1);
	    
	  }
	
	
	@PutMapping(value = "sarjas/{id}") 
	public void updateSarja(@PathVariable Long id, @RequestBody Sarja newSarja) {
		List<Sarja> sarjas = new ArrayList<>();
		sarjas.addAll(repository.findAll());
		int index = 0;
		for(int i = 0; i<sarjas.size();i++) {
			if(sarjas.get(i).getId()==id) {
				index = i;
			}
		}
		Sarja sarja = sarjas.get(index);
		newSarja.setId(id);
		newSarja.setValidWeekDay(sarja.getValidWeekDay());
		newSarja.setValidSeason(sarja.getValidSeason());
		repository.save(newSarja);
	}
	
	
	@PostMapping(value = "sarjas/{id}")
	public void  kuittaa(@PathVariable Long id) {
		List<Sarja> sarjas = new ArrayList<>();
		sarjas.addAll(repository.findAll());
		int index = 0;
		for(int i = 0; i<sarjas.size();i++) {
			if(sarjas.get(i).getId()==id) {
				index = i;
			}
		}
		Sarja sarja = sarjas.get(index);
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObjTime = DateTimeFormatter.ofPattern("HH:mm:ss");
	    String kuittausTime = myDateObj.format(myFormatObjTime);
	    if(sarja.isStatus()) {
	    	sarja.setStatus(false);
	    } else {
	    	sarja.setStatus(true);
	    }
	    
	    sarja.setMsgSentTime(kuittausTime);
	    repository.save(sarja);
	}
	
	@RequestMapping(value = "/statusReset", method = RequestMethod.POST)
	public void statusReset () {
		String season = serviceSeason.season();
		int wekDay = serviceSeason.weekDay();
        List<Sarja> sarjas = repository.findAll();
        for (int i = 0; i<sarjas.size();i++) {
        	String name = sarjas.get(i).getValidSeason().getSeasonName();
        	if(name.equals(season) && sarjas.get(i).getValidWeekDay().getId()==wekDay){
        		Sarja sarja = sarjas.get(i);
        		sarja.setStatus(false);
        		repository.save(sarja);
        		
        	}
        }
        
	}
	
	@GetMapping(value = "/sarjas/{id}")
    public EntityModel<Sarja> getsarjaById(@PathVariable Long id){

		List<Sarja> sarjas = new ArrayList<>();
		sarjas.addAll(repository.findAll());
		int index = 0;
		for(int i = 0; i<sarjas.size();i++) {
			if(sarjas.get(i).getId()==id) {
				index = i;
			}
		}
		Sarja sarja = sarjas.get(index);
		Long season_id = sarja.getValidSeason().getId();
		Long weekday_id =sarja.getValidWeekDay().getId();
		

        sarja.add(linkTo(methodOn(Controller.class).getsarjaById(id)).withSelfRel());
        sarja.add(linkTo(methodOn(Controller.class).getseasonById(season_id)).withRel("valid_season"));
        sarja.add(linkTo(methodOn(Controller.class).getweekDaysById(weekday_id)).withRel("valid_weekdays"));

        return new EntityModel<>(sarja);
    }
	
	
	// RESTful service to delete sarjas by id
	@DeleteMapping("/sarjas/{id}")
	void deleteSarja(@PathVariable Long id) {
		 repository.deleteById(id);
	}
		

	
	// VALID_SEASON APIS
	
	// RESTful service to update valid_season's from and to by id
	@PutMapping("/seasons/{id}")
	void changeSeason(@PathVariable Long id, @RequestBody ValidSeason validSeason) {
		
		List<ValidSeason> validSeasons = new ArrayList<>();
		validSeasons.addAll(vrepository.findAll());
		int index = 0;
		for(int i = 0; i<validSeasons.size();i++) {
			if(validSeasons.get(i).getId()==id) {
				validSeasons.get(i).setValidFrom(validSeason.getValidFrom());
				validSeasons.get(i).setValidTo(validSeason.getValidTo());
				index =1;
			}
		}
		 vrepository.save(validSeasons.get(index));
	}
	
		//// RESTful service to get all seasons
		@RequestMapping(value = "/seasons", method = RequestMethod.GET)
	    public CollectionModel<ValidSeason> getValidSeasons(){

	        List<ValidSeason> validSeasons = vrepository.findAll();

	        for(final ValidSeason validSeason : validSeasons) {

	        	validSeason.add(linkTo(methodOn(Controller.class).getseasonById(validSeason.getId())).withSelfRel());
	        	//validSeason.add(linkTo(methodOn(Controller.class).getsarjaById(validSeason.getSarjas())).withRel("sarjas"));

	            for (final Sarja sarja : validSeason.getSarjas()) {
	                Link selfLink = linkTo(methodOn(Controller.class).getsarjaById(sarja.getId())).withSelfRel();
	                sarja.add(selfLink);
	            }
	        }

	        Link link = linkTo(methodOn(Controller.class).getValidSeasons()).withSelfRel();
	        Link link2 = linkTo(methodOn(Controller.class).getSarjas()).withRel("sarjas");

	        return new CollectionModel<>(validSeasons, link, link2);
	    }

		// RESTful service to get a seasons by id
		@GetMapping(value = "seasons/{id}")
	    public EntityModel<ValidSeason> getseasonById(@PathVariable Long id){

			List<ValidSeason> seasons = new ArrayList<>();
			seasons.addAll(vrepository.findAll());
			int index = 0;
			for(int i = 0; i<seasons.size();i++) {
				if(seasons.get(i).getId()==id) {
					index = i;
				}
			}
			ValidSeason season = seasons.get(index);
			season.add(linkTo(methodOn(Controller.class).getseasonById(id)).withSelfRel());

	        return new EntityModel<>(season);
	    }
	
		
	// RESTful service FOR VALID_WEEK_DAYS
		
		// RESTful service to get all weekdays
		@RequestMapping(value = "/weekdays", method = RequestMethod.GET)
	    public CollectionModel<ValidWeekDay> getweekDays(){

	        List<ValidWeekDay> validWeekDays = wrepository.findAll();

	        for(final ValidWeekDay validWeekDay : validWeekDays) {

	        	validWeekDay.add(linkTo(methodOn(Controller.class).getweekDaysById(validWeekDay.getId())).withRel("self"));

	            for (final Sarja sarja : validWeekDay.getSarjas()) {
	                Link selfLink = linkTo(methodOn(Controller.class).getsarjaById(sarja.getId())).withSelfRel();
	                sarja.add(selfLink);
	            }
	        }

	        Link link = linkTo(methodOn(Controller.class).getValidSeasons()).withSelfRel();
	        Link link2 = linkTo(methodOn(Controller.class).getSarjas()).withSelfRel();

	        return new CollectionModel<>(validWeekDays, link, link2);
	    }

		// RESTful service to get a weekday by id
		@GetMapping(value = "/weekdays/{id}")
	    public EntityModel<ValidWeekDay> getweekDaysById(@PathVariable Long id){

			List<ValidWeekDay> weekdays = new ArrayList<>();
			weekdays.addAll(wrepository.findAll());
			int index = 0;
			for(int i = 0; i<weekdays.size();i++) {
				if(weekdays.get(i).getId()==id) {
					index = i;
				}
			}
			ValidWeekDay weekday = weekdays.get(index);

			weekday.add(linkTo(methodOn(Controller.class).getweekDaysById(id)).withSelfRel());
	        return new EntityModel<>(weekday);
	    }
	
	@GetMapping("/test")
	public void login() {
		boolean onko = serviceSarja.saveNotification("78");
		System.out.println(onko);
	}

}
