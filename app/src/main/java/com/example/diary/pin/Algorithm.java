package com.example.diary.pin;

public enum Algorithm {

    SHA1("1"), SHA256("2");
    String value = "";

    Algorithm(String s) {

        this.value = s;
    }

    public String getValue() {
        return value;
    }

    public static Algorithm getFromText(String pass) {

        for(Algorithm algorithm : Algorithm.values()) {
            if(algorithm.value.equals(pass))
                return algorithm;
        }
        // default
        return SHA1;
    }
}
