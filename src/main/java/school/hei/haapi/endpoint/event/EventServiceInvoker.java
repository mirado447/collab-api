package school.hei.haapi.endpoint.event;

import java.io.Serializable;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.event.model.TypedEvent;
import school.hei.haapi.endpoint.event.model.gen.LateFeeVerified;
import school.hei.haapi.endpoint.event.model.gen.UserUpserted;
import school.hei.haapi.service.LateFeeService;
import school.hei.haapi.service.UserUpsertedService;

@Component
@AllArgsConstructor
@Slf4j
public class EventServiceInvoker implements Consumer<TypedEvent> {

  private final UserUpsertedService userUpsertedService;
  private final LateFeeService lateFeeService;

  @Override
  public void accept(TypedEvent typedEvent) {
    Serializable payload = typedEvent.getPayload();
    if (UserUpserted.class.getTypeName().equals(typedEvent.getTypeName())) {
      userUpsertedService.accept((UserUpserted) payload);
    } else if (LateFeeVerified.class.getTypeName().equals(typedEvent.getTypeName())) {
      lateFeeService.accept((LateFeeVerified) payload);
    } else {
      log.error("Unexpected type for event={}", typedEvent);
    }
  }
}
