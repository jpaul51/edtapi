package dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import model.Lesson;

@Component
public interface LessonRepository extends CrudRepository<Lesson, String> {

}
