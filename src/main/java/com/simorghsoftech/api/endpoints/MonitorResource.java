package com.simorghsoftech.api.endpoints;

import com.simorghsoftech.core.services.BlockService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/monitor")
public class MonitorResource {


    private final BlockService blockService;

    public MonitorResource (BlockService blockService) {
        this.blockService = blockService;
    }

    @GET
    @Path("/latest")
    @Produces(MediaType.APPLICATION_JSON)
    public long latest() {
        return blockService.latestScannedBlockNumber();
    }
}