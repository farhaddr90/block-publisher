package com.simorghsoftech.core.models;


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

}
