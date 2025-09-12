package xyz.yuanowo.keelungsights.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.yuanowo.keelungsights.model.District;
import xyz.yuanowo.keelungsights.model.ResponseList;

import java.io.IOException;
import java.util.List;


@Tag(name = "Configuration")
@RestController
public class ConfigController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Operation(summary = "取得基隆市行政區列表", description = "取得基隆市所有行政區的郵遞區號、中文名稱及英文名稱")
    @GetMapping("/districts")
    public ResponseList<District> getDistricts() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/keelungDistricts.json");
        List<District> list = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() { });
        return new ResponseList<>(list);
    }

}
