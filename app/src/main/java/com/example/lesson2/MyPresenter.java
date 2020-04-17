package com.example.lesson2;

public final class MyPresenter {
    private static MyPresenter instance = null;
    private static final Object syncObj = new Object();
    private int counter;

    private MyPresenter() {
        counter = 0;
    }

    public void incrementCounter() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }

    public static MyPresenter getInstance() {
        synchronized (syncObj) {
            if (instance == null) {
                instance = new MyPresenter();
            }
            return instance;
        }
    }
}
