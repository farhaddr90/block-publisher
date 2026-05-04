package com.simorghsoftech.api.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public record EthBlock(
        String jsonrpc,
        int id,
        Result result
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Result(
            String number,
            String hash,
            List<Transaction> transactions,
            String totalDifficulty,
            String logsBloom,
            String receiptsRoot,
            String extraData,
            String baseFeePerGas,
            String nonce,
            String miner,
            String difficulty,
            String gasLimit,
            String gasUsed,
            List<String> uncles,
            String sha3Uncles,
            String size,
            String transactionsRoot,
            String stateRoot,
            String mixHash,
            String parentHash,
            String timestamp
    ) {

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Transaction(
                String blockHash,
                String blockNumber,
                String hash,
                List<AccessList> accessList,
                String transactionIndex,
                String type,
                String nonce,
                String input,
                String r,
                String s,
                String chainId,
                String v,
                String gas,
                String maxPriorityFeePerGas,
                String from,
                String to,
                String maxFeePerGas,
                String value,
                String gasPrice
        ) {
        }

        public record AccessList(
                String address,
                List<String> storageKeys
        ) {
        }
    }
}
