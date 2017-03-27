package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="lesson")
public class Lesson {

	
	
	@Id @GeneratedValue
	long id;
	
	
	String title;
	@JsonProperty("date_update")
	DateTime dateUpdate;
	@JsonProperty("date_create")
	DateTime dateCreate;
	
	@JsonProperty("lesson_notes")
	@OneToMany
	List<LessonNote> lessonNotes;
	
	//The combination of these fields should be unique
	
	@ManyToOne
	Room room;
	
	DateTime dateStart; 
	DateTime dateEnd; 
	
	String description;
	
	
	public Lesson()
	{
		
	}

	

	public Lesson(Lesson oldLesson) {
		this.title = oldLesson.getTitle();
		this.dateUpdate = oldLesson.getDateUpdate();
		this.dateCreate = oldLesson.getDateCreate();
		this.lessonNotes = oldLesson.getLessonNotes();
		this.room = oldLesson.getRoom();
		this.dateStart = oldLesson.getDateStart();
		this.dateEnd = oldLesson.getDateEnd();
		this.description = oldLesson.getDescription();
	}



	public Lesson(String title, DateTime dateUpdate, DateTime dateCreate, Room location, DateTime dateStart,
			DateTime dateEnd) {
		super();
		this.title = title;
		this.dateUpdate = dateUpdate;
		this.dateCreate = dateCreate;
		this.room = location;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}



	public Lesson(String title, DateTime dateUpdate, DateTime dateCreate, List<LessonNote> lessonNotes, Room location,
			DateTime dateStart, DateTime dateEnd) {
		super();
		this.title = title;
		this.dateUpdate = dateUpdate;
		this.dateCreate = dateCreate;
		this.lessonNotes = lessonNotes;
		this.room = location;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
	}



	
	
	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public DateTime getDateUpdate() {
		return dateUpdate;
	}


	public void setDateUpdate(DateTime dateUpdate) {
		this.dateUpdate = dateUpdate;
	}


	public DateTime getDateCreate() {
		return dateCreate;
	}


	public void setDateCreate(DateTime dateCreate) {
		this.dateCreate = dateCreate;
	}


	public List<LessonNote> getLessonNotes() {
		return lessonNotes;
	}


	public void setLessonNotes(List<LessonNote> lessonNotes) {
		this.lessonNotes = lessonNotes;
	}




	public DateTime getDateStart() {
		return dateStart;
	}


	public void setDateStart(DateTime dateStart) {
		this.dateStart = dateStart;
	}


	public DateTime getDateEnd() {
		return dateEnd;
	}


	public void setDateEnd(DateTime dateEnd) {
		this.dateEnd = dateEnd;
	}



	public Room getRoom() {
		return room;
	}



	public void setRoom(Room room) {
		this.room = room;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
		result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
		result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
		result = prime * result + ((dateUpdate == null) ? 0 : dateUpdate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((lessonNotes == null) ? 0 : lessonNotes.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lesson other = (Lesson) obj;
		if (dateCreate == null) {
			if (other.dateCreate != null)
				return false;
		} else if (!dateCreate.equals(other.dateCreate))
			return false;
		if (dateEnd == null) {
			if (other.dateEnd != null)
				return false;
		} else if (!dateEnd.equals(other.dateEnd))
			return false;
		if (dateStart == null) {
			if (other.dateStart != null)
				return false;
		} else if (!dateStart.equals(other.dateStart))
			return false;
		if (dateUpdate == null) {
			if (other.dateUpdate != null)
				return false;
		} else if (!dateUpdate.equals(other.dateUpdate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (lessonNotes == null) {
			if (other.lessonNotes != null)
				return false;
		} else if (!lessonNotes.equals(other.lessonNotes))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}




	
	
	
	
}
