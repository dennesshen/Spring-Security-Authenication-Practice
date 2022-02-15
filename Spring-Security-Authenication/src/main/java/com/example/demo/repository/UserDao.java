package com.example.demo.repository;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
	
	public Map<String, Map<String, String>> users;
	{
		//A01 1234 admin,normal,Role_manager
		Map<String, String> info1 = new LinkedHashMap<>();
		info1.put("password", "$2a$10$ZNGOQ36tHvqT0q5g2hOLQON12SIkIa61thWw/37ueY9Jo/87lP4ym");
		info1.put("authority","admin,normal,ROLE_manager");
		
		//A01 5678 normal,Role_manager
		Map<String, String> info2 = new LinkedHashMap<>();
		info2.put("password", "$2a$10$awzDois2JAW6Vuy/TpzXC.QMGwxsuABQPMFBrA8Q05ooAg6YhTz02");
		info2.put("authority","normal,ROLE_employee");
		
		users = new LinkedHashMap<>();
		users.put("A01", info1);
		users.put("A02", info2);
		
		System.out.println(users);
	}

}
