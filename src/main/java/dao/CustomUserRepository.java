package dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import model.CustomUser;

@Component
public interface CustomUserRepository  extends CrudRepository<CustomUser, Long>{

}
