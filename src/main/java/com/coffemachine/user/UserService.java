package com.coffemachine.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	
	public List<User> getAllUsers(){
		return (List<User>) userRepository.findAll();
	}
	
	public User getUser(Long id){
		return userRepository.findOne(id);
	}
	
	public void addUser(User user){
		userRepository.save(user);
	}
	
	public void deleteUser(Long id){
		userRepository.delete(id);
	}

	
	public void updateUser(User user){
		userRepository.save(user);
	}
}