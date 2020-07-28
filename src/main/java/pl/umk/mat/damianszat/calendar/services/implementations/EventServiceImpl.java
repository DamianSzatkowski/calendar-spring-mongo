package pl.umk.mat.damianszat.calendar.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.umk.mat.damianszat.calendar.exception.InvalidOperationException;
import pl.umk.mat.damianszat.calendar.exception.NotFoundException;
import pl.umk.mat.damianszat.calendar.model.Event;
import pl.umk.mat.damianszat.calendar.repositories.EventRepository;
import pl.umk.mat.damianszat.calendar.services.EventService;
import pl.umk.mat.damianszat.calendar.services.UserService;

import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    @Autowired
    public EventServiceImpl(final EventRepository eventRepository, @Lazy final UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    @Override
    public Event save(Event event) {
        if(event.getId() != null && this.eventExistsById(event)){
            throw new InvalidOperationException("Event already exists!");
        }

        return eventRepository.save(event);
    }

    @Override
    public List<Event> saveAll(Iterable<Event> events){
        return eventRepository.saveAll(events);
    }

    @Override
    public boolean eventExistsById(Event event){
        return eventRepository.existsById(event.getId());
    }

    @Override
    public boolean eventExistsById(String id){
        return eventRepository.existsById(id);
    }

    @Override
    public List<Event> findAll(){
        return eventRepository.findAll();
    }

    @Override
    public Event findOne(String id){
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with given id doesn't exist!"));
    }

    @Override
    public void deleteOne(String id){
        if(!eventExistsById(id)){
            throw new NotFoundException("Cannot delete event. Event with given id doesn't exist!");
        }

        if(userService.allWithGivenEvent(id).size() == 0){
            eventRepository.deleteById(id);
        }

    }

    @Override
    public void deleteAll(Iterable<Event> events){
        eventRepository.deleteAll(events);
    }


}
