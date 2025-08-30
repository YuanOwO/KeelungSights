package xyz.yuanowo.keelungsightsviewer.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xyz.yuanowo.keelungsightsviewer.model.ResponseMessage;


@Service
public class HelloService {

    public ResponseMessage getGreeting(String name) {
        return new ResponseMessage(HttpStatus.OK, String.format("Hello %s!", name));
    }

}
