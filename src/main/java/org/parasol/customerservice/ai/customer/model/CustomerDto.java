package org.parasol.customerservice.ai.customer.model;

public class CustomerDto {

    private String id;

    private String company;

    private String contactName;

    private String contactTitle;

    private String email;

    private String address;

    private String city;

    private String region;

    private String postalCode;

    private String country;

    private String phone;

    public String getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final CustomerDto customerDto;

        public Builder() {
            this.customerDto = new CustomerDto();
        }

        public Builder withId(String id) {
            this.customerDto.id = id;
            return this;
        }

        public Builder withCompany(String company) {
            this.customerDto.company = company;
            return this;
        }

        public Builder withContactName(String contactName) {
            this.customerDto.contactName = contactName;
            return this;
        }

        public Builder withContactTitle(String contactTitle) {
            this.customerDto.contactTitle = contactTitle;
            return this;
        }

        public Builder withEmail(String email) {
            this.customerDto.email = email;
            return this;
        }

        public Builder withAddress(String address) {
            this.customerDto.address = address;
            return this;
        }

        public Builder withCity(String city) {
            this.customerDto.city = city;
            return this;
        }

        public Builder withRegion(String region) {
            this.customerDto.region = region;
            return this;
        }

        public Builder withPostalCode(String postalCode) {
            this.customerDto.postalCode = postalCode;
            return this;
        }

        public Builder withCountry(String country) {
            this.customerDto.country = country;
            return this;
        }

        public Builder withPhone(String phone) {
            this.customerDto.phone = phone;
            return this;
        }

        public CustomerDto build() {
            return this.customerDto;
        }
    }

}
