package org.parasol.customerservice.ai.customer.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.parasol.customerservice.ai.customer.model.Customer;
import org.parasol.customerservice.ai.customer.model.CustomerDto;
import org.parasol.customerservice.ai.customer.model.CustomerMapper;

@ApplicationScoped
public class CustomerService {

    @Transactional
    public CustomerDto getCustomerByEmail(String email) {
        return CustomerMapper.toDto(Customer.findByEmail(email).orElse(null));
    }

}
