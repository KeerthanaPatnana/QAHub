package backend;

import backend.Entity.User;
import backend.Repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    @Autowired
    UserRepository userRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Log authenticated object
        System.out.println("Authentication successful for user: " + authentication);
        String email = ((DefaultOidcUser) authentication.getPrincipal()).getAttribute("email");
        // get user by email
        userRepository.findByEmail(email).ifPresentOrElse(
                user -> {
                    System.out.println("User is already present");
                },
                () -> {
                    System.out.println("User is not present, creating new user");
                    User user = new User();
                    String name = ((DefaultOidcUser) authentication.getPrincipal()).getAttribute("email");
                    user.setName(name);
                    user.setEmail(email);

                    userRepository.save(user);
                }
        );
        // redirect to the home page
        response.sendRedirect("http://localhost:3000");
    }
}
