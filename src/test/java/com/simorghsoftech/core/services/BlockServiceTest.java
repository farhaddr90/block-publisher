package com.simorghsoftech.core.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simorghsoftech.core.models.Block;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.List;

@QuarkusTest
class BlockServiceTest {

    private final ObjectMapper mapper;
    private final BlockService blockService;

    BlockServiceTest(BlockService blockService) {
        mapper = new ObjectMapper();
        this.blockService = blockService;
    }

    @Test
    void findBlocksInRange() throws JsonProcessingException {
        int start = 21183316;
        int end = 21183318;
        List<Block> blocks = blockService.findBlocksInRange(start, end);
        Block first = blocks.getFirst();
        JsonNode json = mapper.readTree(first.getData());
        String result = json.path("result").toString();
        System.out.println(first.getData());
    }

    @Test
    void storeFromBlockchain() {
    }

    @Test
    void latestScannedBlockNumber() {
    }
}