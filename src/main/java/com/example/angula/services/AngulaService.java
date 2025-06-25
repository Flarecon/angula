package com.example.angula.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.angula.database.model.AngulaUser;
import com.example.angula.database.repository.AngulaUserRepo;

@Service
public class AngulaService {
    PasswordEncoder encoder;
    AngulaUserRepo userRepo;

    public AngulaService(AngulaUserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Transactional(propagation = Propagation.REQUIRED // default, continues current transaction else starts new
            // Propagation.REQUIRES_NEW. // starts new transaction
            // Propagation.MANDATORY, // throws exception if not in a transaction
            // Propagation.NEVER, // throws exception if in a transaction
            // Propagation.SUPPORTS, // continues current transaction else nothing
            // Propagation.NESTED, // saves previous transaction and starts new if new
            // transaction fails
            // rollback doesn't affect saved transaction its best for transaction safety
            // readOnly = true,
            // isolation = Isolation.DEFAULT  // default
            // Isolation.READ_UNCOMMITTED  // no locks best for only reads (very fast)
            // Isolation.READ_COMMITTED  // prevents dirty reads (default for reads and writes ) (fast) 
            // Isolation.REPEATABLE_READ  // prevents repetable reads + dirty reads (slow)
            // Isolation.SERIALIZABLE  // prevents phantom reads + repeatable reads + dirty reads (slowest)
            , isolation = Isolation.READ_COMMITTED
            , rollbackFor = Exception.class
            , readOnly = true
            )

    public AngulaUser findUserById(Long id) {
        var existingUser = userRepo.findById(id);
        if (existingUser.isEmpty()) {
            return null;
        }
        var user = existingUser.get();
        user.setUsername(user.getUsername() + " from service");
        return user;
    }

	public void createInitialUser() throws Exception{
		new AngulaUser();
		userRepo.save(
			AngulaUser.builder()
				.username("user")
				.password(encoder.encode("1234"))
				.role("USER")
				.build()
		);
	}
}
