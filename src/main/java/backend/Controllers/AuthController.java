package backend.Controllers;

import backend.Entity.User;
import backend.DTOs.UserDTO;
import backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser(@AuthenticationPrincipal OAuth2User user) {
        String email = user.getAttribute("email");
        // get user by email
        Optional<User> currentUser = userRepository.findByEmail(email);
        if (currentUser.isPresent()) {
            User user1 = currentUser.get();
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user1.getId());
            userDTO.setName(user1.getName());
            userDTO.setEmail(user1.getEmail());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
