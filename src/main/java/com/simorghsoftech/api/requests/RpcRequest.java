package com.simorghsoftech.api.requests;

import java.util.List;

public record RpcRequest(long id,
                         String jsonrpc,
                         String method,
                         List<Object> params) {
}