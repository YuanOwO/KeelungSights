package xyz.yuanowo.keelungsightsviewer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuanowo.keelungsightsviewer.model.HelloWorld;


@Tag(name = "Hello")
@RestController
public class HelloController {

    @Operation(summary = "Hello World", description = "回傳 Hello World!")
    @ApiResponse(
            responseCode = "200",
            description = "我還活著!",
            content = @Content(schema = @Schema(implementation = HelloWorld.class))
    )
    @GetMapping("/")
    public HelloWorld hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new HelloWorld(String.format("Hello %s!", name));
    }

}
