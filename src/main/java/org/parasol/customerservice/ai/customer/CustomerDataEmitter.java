package org.parasol.customerservice.ai.customer;

import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

@ApplicationScoped
public class CustomerDataEmitter {

    @Inject
    @Channel("customer-data")
    Emitter<String> emitter;

    public void emit(String key, String payload) {
        emitter.send(toMessage(key, payload));
    }

    private Message<String> toMessage(String key, String payload) {
        return KafkaRecord.of(key, payload);
    }
}
