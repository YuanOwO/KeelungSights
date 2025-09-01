package xyz.yuanowo.keelungsightsviewer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuanowo.keelungsightsviewer.model.ResponseList;
import xyz.yuanowo.keelungsightsviewer.model.Sight;
import xyz.yuanowo.keelungsightsviewer.service.SightsService;

import java.util.List;


@Tag(name = "Sight")
@RestController
@Validated
public class SightController {

    private final SightsService sightsService;

    public SightController(SightsService sightsService) { this.sightsService = sightsService; }

    @Operation(summary = "取得景點列表", description = "根據行政區取得景點列表")
    @GetMapping("/sights")
    public ResponseList<Sight> getSightsList(
            @Schema(description = "基隆市的行政區", example = "中正") @NotNull(message = "行政區不可為空")
            @RequestParam(name = "district") String district
                                            ) {
        List<Sight> sights = sightsService.getSightsByDistrict(district);
        return new ResponseList<>(sights);
    }

    @Operation(summary = "取得景點詳細資訊", description = "根據景點 ID 取得景點詳細資訊")
    @GetMapping("/sight/{id}")
    public Sight getSightDetails(@Schema(description = "景點 ID") @NotNull(message = "ID 不可為空")
                                 @Min(value = 1, message = "ID 必須是正整數") @PathVariable(value = "id") Long id
                                ) {
        return sightsService.getSightById(id);
    }

}
