package com.vulnapp.vulnerabilities.deserialization.payload;

import java.io.Serializable;

public class SystemConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private String host;
    private int port;
    private String environment;

    public SystemConfig(String host, int port, String environment) {
        this.host = host;
        this.port = port;
        this.environment = environment;
    }

    public String getEnvironment() {
        return environment;
    }

    @Override
    public String toString() {
        return "Host: " + host + ", Port: " + port + ", Environment: " + environment;
    }
}
