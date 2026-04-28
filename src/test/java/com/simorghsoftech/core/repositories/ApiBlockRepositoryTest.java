package com.simorghsoftech.core.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simorghsoftech.core.models.Block;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

@QuarkusTest
class ApiBlockRepositoryTest {

    private final ApiBlockRepository apiBlockRepo;
    private final ObjectMapper mapper;

    ApiBlockRepositoryTest(
            ApiBlockRepository blockRepo
    ) {
        this.apiBlockRepo = blockRepo;
        this.mapper = new ObjectMapper();
    }

    @Test
    void getBlockByHash() throws JsonProcessingException {
        String hash = "0x04ff5627c3648a0685255284de2c13f73a0bad1a07e759153bbb0f715b46f3a7";
        Block block = apiBlockRepo.getBlockByHash(hash);
        Assertions.assertNotNull(block);
        Assertions.assertNotNull(block.getData());
        Assertions.assertEquals(
                hash,
                mapper.readTree(block.getData()).path("result").path("hash").asText()
        );
    }

    @Test
    void getBlocks() {
        List<Block> blocks = apiBlockRepo.getBlocks(24970248, 24970250);
        Assertions.assertNotNull(blocks);
        Assertions.assertEquals(3, blocks.size());
    }

    @Test
    void getBlockByNum() throws JsonProcessingException {
        int blockNum = 24970248;
        Block block = apiBlockRepo.getBlockByNum(blockNum);

        Assertions.assertNotNull(block);
        Assertions.assertNotNull(block.getData());
        Assertions.assertEquals(
                Numeric.toHexStringWithPrefix(new BigInteger(String.valueOf(blockNum))),
                mapper.readTree(block.getData()).path("result").path("number").asText()
        );

    }

}