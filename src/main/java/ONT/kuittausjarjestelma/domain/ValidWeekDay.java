package ONT.kuittausjarjestelma.domain;


import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ValidWeekDay extends RepresentationModel<ValidWeekDay>{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String validWeekDayName;
	
	@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "validWeekDay")
	private List<Sarja> sarjas;

	public ValidWeekDay() {
		
	}
	
	public ValidWeekDay(String validWeekDayName) {
		super();
		this.validWeekDayName = validWeekDayName;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public List<Sarja> getSarjas() {
		return sarjas;
	}

	public void setSarjas(List<Sarja> sarjas) {
		this.sarjas = sarjas;
	}
	

	public String getValidWeekDayName() {
		return validWeekDayName;
	}

	public void setValidWeekDayName(String validWeekDayName) {
		this.validWeekDayName = validWeekDayName;
	}

	@Override
	public String toString() {
		return "ValidWeekDay [id=" + this.id + ", validWeekDayName=" + this.validWeekDayName + "]";
	}

	
	
}
