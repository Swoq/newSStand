package com.swoqe.newSStand.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Subscription {
    private final Long id;
    private final User user;
    private final String publicationName;
    private final Period period;
    private final BigDecimal price;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Subscription(Long id, User user, String publicationName, Period period,
                        BigDecimal price, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.user = user;
        this.publicationName = publicationName;
        this.period = period;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getPublicationName() {
        return publicationName;
    }

    public Period getPeriod() {
        return period;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
