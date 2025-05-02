package com.example.alimentacao.utils;

public class Result<T> {
    public static class Success<T> extends Result<T> {
        public T data;

        public Success(T data) {
            this.data = data;
        }
    }

    public static class Error<T> extends Result<T> {
        public String message;

        public Error(String message) {
            this.message = message;
        }
    }

    public static class Loading<T> extends Result<T> {}
}
