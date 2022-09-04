package by.singularity.service;

import by.singularity.dto.StorageDto;
import by.singularity.entity.Storage;
import by.singularity.mapper.StorageMapper;
import by.singularity.repository.impl.ClientRepository;
import by.singularity.repository.impl.StorageRepository;
import by.singularity.repository.jparepo.StorageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;
    private final StorageJpaRepository storageJpaRepository;
    private final ClientRepository clientRepository;
    private final StorageMapper storageMapper;

    public String createStorage(StorageDto storageDto) {
        //todo
        if (clientRepository.findById(storageDto.getClientId()).isEmpty()) {
            return null;
        }
        Storage storage = storageMapper.toModel(storageDto);
        storageJpaRepository.save(storage);
        return storage.getAddress();
    }

    public void updateStorage(StorageDto storageDto, Long id) {
        //todo
        Optional<Storage> storageOpt = storageRepository.findById(id);
        if (storageOpt.isEmpty()) {
            return;
        }
        Storage storage = storageOpt.get();
        Optional.ofNullable(storageDto.getName()).ifPresent(storage::setName);
        Optional.ofNullable(storageDto.getAddress()).ifPresent(storage::setAddress);
        storageJpaRepository.save(storage);
    }

    public List<Storage> getAllStorages() {
        //todo
        return storageRepository.findAll();
    }

    public void deleteStorage(Long id) {
        storageRepository.deleteById(id);
    }

    public Storage getStorage(Long id) {
        Optional<Storage> storageOpt = storageRepository.findById(id);
        if (storageOpt.isEmpty()) {
            //todo
            return null;
        }
        return storageOpt.get();
    }

}
