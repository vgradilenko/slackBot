package com.mev.jbot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import me.ramswaroop.jbot.core.slack.models.RichMessage;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SlackMessage extends RichMessage{

    private RichAttachment[] attachments;

    @Override
    public RichAttachment[] getAttachments() {
        return attachments;
    }

    public void setAttachments(RichAttachment[] attachments) {
        this.attachments = attachments;
    }
}
