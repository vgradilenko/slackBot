package com.mev.jbot;

import me.ramswaroop.jbot.core.slack.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlackBotService {
    private final TemplateMessage templateMessage;
    private final MessageUtils utils;

    private final String[] vacationDictionary = {"vacation", "отпуск"};
    private final String[] sickDictionary = {"sick", "заболел"};
    private final String[] homeWorkDictionary = {"homework", "дома"};

    @Autowired
    public SlackBotService(TemplateMessage templateMessage, MessageUtils utils) {
        this.templateMessage = templateMessage;
        this.utils = utils;
    }

    public Message getAnswer(String message) {

        if (checkUserMessage(message, vacationDictionary)) {

            try {
                return new Message(templateMessage.getVacationTemplate(message));
            } catch (SlackBotTemplateException slackBotTemplateException) {
                return new Message(templateMessage.getHelpMessage());
            }

        } else if (checkUserMessage(message, sickDictionary)) {

            try {
                return new Message(templateMessage.getSickLeaveTemplate(message));
            } catch (SlackBotTemplateException e) {
                return new Message(templateMessage.getHelpMessage());
            }

        } else if (checkUserMessage(message, homeWorkDictionary)) {

            try {
                return new Message(templateMessage.getWorkAtHomeTemplate(message));
            } catch (SlackBotTemplateException e) {
                return new Message(templateMessage.getHelpMessage());
            }

        } else {
            return new Message(templateMessage.getHelpMessage());
        }
    }

    private boolean checkUserMessage(String message, String[] dictionary) {
        String[] params = utils.getMessageParams(message);

        for (String dictionaryWord : dictionary) {
            for (String param : params) {
                if (dictionaryWord.equals(param)) {
                    return true;
                }
            }
        }

        return false;
    }
}
