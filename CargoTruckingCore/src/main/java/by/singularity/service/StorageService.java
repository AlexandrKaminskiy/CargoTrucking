package by.singularity.service;

import by.singularity.dto.StorageDto;
import by.singularity.entity.Storage;
import by.singularity.exception.ClientException;
import by.singularity.exception.StorageException;
import by.singularity.mapper.StorageMapper;
import by.singularity.repository.ClientRepository;
import by.singularity.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;
    private final ClientRepository clientRepository;
    private final StorageMapper storageMapper;

    public String createStorage(StorageDto storageDto) throws ClientException {
        if (clientRepository.findById(storageDto.getClientId()).isEmpty()) {
            throw new ClientException("client with id " + storageDto.getClientId() + " not found");
        }
        Storage storage = storageMapper.toModel(storageDto);
        storageRepository.save(storage);
        log.info("STORAGE WITH ID {} CREATED", storage.getId());
        return storage.getAddress();
    }

    public void updateStorage(StorageDto storageDto, Long id) throws StorageException {
        Optional<Storage> storageOpt = storageRepository.findById(id);
        if (storageOpt.isEmpty()) {
            throw new StorageException("storage with id " + storageDto.getId() + " not found");
        }
        Storage storage = storageOpt.get();
        Optional.ofNullable(storageDto.getName()).ifPresent(storage::setName);
        Optional.ofNullable(storageDto.getAddress()).ifPresent(storage::setAddress);
        storageRepository.save(storage);
        log.info("STORAGE WITH ID {} UPDATED", storage.getId());
    }

    public List<Storage> getAllStorages() {
        //todo
        return storageRepository.findAll();
    }

    public void deleteStorage(Long id) {
        storageRepository.deleteById(id);
    }

    public Storage getStorage(Long id) throws StorageException {
        Optional<Storage> storageOpt = storageRepository.findById(id);
        if (storageOpt.isEmpty()) {
            throw new StorageException("storage with id" + id + "not exist");
        }
        return storageOpt.get();
    }

}
