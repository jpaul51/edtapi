package controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
}
