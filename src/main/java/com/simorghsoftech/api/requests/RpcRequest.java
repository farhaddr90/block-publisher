package com.simorghsoftech.api.requests;

import java.util.List;

public record RpcRequest(int id,
                         String jsonrpc,
                         String method,
                         List<Object> params) {
}