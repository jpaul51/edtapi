package controller;



import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import model.Lesson;
import service.LessonService;

@RestController
@Configuration
@EnableJpaRepositories("dao")
@EntityScan("model")
public class InitController {

	
	@Autowired private LessonService lessonService;
	
	
	
	@RequestMapping(value="/init",method = RequestMethod.GET)
	@ResponseBody
	public void initDb()
	{
		 lessonService.loadLessons();
	}
	
	
	@RequestMapping(value="/lessons",method = RequestMethod.GET)
	@ResponseBody
	public List<Lesson> getAllLessons()
	{
		 return lessonService.getAllLessons();
	
	}
	
	
	@RequestMapping(value="/load",method = RequestMethod.GET)
	public String loadResource(@RequestParam String id)
	{
		
		
		try{
		    PrintWriter writer = new PrintWriter("src/main/resources/t1.txt", "UTF-8");
		    writer.println(" ");
		    
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
		
		try(
				  ReadableByteChannel in=Channels.newChannel(
				    new URL("http://adelb.univ-lyon1.fr/jsp/custom/modules/plannings/anonymous_cal.jsp?resources=9444&projectId=3&calType=ical&firstDate=2017-03-27&lastDate=2017-03-27").openStream());
				 
				FileChannel out=new FileOutputStream(
				    "src/main/resources/t1.txt").getChannel() ) {

				  out.transferFrom(in, 0, Long.MAX_VALUE);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		return "oui";
	}	
	
}
