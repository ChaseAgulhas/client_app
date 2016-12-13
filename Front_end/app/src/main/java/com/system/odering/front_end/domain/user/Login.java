package com.system.odering.front_end.domain.user;

import java.io.Serializable;

/**
 * Created by cfebruary on 2016/09/29.
 */
public class Login implements Serializable, ILogin{
    private String username;
    private String password;

    private Login(Builder builder)
    {
        this.username = builder.username;
        this.password = builder.password;
    }

    public String getUsername(){return username;}
    public String getPassword(){return password;}

    public String toString()
    {
        return "Username: " + getUsername()
                + "\nPassword: " + getPassword();
    }

    public static class Builder
    {
        private String username;
        private String password;

        public Builder(){}

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
            this.username = login.getUsername();
            this.password = login.getPassword();
            return this;
        }

        public Login build(){return new Login(this);}
    }
}
