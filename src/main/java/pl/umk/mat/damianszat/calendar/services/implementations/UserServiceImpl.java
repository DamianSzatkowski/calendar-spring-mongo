package pl.umk.mat.damianszat.calendar.services.implementations;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.umk.mat.damianszat.calendar.exception.InvalidOperationException;
import pl.umk.mat.damianszat.calendar.exception.NotFoundException;
import pl.umk.mat.damianszat.calendar.model.Event;
import pl.umk.mat.damianszat.calendar.model.User;
import pl.umk.mat.damianszat.calendar.repositories.UserRepository;
import pl.umk.mat.damianszat.calendar.services.EventService;
import pl.umk.mat.damianszat.calendar.services.UserService;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EventService eventService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final EventService eventService, final ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.eventService = eventService;
        this.modelMapper = modelMapper;
    }

    private User mapUserPatch2UserDb(User userDb, User userPatch){

        TypeMap<User, User> typeMap = modelMapper.getTypeMap(User.class, User.class);

        if(typeMap == null){
            modelMapper.addMappings(new PropertyMap<User, User>() {
                @Override
                protected void configure() {
                    skip().setEvents(null);
                }
            });
        }

        modelMapper.map(userPatch, userDb);

        return userDb;
    }

    private String hashPassword(String oldPassword){
        BCrypt.Hasher hasher = BCrypt.withDefaults();

        return hasher.hashToString(BCrypt.MIN_COST, oldPassword.toCharArray());
    }

    @Override
    public User save(User user) {

        if(user.getId() != null && this.userExistsById(user)){
            throw new InvalidOperationException("User already exists!");
        }

        if(user.getEvents() != null && user.getEvents().size() > 0){
            List<Event> events = user.getEvents();
            events = this.eventService.saveAll(events);
            user.setEvents(events);
        }

        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    @Override
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllByEventId(String eventId) {
        return userRepository.findAllByEventsIn(eventId);
    }

    @Override
    public boolean userExistsById(User user){
        return userRepository.existsById(user.getId());
    }

    @Override
    public boolean userExistsById(String id){
        return userRepository.existsById(id);
    }

    @Override
    public User findOne(String id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User doesn't exist!"));
    }

    @Override
    public User patchOne(User user){

        if(user.getId() == null || !userExistsById(user.getId())){
            throw new InvalidOperationException("User doesn't exist!");
        }

        User userDb = findOne(user.getId());
        User patchedUser = mapUserPatch2UserDb(userDb, user);

        return userRepository.save(patchedUser);
    }

    @Override
    public User addEventToUser(Event event, String userId) {

        User user = findOne(userId);
        Event eventDb = eventService.save(event);

        user.addEvent(eventDb);

        return userRepository.save(user);

    }

    @Override
    public User deleteEventOfUser(String eventId, String userId) {
        User user = findOne(userId);
        Event eventDb = eventService.findOne(eventId);

        boolean userHadTheEvent = user.deleteEvent(eventDb);
        List<User> usersWhoHaveGivenEvent = allWithGivenEvent(eventDb.getId());

        User userRslt = userRepository.save(user);

        if(usersWhoHaveGivenEvent.size() == 1 && userHadTheEvent ){
            eventService.deleteOne(eventId);
        }

        return userRslt;
    }

    @Override
    public List<User> allWithGivenEvent(String eventId) {
        return userRepository.findAllByEventsIn(eventId);
    }

    @Override
    public void deleteOne(String id){

        User user = findOne(id);
        List<Event> events = user.getEvents();

        eventService.deleteAll(events);
        userRepository.deleteById(id);
    }
}
