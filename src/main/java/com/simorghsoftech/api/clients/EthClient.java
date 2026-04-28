package com.simorghsoftech.api.clients;

import com.simorghsoftech.api.requests.RpcRequest;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "blockchain-node")
public interface EthClient {

    @POST
    Response ethRpc(RpcRequest request);

    @POST
    Response ethRpcBatch(List<RpcRequest> request);
}
