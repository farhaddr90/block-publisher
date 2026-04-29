package com.simorghsoftech.core.models;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Objects;

public class Block {
    private final int number;
    private final String hash;
    private final String data;

    public Block(int number, String hash, String data) {
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

    public int getNumber() {
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
