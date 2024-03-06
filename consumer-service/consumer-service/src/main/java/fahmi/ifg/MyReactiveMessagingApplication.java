package fahmi.ifg;

import io.smallrye.reactive.messaging.annotations.Merge;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;
import org.eclipse.microprofile.reactive.messaging.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class MyReactiveMessagingApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyReactiveMessagingApplication.class.getName());
    @Inject    // build locally
    ManagedExecutor managedExecutor;

    @Inject	// build locally
    ThreadContext threadContext;

    @Inject
    CsvFileProcessor csvFileProcessor;

    @Incoming("data-csv-consumer")
    @Merge(Merge.Mode.MERGE)
    public CompletionStage<Void> consumeDataDealerPayment(Message<String> message) {
        LOGGER.info("Incoming message: {}", message.getPayload());
        message.ack();
        AtomicReference<String> data = new AtomicReference<>(message.getPayload());
        return managedExecutor.runAsync(threadContext.contextualRunnable(() -> {
            try {
                String payloadString = data.get();
                byte[] fileContent = payloadString.getBytes(StandardCharsets.UTF_8);
                // Proses file CSV
                csvFileProcessor.processCsvFile(fileContent);
                LOGGER.info("Payload: {}", payloadString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }


}
