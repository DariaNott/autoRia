package com.ria.auto.entity;

/**
 * The class {@code CarItem} represents entities of search results.
 *
 * @author Daria Ivanova
 */
public class CarItem {

    private String name;
    private int priceUSD;
    private int priceUAH;

    public CarItem(String name, int priceUSD, int priceUAH) {
        this.name = name;
        this.priceUSD = priceUSD;
        this.priceUAH = priceUAH;
    }

    public String getName() {
        return name;
    }

    public int getPriceUSD() {
        return priceUSD;
    }

    public int getPriceUAH() {
        return priceUAH;
    }

    @Override
    public String toString() {
        return "CarItem{" +
                "name='" + name + '\'' +
                ", priceUSD=" + priceUSD +
                ", priceUAH=" + priceUAH +
                '}';
    }
}
