package ProjektZespolowySpring.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class Util {

    public static boolean isAdminOrUser(Authentication authentication, String username) {
        return isAdmin(authentication) || isUser(authentication, username);
    }

    public static boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch("ADMIN"::equals);
    }

    public static boolean isUser(Authentication authentication, String username) {
        return authentication.getName().equals(username);
    }

    public static String getErrorMessage(BindingResult result) {
        return result.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
    }

}
