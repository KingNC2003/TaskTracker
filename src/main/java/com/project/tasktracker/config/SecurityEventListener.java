package com.project.tasktracker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class SecurityEventListener {
    private static final Logger logger =
            LoggerFactory.getLogger(SecurityEventListener.class);
    @EventListener
    public void onLoginSuccess(AuthenticationSuccessEvent event) {
        logger.info(
                "Login successful: username={}",
                event.getAuthentication().getName()
        );
    }

    @EventListener
    public void onLoginFailure(
            AuthenticationFailureBadCredentialsEvent event) {
        logger.warn(
                "Login failed: username={}",
                event.getAuthentication().getName()
        );
    }
}