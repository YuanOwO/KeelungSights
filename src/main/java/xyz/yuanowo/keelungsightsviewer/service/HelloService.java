package xyz.yuanowo.keelungsightsviewer.service;

import org.springframework.stereotype.Service;
import xyz.yuanowo.keelungsightsviewer.model.Message;


@Service
public class HelloService {

    public Message getGreeting(String name) {
        return new Message(String.format("Hello %s!", name));
    }

}
