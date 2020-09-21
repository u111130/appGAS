package com.dpm2020.appgas.data;

import java.util.ArrayList;
import java.util.List;

public class OrderDB {
    private String address_id;
    private String payment_method;
    private String card_id;
    private List<Details> details = new ArrayList<OrderDB.Details>();

    public static class Details {
        private String product_id;
        private int quantity;

        public Details(String product_id, int quantity) {
            this.product_id = product_id;
            this.quantity = quantity;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

    }

    public void AdicionaDet (Details det) {
        details.add(det);
    }

    public OrderDB() {
        this.address_id = "";
        this.payment_method = "";
        this.card_id = "";
        this.details.clear();
    }

    public OrderDB(String address_id, String payment_method, String card_id) {
        this.address_id = address_id;
        this.payment_method = payment_method;
        this.card_id = card_id;
    }

    public OrderDB(List<Details> details) {
        this.details = details;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }
}

