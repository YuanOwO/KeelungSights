package xyz.yuanowo.keelungsightsviewer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuanowo.keelungsightsviewer.model.Sight;

import java.util.List;


@Tag(name = "Sight")
@RestController
@Validated
public class SightController {

    @Operation(summary = "取得景點列表", description = "根據行政區取得景點列表")
    @GetMapping("/sights")
    public List<Sight> getSightsList(
            @Parameter(description = "行政區", example = "中正區")
            @RequestParam(value = "district", required = true)
            String district
    ) {
        return List.of(new Sight(), new Sight());
    }

    @Operation(summary = "取得景點詳細資訊", description = "根據景點 ID 取得景點詳細資訊")
    @ApiResponse(
            responseCode = "200",
            description = "景點詳細資訊",
            content = @Content(schema = @Schema(implementation = Sight.class))
    )
    @GetMapping("/sight/{id}")
    public Sight getSightDetails(
            @Min(value = 1, message = "ID 必須是正整數")
            @PathVariable(value = "id", required = true)
            long id
    ) {
        return new Sight();
    }

}
