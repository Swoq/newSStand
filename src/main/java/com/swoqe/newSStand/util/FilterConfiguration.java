package com.swoqe.newSStand.util;

import java.time.LocalDate;

public class FilterConfiguration {
    private final int recordsPerPage;
    private final int pageNumber;
    private final Integer[] genresIds;
    private final OrderBy orderBy;
    private final SortingDirection sortingDirection;
    private final LocalDate dateToSort;

    private FilterConfiguration(FilterConfigurationBuilder builder){
        this.recordsPerPage = builder.recordsPerPage;
        this.pageNumber = builder.pageNumber;
        this.genresIds = builder.genresIds;
        this.orderBy = builder.orderBy;
        this.sortingDirection = builder.sortingDirection;
        this.dateToSort = builder.dateToSort;
    }

    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public Integer[] getGenresIds() {
        return genresIds;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public SortingDirection getSortingDirection() {
        return sortingDirection;
    }

    public LocalDate getDateToSort() {
        return dateToSort;
    }

    public static class FilterConfigurationBuilder{
        private int recordsPerPage;
        private int pageNumber;
        private Integer[] genresIds;
        private OrderBy orderBy;
        private SortingDirection sortingDirection;
        private LocalDate dateToSort;

        public FilterConfigurationBuilder withRecordsPerPage(int recordsPerPage){
            this.recordsPerPage = recordsPerPage;
            return this;
        }

        public FilterConfigurationBuilder withPageNumber(int pageNumber){
            this.pageNumber = pageNumber;
            return this;
        }

        public FilterConfigurationBuilder withGenresIds(Integer[] genresIds){
            this.genresIds = genresIds;
            return this;
        }

        public FilterConfigurationBuilder withOrderBy(OrderBy orderBy){
            this.orderBy = orderBy;
            return this;
        }

        public FilterConfigurationBuilder withSortingDirection(SortingDirection sortingDirection){
            this.sortingDirection = sortingDirection;
            return this;
        }

        public FilterConfigurationBuilder withDateToSort(LocalDate dateToSort){
            this.dateToSort = dateToSort;
            return this;
        }

        public FilterConfiguration build(){
            return new FilterConfiguration(this);
        }

    }
}
