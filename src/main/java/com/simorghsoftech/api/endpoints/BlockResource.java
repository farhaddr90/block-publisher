package com.simorghsoftech.api.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.simorghsoftech.core.models.Block;
import com.simorghsoftech.core.services.BlockService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/v1/block")
public class BlockResource {

    private final BlockService blockService;

    BlockResource(final BlockService blockService) {
        this.blockService = blockService;
    }

    public record RangeOfBlocksRequest(long start, long end) {
    }

    @POST
    @Path("/get")
    public Response getBlocks(RangeOfBlocksRequest request) {
        return getBlocks(request.start, request.end);
    }

    @GET
    @Path("/get")
    public Response getBlocks(@QueryParam("start") long start) {
        long end = blockService.latestReceivedBlock();
        return getBlocks(start, end);
    }

    private Response getBlocks(long start, long end) {
        List<Block> blocks = blockService.findBlocksInRange(start, end);
        ArrayList<JsonNode> response = new ArrayList<>();
        try {
            for (Block block : blocks) {
                response.add(block.getDataAsJson());
            }
            return Response.ok(response.toString()).build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }

    @POST
    @Path("/cache")
    public Response storeFromChain(RangeOfBlocksRequest request) {
        blockService.storeFromBlockchain(request.start(), request.end());
        return Response.ok().build();
    }
}
