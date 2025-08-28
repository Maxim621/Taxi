package com.solvd.taxi.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "promocodes")
@XmlAccessorType(XmlAccessType.FIELD)
public class Promocodes {

    @XmlElement(name = "promocode")
    private List<PromoCode> promoCodes;

    public Promocodes() {}

    public Promocodes(List<PromoCode> promoCodes) {
        this.promoCodes = promoCodes;
    }

    public List<PromoCode> getPromoCodes() {
        return promoCodes;
    }

    public void setPromoCodes(List<PromoCode> promoCodes) {
        this.promoCodes = promoCodes;
    }
}