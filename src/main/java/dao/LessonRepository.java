package dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import model.Lesson;
import org.springframework.stereotype.Component;

import model.Lesson;

@Component
public interface LessonRepository extends CrudRepository<Lesson, Long> {
	
	@Query(value= "select l.* FROM Lesson l WHERE l.lessonUid = :lessonUid")
	public Lesson findLessonByUid(@Param("lessonUid")String lessonUid);

}
