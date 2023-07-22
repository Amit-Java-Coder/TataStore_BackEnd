package com.watch.store;

import java.util.UUID;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.watch.store.entity.Role;
import com.watch.store.repository.RoleRepository;

@SpringBootApplication
public class WatchStoreApplication  implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(WatchStoreApplication.class, args);
	}

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepository repository;
	
	@Value("${admin.role.id}")
	private String admin_role_id;
	
	@Value("${normal.role.id}")
	private String normal_role_id;
	
	@Override
	public void run(String... args) throws Exception {
		
	        try {
	        	 Role role_admin = Role.builder().roleId(admin_role_id).roleName("ROLE_ADMIN").build();
			     Role role_normal =	Role.builder().roleId(normal_role_id).roleName("ROLE_NORMAL").build();
			     repository.save(role_admin);
			     repository.save(role_normal);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
