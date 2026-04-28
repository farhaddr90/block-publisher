package com.simorghsoftech.core.services;

import com.simorghsoftech.core.entities.BlockEntity;
import com.simorghsoftech.core.models.Block;
import com.simorghsoftech.core.repositories.ApiBlockRepository;
import com.simorghsoftech.core.repositories.BlockEntityRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<Integer> findMissingBlockNumbers(int start, int end) {

        List<Integer> existing = entityRepo.findExistingNumbersInRange(start, end);

        Set<Integer> existingSet = new HashSet<>(existing);
        List<Integer> missing = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            if (!existingSet.contains(i)) {
                missing.add(i);
            }
        }

        return missing;
    }

//    public void m(int start, int end) {
//        List<Integer> missingBlockNumbers = findMissingBlockNumbers(start, end);
//        client.
//    }

    public List<Block> findBlocksInRange(int start, int end) {
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
    public void storeFromBlockchain(int start, int end) {
        List<Block> blocks = client.getBlocks(start, end);
        List<BlockEntity> entities = toEntity(blocks);
        entityRepo.persist(entities);
    }

    public int latestScannedBlock(){
        return entityRepo.findLatestBlockNumber();
    }
}
