package com.dpm2020.appgas.network;

public interface Routes {

    String URL_BASE = "https://apigas.komanda.pe";

    String URL_AUTH_LOGIN = URL_BASE + "/auth/token/auth";
    String URL_AUTH_VERIFY = URL_BASE + "/auth/token/auth/verify";

    String URL_PRODUCTS_LIST = URL_BASE + "/orders/product";
    String URL_ADDRESS_LIST = URL_BASE + "/users/address";

    String URL_REGISTER_USER = URL_BASE + "/users/register";

}
