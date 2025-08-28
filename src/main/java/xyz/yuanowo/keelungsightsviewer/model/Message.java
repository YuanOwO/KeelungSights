package xyz.yuanowo.keelungsightsviewer.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


@Data
@Schema(description = "回應訊息")
public class Message implements Serializable {

    @Schema(example = "Hello World!")
    private final String message;

}
