package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.LessonRepository;
import model.Lesson;
import model.Room;

@Service
@Qualifier("LessonService")
@Transactional
public class LessonService {

	final String FILEPATH = "src/main/resources/IEMB.ics";
	
	
	private final String LESSON_START="START:VEVENT";
	private final String LESSON_END="END:VEVENT";
	
	private final String LESSON_UPDATE="DTSTAMP:";
	private final String LESSON_DATE_START="DTSTART:";
	private final String LESSON_DATE_END="DTEND:";
	private final String LESSON_SUMMARY="SUMMARY:";
	private final String LESSON_LOCATION="LOCATION:";
	private final String LESSON_DESCRIPTION="DESCRIPTION:";
	private final String LESSON_UID="UID:";
	private final String LESSON_CREATED_DATE="CREATED:";
	private final String LESSON_LAST_MODIFIED_DATE="LAST-MODIFIED:";
	private final String LESSON_SEQUENCE="SEQUENCE:";
	
	
	
	 LessonRepository lessonRepo;
	
	
	public Iterable<Lesson> loadLessons()
	{
		return getLessonsFromFile();
	}
	
	private Iterable<Lesson> getLessonsFromFile()
	{
		File lessonFile = new File(FILEPATH);
		
		HashMap<String,Room> roomsByName = new HashMap<>();
		ArrayList<Lesson> lessons = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss'Z'");
		Lesson oneLesson = new Lesson();;
		try(BufferedReader br = new BufferedReader(new FileReader(lessonFile))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        // process the line.
		    	
		    	if(line.startsWith(LESSON_START))
		    	{
		    		System.out.println("START");
		    		oneLesson = new Lesson();
		    	}
		    	else if(line.startsWith(LESSON_END))
		    	{

		    		System.out.println("START");
		    		lessons.add(new Lesson(oneLesson));
		    	}
		    	else if(line.startsWith(LESSON_UPDATE))
		    	{
		    		String updateDateString = line.substring(line.indexOf(LESSON_UPDATE)+LESSON_UPDATE.length());
		    		DateTime dt = formatter.parseDateTime(updateDateString);
		    		oneLesson.setDateUpdate(dt);
		    	}
		    	else if(line.startsWith(LESSON_DATE_START))
		    	{
		    		String startDateString = line.substring(line.indexOf(LESSON_DATE_START)+LESSON_DATE_START.length());
		    		DateTime dt = formatter.parseDateTime(startDateString);
		    		oneLesson.setDateUpdate(dt);
		    	}
		    	else if(line.startsWith(LESSON_DATE_END))
		    	{
		    		System.out.println(line);
		    		String endDateString = line.substring(line.indexOf(LESSON_DATE_END)+LESSON_DATE_END.length());
		    		System.out.println(endDateString);
		    		DateTime dt = formatter.parseDateTime(endDateString);
		    		oneLesson.setDateUpdate(dt);
		    	}
		    	else if(line.startsWith(LESSON_SUMMARY))
		    	{
		    		oneLesson.setDescription(line);		    		
		    	}
		    	else if(line.startsWith(LESSON_LOCATION))
		    	{
		    		Room r = new Room(line);
		    		if(roomsByName.containsKey(r.getName()))
		    		{
		    			r = roomsByName.get(r.getName());
		    		}
		    		oneLesson.setRoom(r);
		    	}
		    	else if(line.startsWith(LESSON_DESCRIPTION))
		    	{
		    		oneLesson.setDescription(line);
		    	}
		    	else if(line.startsWith(LESSON_UID))
		    	{
		    		
		    	}
		    	else if(line.startsWith(LESSON_CREATED_DATE))
		    	{
		    		String createDateString = line.substring(line.indexOf(LESSON_CREATED_DATE)+LESSON_CREATED_DATE.length());
		    		DateTime dt = formatter.parseDateTime(createDateString);
		    		oneLesson.setDateUpdate(dt);
		    	}
		    	else if(line.startsWith(LESSON_LAST_MODIFIED_DATE))
		    	{
		    		String modifiedDateString = line.substring(line.indexOf(LESSON_LAST_MODIFIED_DATE)+LESSON_LAST_MODIFIED_DATE.length());
		    		DateTime dt = formatter.parseDateTime(modifiedDateString);
		    		oneLesson.setDateUpdate(dt);
		    	}
		    		  
		    	
		    }
		    // line is not visible here.
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lessons;
	}
	
}
