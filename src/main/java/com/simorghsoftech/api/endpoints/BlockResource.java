package com.simorghsoftech.api.endpoints;

import com.simorghsoftech.core.models.Block;
import com.simorghsoftech.core.services.BlockService;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/v1/block")
public class BlockResource {

    private final BlockService blockService;

    BlockResource(final BlockService blockService) {
        this.blockService = blockService;
    }

    @POST
    @Path("/get")
    public Response getBlocks(RangeOfBlocksRequest request) {
        List<Block> blocks = blockService.findBlocksInRange(request.start, request.end);
        return Response.ok(blocks).build();
    }

    public record RangeOfBlocksRequest(int start, int end) {
    }

    @POST
    @Path("/store")
    public Response storeFromChain(RangeOfBlocksRequest request) {
        blockService.storeFromBlockchain(request.start(), request.end());
//        List<Block> blocks = blockService.findBlocksInRange(request.start, request.end);
        return Response.ok().build();
    }
}
