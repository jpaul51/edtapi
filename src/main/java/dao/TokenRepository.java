package dao;

import model.CustomUser;
import model.Token;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface TokenRepository extends CrudRepository<Token, Long>{
	
	@Query("select t from Token t where t.user = :user")
	Token findTokenByUser(@Param("user") CustomUser user);

}
