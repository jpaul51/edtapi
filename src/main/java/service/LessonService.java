package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.CustomUserRepository;
import dao.EdtResourceRepository;
import dao.LessonRepository;
import dao.RoomRepository;
import model.CustomUser;
import model.EdtResource;
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
	@Autowired CustomUserRepository userRepo;
	@Autowired EdtResourceRepository edtResRepo;
	
	public void loadLessons()
	{
		ArrayList<Lesson> lessons =  (ArrayList<Lesson>) getLessonsFromFile(FILEPATH,null);
		
		CustomUser user = new CustomUser();
		user.setLogin("test");
		
		
		
		lessonRepo.save(lessons);
		userRepo.save(user);
		
	}
	
	
	public String downloadResourceFile(String resourceId,Optional<Long> timeStampStart, Optional<Long> timeStampEnd)
	{
		
		try{
		    PrintWriter writer = new PrintWriter("src/main/resources/"+resourceId+".txt", "UTF-8");
		    writer.println(" ");
		    
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
		
		long timeStart;
		long timeEnd ;
		
		if(!timeStampStart.isPresent() || !timeStampEnd.isPresent() )
		{
			timeStart = System.currentTimeMillis();
			timeEnd = timeStart + new DateTime().plusMonths(1).getMillis();
		}
		else
		{
			timeStart=timeStampStart.get();
			timeEnd = timeStampEnd.get();
		}
		
		Timestamp timeStartUrl = new Timestamp(timeStart);
		Timestamp timeEndUrl = new Timestamp(timeEnd);
		
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		 String dateStart = simpleDateFormat.format(timeStartUrl);
		 String dateEnd = simpleDateFormat.format(timeEndUrl);
		 
		 

		try(
				
				  ReadableByteChannel in=Channels.newChannel(
				    new URL("http://adelb.univ-lyon1.fr/jsp/custom/modules/plannings/anonymous_cal.jsp?resources="+resourceId+"&projectId=3&calType=ical&firstDate="+dateStart+"&lastDate="+dateEnd).openStream());
				 
				FileChannel out=new FileOutputStream(
				    "src/main/resources/"+resourceId+".txt").getChannel() ) {

				  out.transferFrom(in, 0, Long.MAX_VALUE);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return "src/main/resources/"+resourceId+".txt";
	}
	
	public boolean checkEdtResourceByName(String name)
	{
		return edtResRepo.countResByName(name)>0;
	}
	public boolean checkEdtResourceByEdtId(String edtId)
	{
		return edtResRepo.countResByEdtId(edtId)>=1;
	}
	
	

	
	public void addEdtResource(String name,String edtResourceId,Optional<Long> timeStampStart, Optional<Long> timeStampEnd)
	{
		EdtResource edtRes = new EdtResource(edtResourceId,name);	
		System.out.println("ADD "+name);
		if( !checkEdtResourceByEdtId(edtResourceId) || !checkEdtResourceByName(name))
		{
			System.out.println("YES");
			String filePath = downloadResourceFile(edtResourceId, timeStampStart,  timeStampEnd);
			edtRes.setFileName(filePath);
			
			edtResRepo.save(edtRes);
			
			ArrayList<Lesson> lessons = (ArrayList<Lesson>) getLessonsFromFile(edtRes.getFileName(),edtRes.getName());
			System.out.println("name: "+name+", id: "+edtResourceId+", Size: "+lessons.size());
			lessonRepo.save(lessons);
		}
	}
	
	public void updateResource(String edtResourceName,Optional<Long> timeStampStart, Optional<Long> timeStampEnd)
	{
		EdtResource res = edtResRepo.findByResourceName(edtResourceName);
		if(res != null)
		{
			String fileName = downloadResourceFile(res.getResourceId(),timeStampStart,timeStampEnd);
			Iterable<Lesson> lessons = getLessonsFromFile(fileName,edtResourceName);
			lessonRepo.save(lessons);
		}
	}
	
	
	public Iterable<EdtResource> getResourceList()
	{
		return edtResRepo.findAll();
	}
	
	
	public Iterable<Lesson> getLessonsByResourceName(String resourceName,Optional<Long> timeStampStart, Optional<Long> timeStampEnd)
	{
//		List<String[]> resources = (List<String[]>) lessonRepo.findAvailableResourceNames();
//		
//		for(String[] s : resources)
//		{
//			System.out.println(s);
//		}
		
		System.out.println(resourceName);
		 DateTime dtStart;
		 DateTime dtEnd;
		
		 if(timeStampStart.isPresent())
		  dtStart = new DateTime(timeStampStart.get());
		 else
			 dtStart = new DateTime();
		 if(timeStampEnd.isPresent())
		  dtEnd = new DateTime(timeStampEnd.get());
		 else
		 {
			 dtEnd=new DateTime();
			 dtEnd = dtEnd.plusWeeks(1);
		 }
		
		 System.out.println("DSTART: "+(dtStart.toString("yyyy-MM-dd HH:mm:ss")));
		 System.out.println(dtEnd.toString("yyyy-MM-dd HH:mm:ss"));
		List<Lesson> lessons = (List<Lesson>) lessonRepo.findLessonsByResourceName(resourceName,dtStart,dtEnd);
		System.out.println(lessons.size());
		return lessons;
		
	
	}
	
	public List<Lesson> getAllLessons()
	{
		return (List<Lesson>) lessonRepo.findAll();
	}
	
	
	private Iterable<Lesson> getLessonsFromFile(String fileName,String resourceName)
	{
		File lessonFile = new File(fileName);
		
		HashMap<String,Room> roomsByName = new HashMap<>();
		ArrayList<Lesson> lessons = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss'Z'");
		Lesson oneLesson = new Lesson("null");
		
		
		
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

		    		System.out.println("END ADD lesson with "+oneLesson.getLessonUid());
		   		 
		    		if(resourceName != null)
		    		{
		    			if(oneLesson.getResourcesName() == null)
		    			{
		    				oneLesson.setResourcesName(new ArrayList<String>());
		    			}
		    			oneLesson.getResourcesName().add(resourceName);
		    		}
		    	
		    		
		    		if(lessonRepo.findLessonByUid(oneLesson.getLessonUid())!=null)
		    		{
		    			lessonRepo.findLessonByUid(oneLesson.getLessonUid());
		    			Lesson a = lessonRepo.findLessonByUid(oneLesson.getLessonUid());
		    			

			    		if(resourceName != null)
			    		{
			    			if(a.getResourcesName() == null)
			    			{
			    				a.setResourcesName(new ArrayList<String>());
			    			}
			    			a.getResourcesName().add(resourceName);
			    		}
		    			
		    			System.out.println(a.getTitle()+": "+a.getResourcesName().size());
		    			lessonRepo.save(a);
		    			
		    		}
		    		else
		    		{
		    			lessons.add(new Lesson(oneLesson));
		    		}
		    		
		    	
		    		
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
		    		
		    		String uid = line.substring(line.indexOf(LESSON_UID)+LESSON_UID.length());
		    		System.out.println("SET UID: "+uid);
		    		oneLesson.setLessonUid(uid);
		    	}
		    	else if(line.startsWith(LESSON_CREATED_DATE))
		    	{
		    		String createDateString = line.substring(line.indexOf(LESSON_CREATED_DATE)+LESSON_CREATED_DATE.length());
		    		DateTime dt = formatter.parseDateTime(createDateString);
		    		
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
