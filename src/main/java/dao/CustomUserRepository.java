package dao;

import org.springframework.data.repository.CrudRepository;

import model.CustomUser;

public interface CustomUserRepository  extends CrudRepository<CustomUser, Long>{

}
