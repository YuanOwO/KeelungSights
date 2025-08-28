package xyz.yuanowo.keelungsightsviewer.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
public class Sight implements Serializable {

    @Schema(description = "景點名稱", example = "摩艾DUMDUM")
    private String name;

    @Schema(description = "景點所在行政區", example = "中正區")
    private String district;

    @Schema(description = "景點類別", example = "石雕")
    private String category;

    @Schema(
            description = "景點介紹",
            example = "基隆私房景點，縮小版智利\uD83C\uDDE8\uD83C\uDDF1復活節島！\n" +
                    "還有遊艇\uD83D\uDEE5\uFE0F、基隆嶼、基隆的海❤\uFE0F"
    )
    private String description;

    @Schema(description = "景點地址", example = "基隆市中正區北寧路2號")
    private String address;

    @Schema(description = "地圖連結", example = "https://maps.app.goo.gl/MXQsFcCQkUYHhUk87")
    private String mapURL;

    @Schema(description = "景點照片")
    private String photoURL;

    @Schema(description = "來源網址", example = "https://www.example.com/dumdum")
    private String sourceURL;

}