package controller;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import service.LessonService;






@RestController
@Configuration
@EnableJpaRepositories("dao")
@EntityScan("model")
public class InitController {

	
	 @Inject private LessonService lessonService;
	
	
	
	@RequestMapping(value="/init",method = RequestMethod.GET)
	@ResponseBody
	public String initDb()
	{
		//lessonService.loadLessons();
		return "hop";
	}
	
	
	
	
	
}
