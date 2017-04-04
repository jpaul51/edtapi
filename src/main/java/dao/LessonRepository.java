package dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import model.Lesson;

@Component


public interface LessonRepository extends CrudRepository<Lesson, String> {

	
	@Query(value="Select l from Lesson l where :name MEMBER OF l.resourcesName")
	public Iterable<Lesson> findLessonsByResourceName(@Param("name") String resourceName);
	
	//@Query(value="Select distinct resourceName from Lesson")
	//public Iterable<String[]> findAvailableResourceNames();
	
	@Query(value="Select l from Lesson l where l.lessonUid= :uid")
	public Lesson findLessonByUid(@Param("uid") String uid);

}
