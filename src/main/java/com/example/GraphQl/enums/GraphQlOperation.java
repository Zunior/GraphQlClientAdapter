package com.example.GraphQl.enums;

public enum GraphQlOperation {
    QUERY("query"), MUTATION("mutation"), SUBSCRIPTION("subscription");

    public final String label;

    public String getValue() {
        return label;
    }

    GraphQlOperation(String label) {
        this.label = label;
    }
}
