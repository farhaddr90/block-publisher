package com.simorghsoftech.core.repositories;

import com.simorghsoftech.core.models.Block;

import java.util.List;

public interface BlockRepository {
    Block getBlockByNum(int number);

    Block getBlockByHash(String hash);

    List<Block> getBlocks(int start, int end);
}
