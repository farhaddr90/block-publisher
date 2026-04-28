package com.simorghsoftech.api.responses;

import com.fasterxml.jackson.databind.JsonNode;

public record EthBlock(
        String jsonrpc,
        int id,
        JsonNode result
) {
}