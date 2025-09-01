package xyz.yuanowo.keelungsightsviewer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuanowo.keelungsightsviewer.model.ResponseMessage;
import xyz.yuanowo.keelungsightsviewer.service.HelloService;


@Tag(name = "Hello")
@RestController
@Validated
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) { this.helloService = helloService; }

    @Operation(summary = "Hello World", description = "回傳 Hello!")
    @ApiResponse(responseCode = "200", description = "OK",
                 content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
    @ApiResponse(responseCode = "418", description = "I'm a teapot",
                 content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
    @GetMapping("/hello")
    public ResponseMessage hello(
            @Schema(description = "你的名字", pattern = "^[a-zA-Z0-9_]+$", example = "Alice", defaultValue = "World")
            @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "name 只能由英文字母、數字和底線組成")
            @RequestParam(name = "name", defaultValue = "World") String name
                                ) {
        return helloService.getGreeting(name);
    }

}
