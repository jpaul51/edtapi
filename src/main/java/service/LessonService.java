package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import model.Lesson;

@Service("LessonService")
public class LessonService {

	final String FILEPATH = "src/main/resources/IEMB.ics";
	

	
	
	public void loadLessons()
	{
		
	}
	
	private Iterable<Lesson> getLessonsFromFile()
	{
		File lessonFile = new File(FILEPATH);
		
		ArrayList<Lesson> lessons = new ArrayList<>();
		
		Lesson oneLesson = new Lesson();;
		try(BufferedReader br = new BufferedReader(new FileReader(lessonFile))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        // process the line.
		    	
		    	if(line.startsWith("START:VEVENT"))
		    	{
		    		oneLesson = new Lesson();
		    	}
		    	else if(line.startsWith("END:VEVENT"))
		    	{
		    		lessons.add(new Lesson(oneLesson));
		    	}
		    	//else if()
		    	{
		    		
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
		
		return null;
	}
	
}
