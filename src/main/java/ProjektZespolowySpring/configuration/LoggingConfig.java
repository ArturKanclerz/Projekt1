package ProjektZespolowySpring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ServletContextRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class LoggingConfig {

    @Bean
    public ServletContextRequestLoggingFilter loggingFilter() {
        ServletContextRequestLoggingFilter filter = new ServletContextRequestLoggingFilter() {
            @Override
            protected void afterRequest(HttpServletRequest request, String message) {
                super.afterRequest(request, request.getMethod() + ": [" + removePassword(message));
            }
        };
        filter.setIncludeClientInfo(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(500);
        filter.setAfterMessagePrefix("");
        return filter;
    }

    private String removePassword(String message) {
        Matcher passwordMatcher = Pattern.compile("\"password\"\\s*:").matcher(message);
        if (passwordMatcher.find()) {
            int start = passwordMatcher.end();
            int end = message.lastIndexOf('}');
            Matcher matcher = Pattern.compile(",\\s*\".*?\"\\s*:").matcher(message);
            end = matcher.find(start) ? matcher.start() : ((end == -1) ? (message.length() - 1) : end);
            message = new StringBuilder(message).replace(start, end, "<removed>").toString();
        }
        return message;
    }

}
