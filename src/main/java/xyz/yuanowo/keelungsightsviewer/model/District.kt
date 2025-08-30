package xyz.yuanowo.keelungsightsviewer.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


@Data
@Schema(description = "行政區資料")
public class District implements Serializable {

    @Schema(description = "郵遞區號", example = "200")
    private String zipcode;
    @Schema(description = "中文名稱", example = "仁愛區")
    private String zh;
    @Schema(description = "英文名稱", example = "Renai")
    private String en;

}
