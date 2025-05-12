package com.example.alimentacao.utils;

public class Result<T> {
    private final T data;
    private final String error;
    private final boolean loading;

    public Result(T data, String error, boolean loading) {
        this.data = data;
        this.error = error;
        this.loading = loading;
    }

    public static <T> Result<T> loading() {
        return new Result<>(null, null, true);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, null, false);
    }

    public static <T> Result<T> error(String error) {
        return new Result<>(null, error, false);
    }

    // Getters
    public T getData() { return data; }
    public String getError() { return error; }
    public boolean isLoading() { return loading; }
}