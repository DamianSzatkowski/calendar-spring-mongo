package pl.umk.mat.damianszat.calendar.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pl.umk.mat.damianszat.calendar.model.User;

import java.math.BigInteger;
import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {


    List<User> findAllByEventsIn(String eventId);

}
