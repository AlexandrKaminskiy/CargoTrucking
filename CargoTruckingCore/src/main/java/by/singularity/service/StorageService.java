package by.singularity.service;

import by.singularity.dto.StorageDto;
import by.singularity.entity.QStorage;
import by.singularity.entity.Storage;
import by.singularity.exception.ClientException;
import by.singularity.exception.StorageException;
import by.singularity.mapper.impl.StorageMapper;
import by.singularity.pojo.StorageUpdateDto;
import by.singularity.repository.ClientRepository;
import by.singularity.repository.StorageRepository;
import by.singularity.repository.queryUtils.QPredicate;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;
    private final ClientRepository clientRepository;
    private final StorageMapper storageMapper;

    public String createStorage(StorageDto storageDto) throws ClientException {
        if (!clientRepository.existsById(storageDto.getClientId())) {
            throw new ClientException("client with id " + storageDto.getClientId() + " not found");
        }
        Storage storage = storageMapper.toModel(storageDto);
        storageRepository.save(storage);
        log.info("STORAGE WITH ID {} CREATED", storage.getId());
        return storage.getAddress();
    }

    public void updateStorage(StorageUpdateDto storageUpdateDto, Long id) throws StorageException {
        Storage storage = storageRepository.findById(id)
                .orElseThrow(()->new StorageException("storage with id " + storageUpdateDto.getId() + " not found"));
        Optional.ofNullable(storageUpdateDto.getName()).ifPresent(storage::setName);
        Optional.ofNullable(storageUpdateDto.getAddress()).ifPresent(storage::setAddress);
        storageRepository.save(storage);
        log.info("STORAGE WITH ID {} UPDATED", storage.getId());
    }

    public Page<Storage> getAllStorages(String name, Pageable pageable) {
        return storageRepository.findAll(getFindingPredicate(name), pageable);
    }

    public void deleteStorage(Long id) {
        storageRepository.deleteById(id);
    }

    public Storage getStorage(Long id) throws StorageException {
        return storageRepository.findById(id)
                .orElseThrow(()->new StorageException("storage with id" + id + "not exist"));
    }

    private Predicate getFindingPredicate(String name) {
        return QPredicate.builder()
                .add(name, QStorage.storage.name::eq)
                .buildAnd();
    }
}
