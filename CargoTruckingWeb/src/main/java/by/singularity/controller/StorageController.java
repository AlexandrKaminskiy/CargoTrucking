package by.singularity.controller;

import by.singularity.dto.StorageDto;
import by.singularity.entity.Storage;
import by.singularity.exception.ClientException;
import by.singularity.exception.StorageException;
import by.singularity.pojo.StorageUpdateDto;
import by.singularity.service.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/storages")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @GetMapping()
    public void getAll(HttpServletResponse response,
                       String name,
                       Pageable pageable) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        Page<Storage> storages = storageService.getAllStorages(name, pageable);
        responseMap.put("content", storages);
        responseMap.put("totalElements", storages.getContent().size());
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @GetMapping("/{id}")
    public Storage getById(@PathVariable Long id) throws StorageException {
        return storageService.getStorage(id);
    }


    @PostMapping()
    public String addStorage(@RequestBody @Valid StorageDto storageDto) throws ClientException {
        return storageService.createStorage(storageDto);
    }

    @PutMapping("/{id}")
    public void updateStorage(@PathVariable Long id,
                              @RequestBody @Valid StorageUpdateDto storageUpdateDto) throws StorageException {
        storageService.updateStorage(storageUpdateDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteStorage(@PathVariable Long id) throws StorageException {
        storageService.deleteStorage(id);
    }
}

