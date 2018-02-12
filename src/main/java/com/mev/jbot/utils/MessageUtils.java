package com.mev.jbot.utils;

import org.springframework.stereotype.Component;

import static com.mev.jbot.UserConst.USER_MESSAGE;

@Component
public class MessageUtils {

    public String[] getMessageParams(String message) {
        StringBuilder builder = new StringBuilder();
        String[] params = message.split(" ");

        if (params.length > 2) {

            for (int i = 2; i < params.length; i++) {
                builder.append(params[i]).append(" ");
            }

            params[USER_MESSAGE] = builder.toString();

        }

        return params;
    }
}
