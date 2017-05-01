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
