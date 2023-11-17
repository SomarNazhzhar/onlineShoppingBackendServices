package com.project.service;

import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.project.exception.AddressNotFound;
import com.project.model.Address;
import com.project.repository.AddressDao;


@Service
@Log4j2
public class AddressServiceImpl implements AddressService{
	
	@Autowired
	private AddressDao aDao;
	
	@Override
	public Address addAddress(Address add) {
		return aDao.save(add);
	}

	@Override
	public Address updateAddress(Address add) {
		
		Address a = aDao.findByaddressId(add.getAddressId());
		
		if(a!=null) {
			return aDao.save(add);
			
		}
		else {
			throw new AddressNotFound("No address exists");
		}
		
	}

	@Override
	public Address removeAddress(Integer id) {
		
		Address a = aDao.findByaddressId(id);
		
		if(a!=null) {
			aDao.delete(a);
			return a;
		}
		else {
			throw new AddressNotFound("No address exists");
		}
	}

	@Override
	public Address viewAddress(Integer addressId) {
		Address a = aDao.findByaddressId(addressId);
		
		if(a!=null) {
			return a;
		}
		else
		{
			throw new AddressNotFound("No address exists");
		}
	}

	@Override
	//@Scheduled(cron = "*1* * * * *")
	public List<Address> viewAllAddress(){
		List<Address> alist=aDao.findAll();
		if(alist!=null) {
	//		log.info("new addresses updated");
			return alist;

		}
		else
		{
			throw new AddressNotFound("No address exists");
		}
		
	}
	
	

}
