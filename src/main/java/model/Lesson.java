package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	
	//The combination of these fields should be unique
	String location;//Room
	DateTime dateStart; 
	DateTime dateEnd; 
	
	
	public Lesson()
	{
		
	}
	
	
	
}
