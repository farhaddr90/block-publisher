package com.simorghsoftech.core.repositories;

import com.simorghsoftech.core.models.Block;

import java.util.List;

public class DbBlockRepository implements BlockRepository {
    @Override
    public Block getBlockByNum(int number) {
        return null;
    }

    @Override
    public Block getBlockByHash(String hash) {
        return null;
    }

    @Override
    public List<Block> getBlocks(int start, int end) {
        return List.of();
    }
}
