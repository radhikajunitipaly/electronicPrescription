package com.uta.eprescription.activities.authenticationMgr;

public interface AuthenticationCallback<T> {
    void callback(boolean isLoggedIn, String userType);
}
