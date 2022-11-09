package com.tdns.toks.core.common.service;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class SlackService {
    @Value(value = "${slack.token}")
    String token;
    @Value(value = "${slack.channel.user-alert}")
    String userAlertChannel;
    public void slackSendMessage(String message){
        try{
            Slack slack = Slack.getInstance();
            slack.methods(token).chatPostMessage(req -> req.channel(userAlertChannel).text(message));
        } catch (SlackApiException | IOException e) {
            log.error(e.getMessage());
        }
    }
}

