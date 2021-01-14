package com.example.tinnews.ui.home;

import java.util.Objects;

public class HomeInput {
    public final String country;
    public final Integer page;
    public final Integer pageSize;

    public HomeInput(String country, Integer page, Integer pageSize) {
        this.country = country;
        this.page = page;
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeInput homeInput = (HomeInput) o;
        return Objects.equals(country, homeInput.country) &&
                Objects.equals(page, homeInput.page) &&
                Objects.equals(pageSize, homeInput.pageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, page, pageSize);
    }
}
