package com.jcg.examples.test;

public enum DUMP_TYPE {
    FILE(0), CONSOLE(1);
    private int value;

    private DUMP_TYPE(int value) {
        this.value = value;
    }

}
