package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.LessonRepository;
import dao.RoomRepository;
import model.Lesson;
import model.Room;

@Service
@Qualifier("LessonService")
@Transactional
public class LessonService {

	final String FILEPATH = "src/main/resources/IEMB.ics";
	
	
	private final String LESSON_START="BEGIN:VEVENT";
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
	
	
	
	 @Autowired LessonRepository lessonRepo;
	@Autowired RoomRepository roomRepo;
	
	public void loadLessons()
	{
		ArrayList<Lesson> lessons =  (ArrayList<Lesson>) getLessonsFromFile();
		lessonRepo.save(lessons);
		
		
	}
	
	
	public List<Lesson> getAllLessons()
	{
		return (List<Lesson>) lessonRepo.findAll();
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
		    		
		    		oneLesson = new Lesson();
		    	}
		    	else if(line.startsWith(LESSON_END))
		    	{
		 
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
		    		oneLesson.setDateStart(dt);
		    	}
		    	else if(line.startsWith(LESSON_DATE_END))
		    	{
		    		
		    		String endDateString = line.substring(line.indexOf(LESSON_DATE_END)+LESSON_DATE_END.length());
		    		
		    		DateTime dt = formatter.parseDateTime(endDateString);
		    		
		    		oneLesson.setDateEnd(dt);
		    	}
		    	else if(line.startsWith(LESSON_SUMMARY))
		    	{
		    		String endSummaryString = line.substring(line.indexOf(LESSON_SUMMARY)+LESSON_SUMMARY.length());
		    		oneLesson.setTitle(endSummaryString);		    		
		    	}
		    	else if(line.startsWith(LESSON_LOCATION))
		    	{
		    		String roomName = line.substring(line.indexOf(LESSON_LOCATION)+LESSON_LOCATION.length());
		    		Room r = new Room(roomName);
		    		if(roomsByName.containsKey(r.getName()))
		    		{
		    			r = roomsByName.get(r.getName());
		    		}
		    		else
		    		{
		    			roomsByName.put(r.getName(), r);
		    		}
		    		oneLesson.setRoom(r);
		    	}
		    	else if(line.startsWith(LESSON_DESCRIPTION))
		    	{
		    		String description = line.substring(line.indexOf(LESSON_DESCRIPTION)+LESSON_DESCRIPTION.length());
		    		
		    		oneLesson.setDescription(description);
		    	}
		    	else if(line.startsWith(LESSON_UID))
		    	{
		    		
		    	}
		    	else if(line.startsWith(LESSON_CREATED_DATE))
		    	{
		    		String createDateString = line.substring(line.indexOf(LESSON_CREATED_DATE)+LESSON_CREATED_DATE.length());
		    		DateTime dt = formatter.parseDateTime(createDateString);
		    		oneLesson.setDateUpdate(dt);
		    		oneLesson.setDateCreate(dt);
		    	}
		    	else if(line.startsWith(LESSON_LAST_MODIFIED_DATE))
		    	{
		    		//String modifiedDateString = line.substring(line.indexOf(LESSON_LAST_MODIFIED_DATE)+LESSON_LAST_MODIFIED_DATE.length());
		    		//DateTime dt = formatter.parseDateTime(modifiedDateString);
		    		//oneLesson.set(dt);
		    		
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
		System.out.println(roomsByName.size());
		roomRepo.save(roomsByName.values());
		return lessons;
	}
	
}
