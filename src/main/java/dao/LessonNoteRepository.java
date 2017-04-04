package dao;
import org.springframework.data.repository.CrudRepository;
import model.LessonNote;

import org.springframework.stereotype.Component;

@Component
public interface LessonNoteRepository extends CrudRepository<LessonNote, Long> {

}