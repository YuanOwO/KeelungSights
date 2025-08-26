package xyz.yuanowo.keelungsightsviewer.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;


public class HelloWorld implements Serializable {

    @Schema(example = "Hello World!")
    private String message;

    public HelloWorld() { }

    public HelloWorld(String message) { this.message = message; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

}
