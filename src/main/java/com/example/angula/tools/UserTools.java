package com.example.angula.tools;

import com.example.angula.database.model.AngulaUser;
import com.example.angula.database.repository.AngulaUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserTools {

    private final AngulaUserRepo userRepo;

    @Tool(description = "get details about the user like username, roles etc.")
    public AngulaUser getUser() {
        return userRepo.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();
    }
}
