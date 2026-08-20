package com.axion.kyc.verification;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.axion.kyc.entity.VerificationAction;

@Component
public class VerificationHandlerRegistry {

    private final Map<VerificationAction, VerificationHandler>
            handlers = new EnumMap<>(VerificationAction.class);

    public VerificationHandlerRegistry(
            List<VerificationHandler> handlerList) {

        for (VerificationHandler handler : handlerList) {

            VerificationAction action =
                    handler.getAction();

            if (handlers.containsKey(action)) {

                throw new IllegalStateException(
                        "Duplicate verification handler: "
                        + action
                );
            }

            handlers.put(action, handler);
        }
    }

    public VerificationHandler getHandler(
            VerificationAction action) {

        VerificationHandler handler =
                handlers.get(action);

        if (handler == null) {

            throw new IllegalStateException(
                    "No verification handler registered for: "
                    + action
            );
        }

        return handler;
    }
}