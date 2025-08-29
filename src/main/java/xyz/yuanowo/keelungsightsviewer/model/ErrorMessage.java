package xyz.yuanowo.keelungsightsviewer.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Schema(description = "錯誤訊息")
public class ErrorMessage implements Serializable {

    @Schema(description = "HTTP 狀態碼")
    private final Integer status;

    @Schema(description = "錯誤類型")
    private final String error;

    @Schema(description = "錯誤訊息")
    private final String message;

    @Schema(description = "時間戳記", example = "1980-01-01T08:00:00.000000000")
    private final String time = LocalDateTime.now().toString();

}
