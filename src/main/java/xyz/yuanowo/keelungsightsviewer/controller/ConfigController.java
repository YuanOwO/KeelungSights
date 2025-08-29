package xyz.yuanowo.keelungsightsviewer.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuanowo.keelungsightsviewer.model.District;

import java.io.IOException;
import java.util.List;


@Tag(name = "Configuration")
@RestController
public class ConfigController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Operation(summary = "取得基隆市行政區列表", description = "取得基隆市所有行政區的郵遞區號、中文名稱及英文名稱")
    @GetMapping("/districts")
    public List<District> getDistricts() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/keelungDistricts.json");
        return objectMapper.readValue(
                resource.getInputStream(),
                new TypeReference<List<District>>() { }
        );
    }

}
