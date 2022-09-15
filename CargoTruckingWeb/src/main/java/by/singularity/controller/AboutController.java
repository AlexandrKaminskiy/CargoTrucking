package by.singularity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/about")
public class AboutController {
    @GetMapping()
    public Map<String,String> about() {
        return Map.of("applicationName","StoneCargoTrucking","version","1.0.0");
    }
}
