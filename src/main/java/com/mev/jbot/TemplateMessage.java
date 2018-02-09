package com.mev.jbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.mev.jbot.UserConst.MAX_FREE_DAYS;
import static com.mev.jbot.UserConst.USER_DEYS;
import static com.mev.jbot.UserConst.USER_MESSAGE;

@Component
public class TemplateMessage {

    private LocalDate localDate = LocalDate.now();
    private MessageUtils utils;

    @Autowired
    public TemplateMessage(MessageUtils messageUtils) {
        this.utils = messageUtils;
    }

    public String getHelpMessage() {
        return "Examples to execute command!\n"
                + "Generate vacation message:" + "`vacation`, `отпуск` [message]\n"
                + "\n"
                + "Example: отпуск 7 cause\n"
                + "\n"
                + "Generate sick-leave message:" + "`sick`, `заболел` [message]\n"
                + "\n"
                + "Example: sick 3\n"
                + "\n"
                + "Generate work at home message:" + "`homework`, `дома` [message]\n"
                + "\n"
                + "Example: homework 3 cause\n"
                + "\n";
    }

    public String getVacationTemplate(String message) throws SlackBotTemplateException {
        String[] params = utils.getMessageParams(message);
        StringBuilder builder = new StringBuilder();

        try {
            builder.append("Получатели: alex@mev.com, andrew@mev.com, maxim.sukhovyi@mev.com\n")
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
                    .append("Заранее благодарю.");

            return builder.toString();
        } catch (Exception e) {
            throw new SlackBotTemplateException(e.getMessage());
        }

    }

    public String getSickLeaveTemplate(String message) throws SlackBotTemplateException {
        String[] params = utils.getMessageParams(message);

        StringBuilder builder = new StringBuilder();
        try {
            builder.append("Получатели: alex@mev.com, andrew@mev.com, maxim.sukhovyi@mev.com\n")
                    .append("\n")
                    .append("Тема: Согласование отпуска\n")
                    .append("\n")
                    .append("Сообщение:\n")
                    .append("Добрый день, коллеги! \n")
                    .append("\n")
                    .append(" Прошу, пожалуйста, предоставить ");
            if (!params[USER_DEYS].isEmpty()) {
                builder.append(params[USER_DEYS]);
            } else {
                builder.append(params[MAX_FREE_DAYS]);
            }
            builder.append(" оплачиваемого больничного. Страховая о моем состоянии здоровья уведомлена. \n")
                    .append("\n")
                    .append(" Заранее благодарю!");

            return builder.toString();
        } catch (Exception e) {
            throw new SlackBotTemplateException(e.getMessage());
        }
    }

    public String getWorkAtHomeTemplate(String message) throws SlackBotTemplateException {
        String[] params = utils.getMessageParams(message);
        StringBuilder builder = new StringBuilder();

        try {
            builder.append("Получатели: alex@mev.com, andrew@mev.com, maxim.sukhovyi@mev.com\n")
                    .append("\n")
                    .append("Тема: Согласование отпуска\n")
                    .append("\n")
                    .append("Сообщение:\n")
                    .append("Добрый день, коллеги!\n")
                    .append("\n")
                    .append("Прошу, пожалуйста, позволить работать из дома с ")
                    .append(localDate)
                    .append(" по ")
                    .append(localDate.plusDays(Integer.parseInt(params[USER_DEYS])))
                    .append(" г. по причине ").append(params[USER_MESSAGE])
                    .append("\n").append("\n").append("\"Заранее благодарю!\"");

            return builder.toString();
        } catch (Exception e) {
            throw new SlackBotTemplateException(e.getMessage());
        }
    }
}
