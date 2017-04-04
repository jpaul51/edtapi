package dao;

import model.Lesson;

import java.util.Optional;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Component;


@Component

public interface LessonRepository extends CrudRepository<Lesson, String> {


	
	@Query(value="Select l from Lesson l where :name MEMBER OF l.resourcesName AND l.dateStart > :start AND l.dateEnd < :end")
	public Iterable<Lesson> findLessonsByResourceName(@Param("name") String resourceName, @Param("start") DateTime start, @Param("end")DateTime end);
	
	//@Query(value="Select distinct resourceName from Lesson")
	//public Iterable<String[]> findAvailableResourceNames();
	
	@Query(value="Select l from Lesson l where l.lessonUid= :uid")
	public Lesson findLessonByUid(@Param("uid") String uid);

}
