package com.dpm2020.appgas.data;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String address_id;
    private String payment_method;
    private String card_id;
    private List<Details> details = new ArrayList<Order.Details>();

    public static class Products {
        private String product_id;
        private int quantity;

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

    public static class Details {
        private String product_id;
        private String name;
        private int quantity;
        private double price;
        private double linetotal;

        public Details(String product_id, String name, int quantity, double price, double linetotal) {
            this.product_id = product_id;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
            this.linetotal = linetotal;
        }

        public Details(int quantity, double linetotal) {
            this.quantity = quantity;
            this.linetotal = linetotal;
        }

        public Details() {
            this.product_id = "";
            this.name = "";
            this.quantity = 0;
            this.price = 0;
            this.linetotal = 0;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getLinetotal() {
            return linetotal;
        }

        public void setLinetotal(double linetotal) {
            this.linetotal = linetotal;
        }
    }

    public double calcularTotal() {
        double total = 0;
        //details.forEach((det)-> total+= det.linetotal);
        for (Details det: details) {
            total += det.linetotal;
        }
        return total;
    }

    public void AdicionaDet (Details det) {
        details.add(det);
    }

    public Order() {
        this.address_id = "";
        this.payment_method = "";
        this.card_id = "";
        this.details.clear();
    }

    public Order(String address_id, String payment_method, String card_id) {
        this.address_id = address_id;
        this.payment_method = payment_method;
        this.card_id = card_id;
    }

    public Order(List<Details> details) {
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

