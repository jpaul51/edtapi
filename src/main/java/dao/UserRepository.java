package dao;

import model.CustomUser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends CrudRepository<CustomUser, Long>{

	@Query("select u from CustomUser u where u.login = :login")
	CustomUser findByLogin(@Param("login") String login); 
}
