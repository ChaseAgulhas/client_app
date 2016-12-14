package com.system.odering.front_end.domain.user.Impl;

import com.system.odering.front_end.domain.user.ILogin;

import java.io.Serializable;

/**
 * Created by cfebruary on 2016/09/29.
 */
public class Login implements Serializable, ILogin {
    private Long id;
    private String username;
    private String password;

    private Login(Builder builder)
    {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
    }
    public Long getId(){return id;}
    public String getUsername(){return username;}
    public String getPassword(){return password;}

    public String toString()
    {
        return "Username: " + getUsername()
                + "\nPassword: " + getPassword();
    }

    public static class Builder
    {
        private Long id;
        private String username;
        private String password;

        public Builder(){}

        public Builder id(Long id)
        {
            this.id = id;
            return this;
        }

        public Builder username(String username)
        {
            this.username = username;
            return this;
        }

        public Builder password(String password)
        {
            this.password = password;
            return this;
        }

        public Builder copy(Login login)
        {
            this.id = login.id;
            this.username = login.username;
            this.password = login.password;
            return this;
        }

        public Login build(){return new Login(this);}
    }
}
