package com.swoqe.newSStand.model.entity;

import java.math.BigDecimal;

public class Rate {
    private Long id;
    private Period period;
    private Long publicationId;
    private BigDecimal price;

    public Rate(Long id, Period period, Long publicationId, BigDecimal price) {
        this.id = id;
        this.period = period;
        this.publicationId = publicationId;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
