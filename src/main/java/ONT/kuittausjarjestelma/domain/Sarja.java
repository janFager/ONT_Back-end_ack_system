package ONT.kuittausjarjestelma.domain;

import java.security.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import org.springframework.hateoas.RepresentationModel;


@Entity
public class Sarja extends RepresentationModel<Sarja>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false, updatable =false)
	private Long id;
	
	@Column(name="sarjaNumber", nullable = false)
	private int sarjaNumber;
	
	@Column(name="buscom", nullable = false)
	private int buscom;
	
	@Column(name="busNumber", nullable = false)
	private String busNumber;
	
	@Column(name="departureStop", nullable = false)
	private String departureStop;

	@Column(name = "departureTime")
	private String departureTime;
	

	@Column(name = "msgSentDate")
	private String msgSentDate;
	

	@Column(name = "msgSentTime")
	private String msgSentTime;
	
	@Column(name="status", nullable = false)
	private boolean status;
	
	@ManyToOne
	@JoinColumn(name = "validSeason_id")
	private ValidSeason validSeason;
	
	@ManyToOne
	@JoinColumn(name = "validWeekDay_id")
	private ValidWeekDay validWeekDay;
	

	public Sarja(int sarjaNumber, int buscom, String busNumber, String departureStop,
			String departureTime, String msgSentDate, String msgSentTime, boolean status, ValidSeason validSeason,
			ValidWeekDay validWeekDay) {
		super();
		this.sarjaNumber = sarjaNumber;
		this.buscom = buscom;
		this.busNumber = busNumber;
		this.departureStop = departureStop;
		this.departureTime = departureTime;
		this.msgSentDate = msgSentDate;
		this.msgSentTime = msgSentTime;
		this.status = status;
		this.validSeason = validSeason;
		this.validWeekDay = validWeekDay;
	}
	
	public Sarja () {
		
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSarjaNumber() {
		return sarjaNumber;
	}

	public void setSarjaNumber(int sarjaNumber) {
		this.sarjaNumber = sarjaNumber;
	}

	public int getBuscom() {
		return buscom;
	}

	public void setBuscom(int buscom) {
		this.buscom = buscom;
	}

	public String getBusNumber() {
		return busNumber;
	}

	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}

	public String getDepartureStop() {
		return departureStop;
	}

	public void setDepartureStop(String departureStop) {
		this.departureStop = departureStop;
	}


	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getMsgSentDate() {
		return msgSentDate;
	}

	public void setMsgSentDate(String msgSentDate) {
		this.msgSentDate = msgSentDate;
	}

	public String getMsgSentTime() {
		return msgSentTime;
	}

	public void setMsgSentTime(String msgSentTime) {
		this.msgSentTime = msgSentTime;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public ValidSeason getValidSeason() {
		return validSeason;
	}

	public void setValidSeason(ValidSeason validSeason) {
		this.validSeason = validSeason;
	}

	public ValidWeekDay getValidWeekDay() {
		return validWeekDay;
	}

	public void setValidWeekDay(ValidWeekDay validWeekDay) {
		this.validWeekDay = validWeekDay;
	}

	@Override
	public String toString() {
		return "Sarja [id=" + id + ", sarjaNumber=" + sarjaNumber + ", buscom=" + buscom + ", busNumber=" + busNumber
				+ ", departureStop=" + departureStop + ", departureTime=" + departureTime + ", msgSentDate="
				+ msgSentDate + ", msgSentTime=" + msgSentTime + ", status=" + status + ", validSeason=" + this.getValidSeason()
				+ ", validWeekDay=" + this.getValidWeekDay() + "]";
	}

	
	
	
}
