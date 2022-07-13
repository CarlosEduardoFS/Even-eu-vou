package com.even.model.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nameEvent;
	private String date;
	private String hour;
	private String description;
	private Boolean guestsTakeProducts;
	private Boolean needProducts;
	private Boolean active;
	private String keySearch;

	@OneToOne
	@JoinColumn
	private Account organizer;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn
	private TechnicalInformationEvent information;

	@OneToMany
	@JoinColumn
	private List<Guest> guests;

	public Event() {
		information = new TechnicalInformationEvent();
		guests = new LinkedList<>();
	}

	public Event(Integer id, String nameEvent, String date, String hour, String description,
			Boolean guestsTakeProducts, Boolean needProducts, Boolean active, String keySearch,
			Account organizer, TechnicalInformationEvent information, List<Guest> guests) {
		this.id = id;
		this.nameEvent = nameEvent;
		this.date = date;
		this.hour = hour;
		this.description = description;
		this.guestsTakeProducts = guestsTakeProducts;
		this.needProducts = needProducts;
		this.active = active;
		this.keySearch = keySearch;
		this.organizer = organizer;
		this.information = information;
		this.guests = guests;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNameEvent() {
		return nameEvent;
	}

	public void setNameEvent(String nameEvent) {
		this.nameEvent = nameEvent;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getGuestsTakeProducts() {
		return guestsTakeProducts;
	}

	public void setGuestsTakeProducts(Boolean guestsTakeProducts) {
		this.guestsTakeProducts = guestsTakeProducts;
	}

	public Boolean getNeedProducts() {
		return needProducts;
	}

	public void setNeedProducts(Boolean needProducts) {
		this.needProducts = needProducts;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getKeySearch() {
		return keySearch;
	}

	public void setKeySearch(String keySearch) {
		this.keySearch = keySearch;
	}

	public Account getOrganizer() {
		return organizer;
	}

	public void setOrganizer(Account organizer) {
		this.organizer = organizer;
	}

	public TechnicalInformationEvent getInformation() {
		return information;
	}

	public void setInformation(TechnicalInformationEvent information) {
		this.information = information;
	}

	public List<Guest> getGuests() {
		return guests;
	}

	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append(nameEvent + "\n");
		sb.append("Day: " + date + "\n");
		sb.append("Hour: " + hour + "/n");
		sb.append("Description: " + description + "\n");
		sb.append("Organizer: " + organizer.getUserName());
		sb.append("Contact: " + organizer.getLogin().getEmail());

		return sb.toString();

	}

}
