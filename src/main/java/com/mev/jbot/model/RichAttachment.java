package com.mev.jbot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import me.ramswaroop.jbot.core.slack.models.Attachment;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RichAttachment extends Attachment{

    private Action actions[];

    public Action[] getActions() {
        return actions;
    }

    public void setActions(Action[] actions) {
        this.actions = actions;
    }
}
