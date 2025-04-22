package org.parasol.customerservice.ai.customer;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.smallrye.reactive.messaging.kafka.Record;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.parasol.customerservice.ai.customer.model.CustomerDto;
import org.parasol.customerservice.ai.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

@ApplicationScoped
public class MessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

    @Inject
    ErrorEventEmitter errorEventEmitter;

    @Inject
    StructuredMessageEmitter structuredMessageEmitter;

    @Inject
    CustomerService customerService;

    @Incoming("message")
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
    public Uni<Void> consume(Record<String, String> message) {
        LOGGER.info("Received message:{}", message.value());
        return Uni.createFrom().item(message).emitOn(Infrastructure.getDefaultWorkerPool())
                .onItem().invoke(m -> {
                    JsonObject json = new JsonObject(m.value());
                    JsonObject structured = json.getJsonObject("structured");
                    if (structured == null) {
                        handleError(m.key(), json, "Structured data missing", null, null);
                        return;
                    }
                    String email = structured.getString("email_address");
                    if (email == null || email.isEmpty()) {
                        handleError(m.key(), json, "Email missing", null, null);
                        return;
                    }
                    CustomerDto customer = customerService.getCustomerByEmail(email);
                    if (customer == null) {
                        handleError(m.key(), json, "Customer not found", null, null);
                        return;
                    }
                    processCustomerData(m.key(), json, customer);
                })
                .onItem().transformToUni(m -> Uni.createFrom().voidItem())
                .onFailure().recoverWithItem(t -> {
                    LOGGER.error("Error while processing Message", t);
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    t.printStackTrace(pw);
                    if (t instanceof DecodeException) {
                        handleError(message.key(), new JsonObject(), t.getMessage(), sw.toString(), message.value());
                    } else {
                        handleError(message.key(), new JsonObject(message.value()), t.getMessage(), sw.toString(), null);
                    }
                    return null;
                });
    }

    private void handleError(String key, JsonObject json, String errorMessage, String stacktrace, String message) {
        LOGGER.error("Error while processing message: {}", errorMessage);
        JsonObject error = new JsonObject();
        error.put("source", "customer-data");
        error.put("error", errorMessage);
        error.put("stack_trace", stacktrace);
        if (message == null) {
            JsonArray errors = json.getJsonArray("errors");
            if (errors == null) {
                errors = new JsonArray();
            }
            errors.add(error);
            json.put("errors", errors);
            errorEventEmitter.emit(key, json.encode());
        } else {
            error.put("message", message);
            errorEventEmitter.emit(key, error.encode());
        }
    }

    private void processCustomerData(String key, JsonObject json, CustomerDto customer) {
        JsonObject structured = json.getJsonObject("structured");
        structured.put("customer_id", customer.getId());
        structured.put("company_name", customer.getCompany());
        structured.put("country", customer.getCountry());
        structured.put("phone", customer.getPhone());
        json.put("structured", structured);
        LOGGER.info("Structured Information: {}", structured.encode());
        structuredMessageEmitter.emit(key, json.encode());
    }
}
