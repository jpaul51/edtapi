package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name="lesson")
public class Lesson {

	
	@Id @GeneratedValue
	long id;
	String title;
	
	DateTime dateUpdate;
	DateTime dateCreate;
	
	@OneToMany
	List<LessonNote> lessonNotes;
	
	//The combination of these fields should be unique
	String location;//Room
	DateTime dateStart; 
	DateTime dateEnd; 
	
	
	public Lesson()
	{
		
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


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
		result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
		result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
		result = prime * result + ((dateUpdate == null) ? 0 : dateUpdate.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((lessonNotes == null) ? 0 : lessonNotes.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
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
		if (id != other.id)
			return false;
		if (lessonNotes == null) {
			if (other.lessonNotes != null)
				return false;
		} else if (!lessonNotes.equals(other.lessonNotes))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	
	
}
