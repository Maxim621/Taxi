package com.solvd.taxi.model;

import com.solvd.taxi.util.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "promocode")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"promoId", "code", "discount", "expiryDate", "isActive"})
public class PromoCode {

    @XmlElement(name = "promoId", required = true)
    private int promoId;

    @XmlElement(required = true)
    private String code;

    @XmlElement(required = true)
    private double discount;

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate expiryDate;

    @XmlElement(defaultValue = "true")
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