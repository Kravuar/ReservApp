package net.kravuar.business.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Business {
    private final Long id;
    private String name;
    private String email;

    Business(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static BusinessBuilder builder() {
        return new BusinessBuilder();
    }

    public static class BusinessBuilder {
        private Long id;
        private String name;
        private String email;

        BusinessBuilder() {
        }

        public BusinessBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public BusinessBuilder name(String name) {
            this.name = name;
            return this;
        }

        public BusinessBuilder email(String email) {
            this.email = email;
            return this;
        }

        public Business build() {
            return new Business(this.id, this.name, this.email);
        }

        public String toString() {
            return "Business.BusinessBuilder(id=" + this.id + ", name=" + this.name + ", email=" + this.email + ")";
        }
    }
}
