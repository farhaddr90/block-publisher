package com.simorghsoftech.core.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simorghsoftech.api.clients.EthClient;
import com.simorghsoftech.api.requests.RpcRequest;
import com.simorghsoftech.core.models.Block;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ApiBlockRepository {

    private final EthClient client;
    private final ObjectMapper mapper;

    public ApiBlockRepository(@RestClient EthClient client) {
        this.client = client;
        this.mapper = new ObjectMapper();
    }

    public Block getLatestBlock() {
        RpcRequest request = new RpcRequest(
                1, "2.0", "eth_getBlockByNumber", List.of("finalized", true)
        );
        JsonNode root = executeSingle(request);
        return toBlock(root);
    }

    public Block getBlockByHash(String hash) {
        RpcRequest request = new RpcRequest(
                1, "2.0", "eth_getBlockByHash", List.of(hash, true)
        );

        JsonNode root = executeSingle(request);
        return toBlock(root);
    }

    public Block getBlockByNum(int number) {
        String hex = Numeric.toHexStringWithPrefix(BigInteger.valueOf(number));

        RpcRequest request = new RpcRequest(
                1, "2.0", "eth_getBlockByNumber", List.of(hex, true)
        );

        JsonNode root = executeSingle(request);
        return toBlock(root);
    }

    public List<Block> getBlocks(int start, int end) {

        List<RpcRequest> requests = new ArrayList<>(end - start + 1);

        for (int i = start, id = 1; i <= end; i++, id++) {
            String hex = Numeric.toHexStringWithPrefix(BigInteger.valueOf(i));
            requests.add(new RpcRequest(id, "2.0", "eth_getBlockByNumber", List.of(hex, true)));
        }

        JsonNode root = executeBatch(requests);

        List<Block> blocks = new ArrayList<>(root.size());

        for (JsonNode node : root) {
            Block block = toBlock(node);
            if (block != null) {
                blocks.add(block);
            }
        }

        return blocks;
    }

    private JsonNode executeSingle(RpcRequest request) {
        try (Response response = client.ethRpc(request)) {

            validateHttp(response);

            JsonNode root = mapper.readTree(response.readEntity(String.class));
            validateRpc(root);

            return root;

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse RPC response", e);
        }
    }

    private JsonNode executeBatch(List<RpcRequest> requests) {
        try (Response response = client.ethRpcBatch(requests)) {

            validateHttp(response);

            return mapper.readTree(response.readEntity(String.class));

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse batch RPC response", e);
        }
    }

    private void validateHttp(Response response) {
        if (response.getStatus() != 200) {
            throw new RuntimeException("HTTP error: " + response.getStatus());
        }
    }

    private void validateRpc(JsonNode root) {
        if (root.has("error")) {
            throw new RuntimeException("RPC error: " + root.get("error"));
        }
    }

    private Block toBlock(JsonNode node) {
        JsonNode result = node.path("result");

        if (result.isMissingNode() || result.isNull()) {
            return null;
        }

        String hash = result.path("hash").asText();
        BigInteger number = Numeric.toBigInt(result.path("number").asText());

        return new Block(
                number.intValue(),
                hash,
                node.toString()
        );
    }
}