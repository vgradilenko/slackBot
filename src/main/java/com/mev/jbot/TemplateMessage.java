package com.mev.jbot;

import com.mev.jbot.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.mev.jbot.UserConst.*;
import static com.mev.jbot.utils.FontFormat.*;

@Component
public class TemplateMessage {

    private LocalDate localDate = LocalDate.now();
    private MessageUtils utils;

    @Autowired
    public TemplateMessage(MessageUtils messageUtils) {
        this.utils = messageUtils;
    }

    public String getHelpMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(ONE_BLOCKQUOTE).append("Examples to execute command!").append("\n")
                .append("\n")
                .append("1) Generate ")
                .append(BOLD).append("vacation").append(BOLD)
                .append(" message: ")
                .append(ITALIC).append(" vacation, отпуск [message]").append(ITALIC)
                .append("\n\n")

                .append("2) Generate ")
                .append(BOLD).append("sick-leave").append(BOLD)
                .append(" message: ")
                .append(ITALIC).append("sick, заболел [message]").append(ITALIC)
                .append("\n\n")

                .append("3) Generate ")
                .append(BOLD).append("work at home").append(BOLD)
                .append(" message: ")
                .append(ITALIC).append("homework, дома [message]").append(ITALIC)
                .append("\n\n")

                .append(ONE_BLOCKQUOTE).append("Example: homework 3 cause :+1:").append("\n");

        return builder.toString();
    }

    public String getVacationTemplate(String message) throws SlackBotTemplateException {
        String[] params = utils.getMessageParams(message);
        StringBuilder builder = new StringBuilder();

        try {
            builder.append(MULTY_CODE)
                    .append("Получатели: alex@mev.com, andrew@mev.com, maxim.sukhovyi@mev.com\n")
                    .append("\n")
                    .append("Тема: Согласование отпуска\n")
                    .append("\n")
                    .append("Сообщение:\n")
                    .append("Добрый день, коллеги!\n")
                    .append("Прошу, пожалуйста, согласовать отпуск с ")
                    .append(localDate)
                    .append(". по ");

            if (!params[USER_DEYS].isEmpty()) {
                builder.append(localDate.plusDays(Integer.parseInt(params[USER_DEYS])));
            } else {
                builder.append(localDate.plusDays(MAX_FREE_DAYS));
            }

            builder.append(".");

            if (!message.isEmpty()) {
                builder.append(" ( ")
                        .append(params[USER_MESSAGE])
                        .append(" )");
            }

            builder.append("\n")
                    .append("Заранее благодарю.")
                    .append(MULTY_CODE);

            return builder.toString();
        } catch (Exception e) {
            throw new SlackBotTemplateException(e.getMessage());
        }

    }

    public String getSickLeaveTemplate(String message) throws SlackBotTemplateException {
        String[] params = utils.getMessageParams(message);

        StringBuilder builder = new StringBuilder();
        try {
            builder.append(MULTY_CODE)
                    .append("Получатели: alex@mev.com, andrew@mev.com, maxim.sukhovyi@mev.com\n")
                    .append("\n")
                    .append("Тема: Согласование отпуска\n")
                    .append("\n")
                    .append("Сообщение:\n")
                    .append("Добрый день, коллеги! \n")
                    .append("Прошу, пожалуйста, предоставить ");

            if (!params[USER_DEYS].isEmpty()) {
                builder.append(params[USER_DEYS]);
            } else {
                builder.append(params[MAX_FREE_DAYS]);
            }

            builder.append(" оплачиваемого больничного. Страховая о моем состоянии здоровья уведомлена. \n")
                    .append("Заранее благодарю!")
                    .append(MULTY_CODE);

            return builder.toString();
        } catch (Exception e) {
            throw new SlackBotTemplateException(e.getMessage());
        }
    }

    public String getWorkAtHomeTemplate(String message) throws SlackBotTemplateException {
        String[] params = utils.getMessageParams(message);

        StringBuilder builder = new StringBuilder();
        try {
            builder.append(MULTY_CODE)
                    .append("Получатели: alex@mev.com, andrew@mev.com, maxim.sukhovyi@mev.com\n")
                    .append("\n")
                    .append("Тема: Согласование отпуска\n")
                    .append("\n")
                    .append("Сообщение:\n")
                    .append("Добрый день, коллеги!\n")
                    .append("Прошу, пожалуйста, позволить работать из дома с ")
                    .append(localDate)
                    .append(" по ")
                    .append(localDate.plusDays(Integer.parseInt(params[USER_DEYS])))
                    .append(" г. по причине ").append(params[USER_MESSAGE])
                    .append("\n")
                    .append("Заранее благодарю!")
                    .append(MULTY_CODE);

            return builder.toString();
        } catch (Exception e) {
            throw new SlackBotTemplateException(e.getMessage());
        }
    }
}
