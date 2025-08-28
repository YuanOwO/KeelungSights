package xyz.yuanowo.keelungsightsviewer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuanowo.keelungsightsviewer.model.Message;


@Tag(name = "Hello")
@RestController
@Validated
public class HelloController {

    @Operation(summary = "Hello World", description = "回傳 Hello!")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "我還活著!",
                            content = @Content(schema = @Schema(implementation = Message.class))
                    ),
                    @ApiResponse(responseCode = "422", description = "參數驗證失敗")
            }
    )
    @GetMapping("/")
    public Message hello(
            @Parameter(description = "你的名字")
            @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "只能由英文字母、數字和底線組成")
            @RequestParam(defaultValue = "World")
            String name
    ) {
        return new Message(String.format("Hello %s!", name));
    }

}
