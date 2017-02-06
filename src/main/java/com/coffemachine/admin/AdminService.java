package com.coffemachine.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
	
	@Autowired 
	AdminRepository adminRepository;
	
	public List<Admin> getAllAdmins(){
		return (List<Admin>) adminRepository.findAll();
	}
	
	public Admin getAdmin(Long id){
		return adminRepository.findOne(id);
	}
	
	public void addAdmin(Admin admin){
		adminRepository.save(admin);
	}
	
	public void deleteAdmin(Long id){
		adminRepository.delete(id);
	}
	
	public void updateAdmin(Admin admin){
		adminRepository.save(admin);
	}

}