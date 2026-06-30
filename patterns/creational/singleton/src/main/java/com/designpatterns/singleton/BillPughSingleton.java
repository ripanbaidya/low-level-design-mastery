package com.designpatterns.singleton;

public class BillPughSingleton {

    private BillPughSingleton() {
    }

    // The inner static class is not loaded into memory until getInstance() is invoked
    private static class Helper {
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        return Helper.INSTANCE;
    }
}
