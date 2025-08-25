package com.solvd.taxi.model;

import java.time.LocalDate;

public class PromoCode {
    private int promoId;
    private String code;
    private double discount;
    private LocalDate expiryDate;
    private boolean isActive;

    public PromoCode() {}

    public PromoCode(String code, double discount, LocalDate expiryDate) {
        this.code = code;
        this.discount = discount;
        this.expiryDate = expiryDate;
        this.isActive = true;
    }

    public PromoCode(int promoId, String code, double discount, LocalDate expiryDate, boolean isActive) {
        this.promoId = promoId;
        this.code = code;
        this.discount = discount;
        this.expiryDate = expiryDate;
        this.isActive = isActive;
    }

    public int getPromoId() {
        return promoId;
    }

    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Business logic
    public boolean isValid() {
        return isActive && expiryDate.isAfter(LocalDate.now());
    }

    public double applyDiscount(double amount) {
        if (isValid()) {
            return amount - (amount * discount / 100);
        }
        return amount;
    }

    @Override
    public String toString() {
        return "PromoCode{" +
                "promoId=" + promoId +
                ", code='" + code + '\'' +
                ", discount=" + discount +
                ", expiryDate=" + expiryDate +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromoCode promoCode = (PromoCode) o;
        return promoId == promoCode.promoId &&
                code.equals(promoCode.code);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(promoId, code);
    }
}