package xyz.yuanowo.keelungsightsviewer.model;

import lombok.Data;

import java.io.Serializable;


@Data
public class ErrorMessage implements Serializable {

    private final String error;
    private final String message;

}
