package pl.umk.mat.damianszat.calendar.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "users")
@TypeAlias("user")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class User extends BaseDocument{

    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    private String login;

    private String password;

    @Field("desc")
    private String description;

    @DBRef(lazy = true)
    private List<Event> events;

    @PersistenceConstructor
    public User(String login, String password, String description, List<Event> events) {
        this.login = login;
        this.password = password;
        this.description = description;
        this.events = events;
    }

    public void addEvent(Event event){
        this.events.add(event);
    }

    public boolean deleteEvent(Event event){
        return this.events.remove(event);
    }
}
