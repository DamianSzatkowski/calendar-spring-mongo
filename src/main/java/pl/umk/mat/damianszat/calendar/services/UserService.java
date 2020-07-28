package pl.umk.mat.damianszat.calendar.services;

import org.springframework.http.ResponseEntity;
import pl.umk.mat.damianszat.calendar.model.Event;
import pl.umk.mat.damianszat.calendar.model.User;

import java.util.List;

public interface UserService {

    User save(final User user);
    List<User> getAll();
    List<User> getAllByEventId(String eventId);
    boolean userExistsById(User user);
    boolean userExistsById(String id);
    User findOne(String id);
    void deleteOne(String id);
    User patchOne(User user);
    User addEventToUser(Event event, String userId);
    User deleteEventOfUser(String eventId, String userId);
    List<User> allWithGivenEvent(String eventId);
}
