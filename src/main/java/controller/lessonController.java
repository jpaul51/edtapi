package controller;

import javax.crypto.AEADBadTagException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dao.LessonRepository;
import model.Lesson;
import model.LessonNote;
import service.LessonService;
import web.LessonNoteBody;

@RestController
@Configuration
@EnableJpaRepositories("dao")
@EntityScan("model")
public class lessonController {

	
	
	/*POST -> proxima/planning/lessons/notes/{lesson_id}/{note_id} (si 0 alors new) commentaire
    Header :
            access_token (String)
            current_user_id (Long)
    Body :
            title (String)
            description (String) 
            importance (String)
            
    return null;
            */
	
	@Autowired
	private LessonService lessonService;

	@RequestMapping(value="proxima/planning/lessons/{lesson_id}/notes",method = RequestMethod.POST)
	@ResponseBody
	public void editNote (@PathVariable("lesson_id") String lessonId, 
							@RequestBody LessonNoteBody lessonNoteBody ){
		try{
			lessonService.AddLessonNote(lessonId,lessonNoteBody);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

	}
	
	@RequestMapping(value="proxima/planning/lessons/{lesson_id}/notes/{note_id}",method = RequestMethod.POST)
	@ResponseBody
	public void editNote (@PathVariable("lesson_id") String lessonId,
							@PathVariable("note_id") String noteId,
							@RequestBody LessonNoteBody lessonNoteBody){
		try{
			lessonService.EditLessonNote(lessonId, noteId, lessonNoteBody);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}


}
