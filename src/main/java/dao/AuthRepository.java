package dao;


import model.Token;
import model.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface AuthRepository extends CrudRepository<Token, Long> {
	
	@Query( value = "Select t from Token t where t.user = :user" )
	Token findByUser(@Param("user") User user);
}