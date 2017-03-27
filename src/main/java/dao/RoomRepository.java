package dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import model.Room;

@Component
public interface RoomRepository extends CrudRepository<Room, Long> {

}
