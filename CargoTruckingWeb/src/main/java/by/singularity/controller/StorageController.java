package by.singularity.controller;

import by.singularity.dto.ClientDto;
import by.singularity.dto.StorageDto;
import by.singularity.entity.Client;
import by.singularity.entity.Storage;
import by.singularity.service.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/storages")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    //todo add pagination
    @GetMapping()
    public void getAll(HttpServletResponse response) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        List<Storage> storages = storageService.getAllStorages();
        responseMap.put("content", storages);
        responseMap.put("totalElements", storages.size());
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @GetMapping("/{id}")
    public Storage getById(@PathVariable Long id) {
        return storageService.getStorage(id);
    }


    @PostMapping()
    public String addClient(@RequestBody @Valid StorageDto storageDto) {
        return storageService.createStorage(storageDto);
    }

    @PutMapping("/{id}")
    public void updateStorage(@PathVariable Long id, @RequestBody StorageDto storageDto) {
        storageService.updateStorage(storageDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteStorage(@PathVariable Long id) {
        storageService.deleteStorage(id);
    }
}

