package pl.umk.mat.damianszat.calendar.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.umk.mat.damianszat.calendar.model.Event;

import java.math.BigInteger;

public interface EventRepository extends MongoRepository<Event, String> {

}
