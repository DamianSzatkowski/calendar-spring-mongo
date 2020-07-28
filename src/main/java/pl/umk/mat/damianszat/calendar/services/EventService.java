package pl.umk.mat.damianszat.calendar.services;

import pl.umk.mat.damianszat.calendar.model.Event;

import java.util.List;

public interface EventService {

    Event save(final Event event);
    List<Event> saveAll(Iterable<Event> events);
    boolean eventExistsById(Event event);
    boolean eventExistsById(String id);
    List<Event> findAll();
    Event findOne(String id);
    void deleteOne(String id);
    void deleteAll(Iterable<Event> events);
}
