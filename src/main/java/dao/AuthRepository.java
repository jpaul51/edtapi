package dao;


import java.util.List;

import model.CustomUser;
import model.Token;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface AuthRepository extends CrudRepository<Token, Long> {
}