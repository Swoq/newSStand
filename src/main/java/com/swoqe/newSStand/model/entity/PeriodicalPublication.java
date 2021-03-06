package com.swoqe.newSStand.model.entity;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PeriodicalPublication {
    private Long id;
    private final String name;
    private final LocalDate publicationDate;
    private final File coverImg;
    private final String publisher;
    private final String description;
    private final Map<String, BigDecimal> pricesPerPeriods;
    private final List<Genre> genres;
    private final BigDecimal shownPrice;
    private final String shownPeriod;

    public PeriodicalPublication(PublicationBuilder publicationBuilder) {
        this.id = publicationBuilder.id;
        this.name = publicationBuilder.name;
        this.publicationDate = publicationBuilder.publicationDate;
        this.coverImg = publicationBuilder.coverImg;
        this.publisher = publicationBuilder.publisher;
        this.pricesPerPeriods = publicationBuilder.pricesPerPeriods;
        this.genres = publicationBuilder.genres;
        this.shownPrice = publicationBuilder.shownPrice;
        this.shownPeriod = publicationBuilder.shownPeriod;
        this.description = publicationBuilder.description;
    }

    public BigDecimal getShownPrice() {
        return shownPrice;
    }

    public String getShownPeriod() {
        return shownPeriod;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public File getCoverImg() {
        return coverImg;
    }

    public String getPublisher() {
        return publisher;
    }

    public Map<String, BigDecimal> getPricesPerPeriods() {
        return pricesPerPeriods;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PeriodicalPublication)) return false;

        PeriodicalPublication that = (PeriodicalPublication) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!publicationDate.equals(that.publicationDate)) return false;
        if (!Objects.equals(coverImg, that.coverImg)) return false;
        if (!publisher.equals(that.publisher)) return false;
        if (!pricesPerPeriods.equals(that.pricesPerPeriods)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + publicationDate.hashCode();
        result = 31 * result + coverImg.hashCode();
        result = 31 * result + publisher.hashCode();
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
                ", pricesPerPeriods=" + pricesPerPeriods +
                ", description='" + description + '\'' +
                '}';
    }

    public static class PublicationBuilder {
        private Long id;
        private String name;
        private LocalDate publicationDate;
        private File coverImg;
        private String publisher;
        private Map<String, BigDecimal> pricesPerPeriods;
        private List<Genre> genres = new ArrayList<>();
        private String description;
        private BigDecimal shownPrice;
        private String shownPeriod;

        public PublicationBuilder withId(Long id){
            this.id = id;
            return this;
        }

        public PublicationBuilder withName(String name){
            this.name = name;
            return this;
        }

        public PublicationBuilder withPublicationDate(LocalDate date){
            this.publicationDate = date;
            return this;
        }

        public PublicationBuilder withCoverImg(File image){
            this.coverImg = image;
            return this;
        }

        public PublicationBuilder withPublisher(String publisher){
            this.publisher = publisher;
            return this;
        }

        public PublicationBuilder withPricesMap(Map<String, BigDecimal> map){
            this.pricesPerPeriods = map;
            return this;
        }

        public PublicationBuilder withDescription(String description){
            this.description = description;
            return this;
        }

        public PublicationBuilder withGenres(List<Genre> genres){
            this.genres = genres;
            return this;
        }

        public PublicationBuilder withShownPrice(BigDecimal shownPrice){
            this.shownPrice = shownPrice;
            return this;
        }

        public PublicationBuilder withShownPeriod(String shownPeriod){
            this.shownPeriod = shownPeriod;
            return this;
        }

        public PublicationBuilder basedOn(PublicationBuilder builder){
            this.id = builder.id;
            this.name = builder.name;
            this.publicationDate = builder.publicationDate;
            this.coverImg = builder.coverImg;
            this.publisher = builder.publisher;
            this.pricesPerPeriods = builder.pricesPerPeriods;
            this.genres = builder.genres;
            this.description = builder.description;
            return this;
        }

        public PeriodicalPublication build(){
            return new PeriodicalPublication(this);
        }

    }
}
