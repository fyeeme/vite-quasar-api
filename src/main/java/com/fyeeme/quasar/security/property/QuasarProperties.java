package com.fyeeme.quasar.security.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ConfigurationProperties("quasar.service")
@ToString
@Getter
@Setter
/**
 * use this properites need annotation
 * @EnableConfigurationProperties(QuasarProperties.class)
 * example is : ConfigCommandRunner
 */
public class QuasarProperties {
    /**
     * 是否开启
     */
    private boolean enabled;
    /**
     * 远程地址
     */
    private InetAddress remoteAddress;
    /**
     * api url
     */
    private String url;

    private final Security security = new Security();


    public static class Security {

        private String username;

        private String password;

        private List<String> roles = new ArrayList<>(Collections.singleton("USER"));

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public InetAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(InetAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Security getSecurity() {
        return security;
    }
}