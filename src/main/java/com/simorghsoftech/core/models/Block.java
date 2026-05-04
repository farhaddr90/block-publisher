package com.simorghsoftech.core.models;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Block {
    private final long number;
    private final String hash;
    private final String data;

    public Block(long number, String hash, String data) {
        this.number = number;
        this.hash = hash;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getHash() {
        return hash;
    }

    public long getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return data;
    }

    public JsonNode getDataAsJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(getData());
    }
}
