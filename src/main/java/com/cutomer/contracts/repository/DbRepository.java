/*
 * This is the in memory database POJO. It stores contracts and customers in a MAP. 
 * The reason to use MAP is for faster iteration by keys which results in O(1) complexity just to find record by ID
 * This object is a singleton object and has API's exposed for CRUD operation a expose few key API's used by controller
 * */
package com.cutomer.contracts.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.cutomer.contracts.models.Contract;
import com.cutomer.contracts.models.Customer;

@Repository
public class DbRepository {
	private Map<Long, Contract> contractsRepo = new HashMap<>();
	private Map<Long, Customer> customersRepo = new HashMap<>();

	/*
	 * Returns List of Contract by customer Id
	 * */
	public List<Contract> getContractsByCustomerId(Long customerId) {
		List<Contract> customerContracts = new ArrayList<Contract>();
		
		contractsRepo.forEach((contractId,contract)->{
			if(contract.getCustomerId() == customerId) {
				customerContracts.add(contract);
			}
		});
		
		return customerContracts;
	}

	
	/**
	 * @return the contractsRepo
	 */
	public Map<Long, Contract> getContractsRepo() {
		return contractsRepo;
	}
	
	public Customer getCustomerById(Long customerId) {
		return customersRepo.get(customerId);
	}

	/*
	 * Returns a Customer with all of its contracts
	 * */
	public Customer getCustomerByIdWithContracts(Long customerId) {
		Customer customer = this.getCustomerById(customerId);
		if(customer == null) {
			return null;
		}
		
		List<Contract> customerContracts = this.getContractsByCustomerId(customerId);
		customer.setContracts(customerContracts);
		return customer; 
	}
	
	/**
	 * @return the customersRepo
	 */
	public Map<Long, Customer> getCustomersRepo() {
		return customersRepo;
	}
	
	public void saveContract(Contract contract) {
		contractsRepo.put(contract.getId(), contract);
	}
	
	public void saveCustomer(Customer customer) {
		customersRepo.put(customer.getId(), customer);
	}
	
	public double totalRevenueByContractType(String contractType) {
		double totalRevenue = 0;
		
		for(Contract contract : contractsRepo.values()) {
			if(contract.getType().equals(contractType))
				totalRevenue = totalRevenue + contract.getMonthlyRevenue();
		}
				
		return totalRevenue;
	}
}
