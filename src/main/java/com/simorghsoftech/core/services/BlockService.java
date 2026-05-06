package com.simorghsoftech.core.services;

import com.simorghsoftech.core.entities.BlockEntity;
import com.simorghsoftech.core.models.Block;
import com.simorghsoftech.core.repositories.ApiBlockRepository;
import com.simorghsoftech.core.repositories.BlockEntityRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

@ApplicationScoped
public class BlockService {

    private final BlockEntityRepository entityRepo;
    private final ApiBlockRepository client;

    public BlockService(
            BlockEntityRepository blockEntityRepository,
            ApiBlockRepository apiBlockRepository
    ) {
        this.entityRepo = blockEntityRepository;
        this.client = apiBlockRepository;
    }

    public List<Block> findBlocksInRange(long start, long end) {
        List<BlockEntity> entities = entityRepo.findExistingBlocksInRange(start, end);
        return toModel(entities);
    }

    private List<Block> toModel(List<BlockEntity> entities) {
        return entities.stream().map(this::toModel).toList();
    }

    private Block toModel(BlockEntity entity) {
        String data = byteToString(entity.rawData);
        return new Block(entity.number, entity.hash, data);
    }

    private List<BlockEntity> toEntity(List<Block> blocks) {
        return blocks.stream().map(this::toEntity).toList();
    }

    private BlockEntity toEntity(Block block) {
        BlockEntity entity = new BlockEntity();
        entity.number = block.getNumber();
        entity.hash = block.getHash();
        entity.rawData = toByteArray(block.getData());
        return entity;
    }

    private String byteToString(byte[] byteData) {
        return byteData != null
                ? new String(byteData, StandardCharsets.UTF_8)
                : null;
    }

    private byte[] toByteArray(String data) {
        return data != null
                ? data.getBytes(StandardCharsets.UTF_8)
                : null;
    }

    @Transactional
    public void storeFromBlockchain(long start, long end) {
        List<Block> blocks = client.getBlocks(start, end);
        List<BlockEntity> entities = toEntity(blocks);
        entityRepo.persist(entities);
    }

    public long latestScannedBlock() {
        return entityRepo.findLatestBlockNumber();
    }

    @Transactional
    public void storeLatestBlockFromBlockchain() {
        Block latestBlock = client.getLatestBlock();
        BlockEntity entity = toEntity(latestBlock);
        entityRepo.persist(entity);
    }
}
