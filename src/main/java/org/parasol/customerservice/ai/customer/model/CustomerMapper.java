package org.parasol.customerservice.ai.customer.model;

public class CustomerMapper {

    public static CustomerDto toDto(Customer customer) {
        if (customer == null) {
            return null;
        }
        return CustomerDto.builder()
                .withId(customer.id)
                .withCompany(customer.company)
                .withContactName(customer.contactName)
                .withContactTitle(customer.contactTitle)
                .withEmail(customer.email)
                .withAddress(customer.address)
                .withCity(customer.city)
                .withRegion(customer.region)
                .withPostalCode(customer.postalCode)
                .withCountry(customer.country)
                .withPhone(customer.phone)
                .build();
    }
}
