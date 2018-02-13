package com.mev.jbot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mev.jbot.model.SlackMessage;
import com.mev.jbot.model.Action;
import com.mev.jbot.model.RichAttachment;
import me.ramswaroop.jbot.core.slack.models.Attachment;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * This is a Slack Webhook sample. Webhooks are nothing but POST calls to
 * Slack with data relevant to your users. You can send the data
 * in the POST call in either ways:
 * 1) Send as a JSON string as the payload parameter in a POST request
 * 2) Send as a JSON string as the body of a POST request
 */
@Component
public class SlackWebhooks {

    private static final Logger logger = LoggerFactory.getLogger(SlackWebhooks.class);

    /**
     * The Url you get while configuring a new incoming webhook
     * on Slack. You can setup a new incoming webhook
     * <a href="https://my.slack.com/services/new/incoming-webhook/">here</a>.
     */
    @Value("${slackIncomingWebhookUrl}")
    private String slackIncomingWebhookUrl;

    /**
     * Make a POST call to the incoming webhook url.
     */
    @PostConstruct
    public void invokeSlackWebhook() {
        RestTemplate restTemplate = new RestTemplate();
        SlackMessage richMessage = new SlackMessage();
        richMessage.setText("SkyNet online");
        // set attachments
        RichAttachment[] attachments = new RichAttachment[2];
        Action[] actions = new Action[1];
        actions[0] = setDefAction();
        attachments[0] = new RichAttachment();
        attachments[1] = new RichAttachment();
        attachments[0].setText("<https://ru.wikipedia.org/wiki/%D0%A1%D0%BA%D0%B0%D0%B9%D0%BD%D0%B5%D1%82|Click here> for details!");
        attachments[1].setActions(actions);
        richMessage.setAttachments(attachments);

        // For debugging purpose only
        try {
            logger.debug("Reply (RichMessage): {}", new ObjectMapper().writeValueAsString(richMessage));
        } catch (JsonProcessingException e) {
            logger.debug("Error parsing RichMessage: ", e);
        }

        // Always remember to send the encoded message to Slack
        try {
            restTemplate.postForEntity(slackIncomingWebhookUrl, richMessage.encodedMessage(), String.class);
        } catch (RestClientException e) {
            logger.error("Error posting to Slack Incoming Webhook: ", e);
        }
    }

    @PostMapping
    public void testWebhook() {
        RestTemplate restTemplate = new RestTemplate();
        RichMessage richMessage = new RichMessage("MENY MENY TEXT");
        // set attachments
        Attachment[] attachments = new Attachment[1];
        attachments[0] = new Attachment();
        attachments[0].setText("<https://ru.wikipedia.org/wiki/%D0%A1%D0%BA%D0%B0%D0%B9%D0%BD%D0%B5%D1%82|Click here> for details!");
        richMessage.setAttachments(attachments);

        // For debugging purpose only
        try {
            logger.debug("Reply (RichMessage): {}", new ObjectMapper().writeValueAsString(richMessage));
        } catch (JsonProcessingException e) {
            logger.debug("Error parsing RichMessage: ", e);
        }

        // Always remember to send the encoded message to Slack
        try {
            restTemplate.postForEntity(slackIncomingWebhookUrl, richMessage.encodedMessage(), String.class);
        } catch (RestClientException e) {
            logger.error("Error posting to Slack Incoming Webhook: ", e);
        }
    }

    private Action setDefAction() {
        Action action = new Action();
        action.setType("button");
        action.setText("Book flights \uD83D\uDEEB");
        action.setUrl("https://flights.example.com/book/r123456");
        action.setStyle("danger");
        return action;
    }

    @Override
    public String toString() {
        return "SlackWebhooks{" +
                "slackIncomingWebhookUrl='" + slackIncomingWebhookUrl + '\'' +
                '}';
    }
}
