package com.simorghsoftech.api.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simorghsoftech.api.responses.EthBlock;
import com.simorghsoftech.core.models.Block;
import com.simorghsoftech.core.services.BlockService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/v1/block")
public class BlockResource {

    private final BlockService blockService;
    private final ObjectMapper mapper;

    BlockResource(
            final BlockService blockService
    ) {
        this.blockService = blockService;
        this.mapper = new ObjectMapper();
    }

    public record RangeOfBlocksRequest(long start, long end) {
    }

    @POST
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<EthBlock> getBlocks(RangeOfBlocksRequest request) {
        return getBlocks(request.start, request.end);
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public List<EthBlock> getBlocks(@QueryParam("start") long start) {
        long end = blockService.latestScannedBlock();
        return getBlocks(start, end);
    }

    public List<EthBlock> getBlocks(long start, long end) {
        List<Block> blocks = blockService.findBlocksInRange(start, end);

        return blocks.stream()
                .map(b -> {
                    try {
                        return mapper.readValue(b.getData(), EthBlock.class);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    @POST
    @Path("/cache")
    public Response storeFromChain(RangeOfBlocksRequest request) {
        blockService.storeFromBlockchain(request.start(), request.end());
        return Response.ok().build();
    }
}
