package dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import model.EdtResource;

@Component
public interface EdtResourceRepository extends CrudRepository<EdtResource, Long> {

	
	@Query(value="Select count(*) from EdtResource e where lower(e.name) = lower(:name) ")
	public int countResByName(@Param("name")String name);
	
	@Query(value="Select count(*) from EdtResource e where lower(e.resourceId)= lower(:id) ")
	public int countResByEdtId(@Param("id")String edtId);
	
	@Query("select e FROM EdtResource e WHERE e.resourceId= :id")
	public EdtResource findByResourceId(@Param("id")String id);
	
	@Query("select e FROM EdtResource e WHERE e.name= :name")
	public EdtResource findByResourceName(@Param("name")String name);
	

	
}
