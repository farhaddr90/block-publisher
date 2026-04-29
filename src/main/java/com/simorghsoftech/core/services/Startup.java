package com.simorghsoftech.core.services;

import com.simorghsoftech.core.entities.BlockEntity;
import com.simorghsoftech.core.repositories.BlockEntityRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;

import java.util.List;

public class Startup {

    private final BlockService blockService;
    private final BlockEntityRepository repository;

    Startup(
            BlockService blockService,
            BlockEntityRepository repository
    ) {
        this.blockService = blockService;
        this.repository = repository;
    }

    public void start(@Observes StartupEvent ev) {
        List<BlockEntity> blockEntities = repository.findAll().list();
        if (blockEntities.isEmpty()) {
            blockService.storeLatestBlockFromBlockchain();
        }
    }
}
