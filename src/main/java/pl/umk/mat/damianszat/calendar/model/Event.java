package pl.umk.mat.damianszat.calendar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "events")
@TypeAlias("event")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Event extends BaseDocument {

    private String name;
    private String place;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginning;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date end;

    @PersistenceConstructor
    public Event(String name, String place, Date beginning, Date end) {
        this.name = name;
        this.place = place;
        this.beginning = beginning;
        this.end = end;
    }
}
