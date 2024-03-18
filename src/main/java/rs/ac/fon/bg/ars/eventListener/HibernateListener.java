package rs.ac.fon.bg.ars.eventListener;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import rs.ac.fon.bg.ars.eventListener.eventHandlers.MessageQueueHandler;

@Component
@Setter
@Getter
@ConfigurationProperties
public class HibernateListener {

    @Autowired
    MessageQueueHandler messageQueueHandler;

    @PostPersist
    private void afterInsert(Object o) {
        String operation = "INSERT";
        messageQueueHandler.sendMessage(o,operation);
    }
    @PostUpdate
    private void afterUpdate(Object o) {
        String operation = "UPDATE";
        messageQueueHandler.sendMessage(o,operation);
    }
    @PostRemove
    private void afterDelete(Object o) {
        String operation = "DELETE";
        messageQueueHandler.sendMessage(o,operation);
    }


}
