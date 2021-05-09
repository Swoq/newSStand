package com.swoqe.newSStand.model.entity;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class PeriodicalPublication {
    private Long id;
    private String name;
    private LocalDate publicationDate;
    private File coverImg;
    private String publisher;
    private String imageName;
    private BigDecimal shownLowestPrice;
    private String periodName;
    private String description;

    public PeriodicalPublication(String name, LocalDate publicationDate, File coverImg, String publisher) {
        this.name = name;
        this.publicationDate = publicationDate;
        this.coverImg = coverImg;
        this.publisher = publisher;
    }

    public PeriodicalPublication() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getShownLowestPrice() {
        return shownLowestPrice;
    }

    public void setShownLowestPrice(BigDecimal shownLowestPrice) {
        this.shownLowestPrice = shownLowestPrice;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public File getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(File coverImg) {
        this.coverImg = coverImg;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PeriodicalPublication)) return false;

        PeriodicalPublication that = (PeriodicalPublication) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!publicationDate.equals(that.publicationDate)) return false;
        if (!coverImg.equals(that.coverImg)) return false;
        if (!publisher.equals(that.publisher)) return false;
        return imageName != null ? imageName.equals(that.imageName) : that.imageName == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + publicationDate.hashCode();
        result = 31 * result + coverImg.hashCode();
        result = 31 * result + publisher.hashCode();
        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PeriodicalPublication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", publicationDate=" + publicationDate +
                ", coverImg=" + coverImg +
                ", publisher='" + publisher + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
