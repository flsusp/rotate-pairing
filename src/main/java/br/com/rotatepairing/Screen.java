package br.com.rotatepairing;

public interface Screen {

    void show(String message);

    default void show(String message, Object... params) {
        show(String.format(message, params));
    }
}
