package xyz.yuanowo.keelungsightsviewer.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


@Data
public class Image implements Serializable {

    @Schema(description = "圖片網址", example = "https://www.example.com/image.jpg")
    private String url;

    @Schema(description = "替代文字", example = "這是一張範例圖片")
    private String alternateText;

}
