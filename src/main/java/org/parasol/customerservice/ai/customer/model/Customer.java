package org.parasol.customerservice.ai.customer.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Optional;

@Entity(name = "Customer")
@Table(name = "customers")
public class Customer extends PanacheEntityBase {

    @Id
    @Column(name = "customer_id")
    public String id;

    @Column(name = "company_name")
    public String company;

    @Column(name = "contact_name")
    public String contactName;

    @Column(name = "contact_title")
    public String contactTitle;

    @Column(name = "contact_email")
    public String email;

    @Column(name = "address")
    public String address;

    @Column(name = "city")
    public String city;

    @Column(name = "region")
    public String region;

    @Column(name = "postal_code")
    public String postalCode;

    @Column(name = "country")
    public String country;

    @Column(name = "phone")
    public String phone;

    public static Optional<Customer> findByEmail(String email) {
        return find("email", email).singleResultOptional();
    }

}