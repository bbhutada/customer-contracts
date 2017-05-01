/*
 * This is the RestController class which exposes 5 REST endpoints as required. Most of the methods are self explanatory and 
 * uses mock database to perform CRUS operation. In real world the mock database can be replaced with any database/JPA.
 * 
 * All the REST endpoints check whether the body/inputs are correct else an appropriate error message is sent back.
 * */
package com.cutomer.contracts;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cutomer.contracts.models.Contract;
import com.cutomer.contracts.models.Customer;
import com.cutomer.contracts.repository.DbRepository;

@RestController
public class CustomerServiceController {
	@Autowired
	private DbRepository dbRepository;

	@RequestMapping(value = "/customerservice/customer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Customer createCustomer(@RequestBody Customer customer) {
		if (customer.getEmail() != null && customer.getFullName() != null) {
			customer.setId(new Date().getTime());
		} else {
			throw new IllegalArgumentException("Body must have 'email' and 'fullName' keys");
		}

		dbRepository.saveCustomer(customer);
		return customer;
	}

	@RequestMapping(value = "/customerservice/contract", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Contract createContract(@RequestBody Contract contract) {
		if (contract.getCustomerId() != 0 && contract.getMonthlyRevenue() != 0 && contract.getStartDate() != null
				&& contract.getType() != null) {

			contract.setId(new Date().getTime());
		} else {
			throw new IllegalArgumentException(
					"Body must have 'customerId', 'monthlyRevenue', 'startDate' and 'type' keys");
		}

		if (dbRepository.getCustomerById(contract.getCustomerId()) != null) {
			dbRepository.saveContract(contract);
		} else {
			throw new IllegalArgumentException("Customer with Id: " + contract.getCustomerId() + " does not exist");
		}

		return contract;
	}

	@RequestMapping(value = "/customerservice/customer/{customerId}", method = RequestMethod.GET)
	public @ResponseBody Customer getCustomerById(@PathVariable("customerId") long customerId) {
		Customer customer = dbRepository.getCustomerByIdWithContracts(customerId);

		if (customer == null) {
			throw new IllegalArgumentException("Customer with Id: " + customerId + " not found");
		}
		return customer;
	}

	@RequestMapping(value = "/customerservice/contractrevenue/customer/{customerId}", method = RequestMethod.GET)
	public @ResponseBody double totalRevenueByCustomerId(@PathVariable("customerId") long customerId) {
		List<Contract> contracts = dbRepository.getContractsByCustomerId(customerId);
		double sumOfRevenues = 0;

		for (Contract contract : contracts) {
			sumOfRevenues = sumOfRevenues + contract.getMonthlyRevenue();
		}
		return sumOfRevenues;
	}

	@RequestMapping(value = "/customerservice/contractrevenue/{type}", method = RequestMethod.GET)
	public @ResponseBody double totalRevenueByContractType(@PathVariable("type") String contractType) {
		double sumOfRevenues = dbRepository.totalRevenueByContractType(contractType);
		return sumOfRevenues;
	}

	/*
	 * This method is used for exception handling ex. bad request
	 * */
	@ExceptionHandler({ IllegalArgumentException.class, NullPointerException.class })
	void handleBadRequests(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}
}
