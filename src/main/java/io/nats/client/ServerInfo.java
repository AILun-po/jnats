/*******************************************************************************
 * Copyright (c) 2015-2016 Apcera Inc. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the MIT License (MIT) which accompanies this
 * distribution, and is available at http://opensource.org/licenses/MIT
 *******************************************************************************/

package io.nats.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

class ServerInfo {
    @SerializedName("server_id")
    private String id;

    @SerializedName("version")
    private String version;

    @SerializedName("go")
    private String goVersion;

    @SerializedName("host")
    private String host;

    @SerializedName("port")
    private int port;

    @SerializedName("auth_required")
    private boolean authRequired;

    @SerializedName("ssl_required")
    private boolean sslRequired;

    @SerializedName("tls_required")
    private boolean tlsRequired;

    @SerializedName("tls_verify")
    private boolean tlsVerify;

    @SerializedName("max_payload")
    private long maxPayload; // int64 in Go

    @SerializedName("connect_urls")
    private String[] connectUrls;

    private transient String jsonString = null;
    static private transient Gson gson = new GsonBuilder().create();

    protected ServerInfo(String id, String host, int port, String version, boolean authRequired,
            boolean tlsRequired, int maxPayload, final String[] connectUrls) {

        this.id = id;
        this.host = host;
        this.port = port;
        this.version = version;
        this.authRequired = authRequired;
        this.tlsRequired = tlsRequired;
        this.maxPayload = maxPayload;
        this.connectUrls = connectUrls;
    }

    // public ServerInfo(String infoString) {
    //
    // this.id = input.id;
    // this.host = input.host;
    // this.port = input.port;
    // this.version = input.version;
    // this.goVersion = input.goVersion;
    // this.authRequired = input.authRequired;
    // this.sslRequired = input.sslRequired;
    // this.tlsRequired = input.tlsRequired;
    // this.tlsVerify = input.tlsVerify;
    // this.maxPayload = input.maxPayload;
    // if (input.connectUrls != null) {
    // this.connectUrls = Arrays.copyOf(input.connectUrls, input.connectUrls.length);
    // }
    // System.err.println("new ServerInfo: " + this.jsonString);
    // }

    protected ServerInfo(ServerInfo input) {
        this.id = input.id;
        this.version = input.version;
        this.goVersion = input.goVersion;
        this.host = input.host;
        this.port = input.port;
        this.authRequired = input.authRequired;
        this.sslRequired = input.sslRequired;
        this.tlsRequired = input.tlsRequired;
        this.tlsVerify = input.tlsVerify;
        this.maxPayload = input.maxPayload;
        if (input.connectUrls != null) {
            this.connectUrls = Arrays.copyOf(input.connectUrls, input.connectUrls.length);
        }
    }

    protected static ServerInfo createFromWire(String infoString) {
        ServerInfo rv = null;
        String jsonString = infoString.replaceFirst("^INFO ", "").trim();
        // logger.info("jsonString = \"" + jsonString + "\"");
        // FIXME Infinite loop here
        rv = gson.fromJson(jsonString, ServerInfo.class);
        return rv;
    }

    /**
     * Returns the server_id.
     * 
     * @return the id
     */
    String getId() {
        return id;
    }

    /**
     * Returns the host.
     * 
     * @return the host
     */
    String getHost() {
        return host;
    }

    /**
     * Returns the port.
     * 
     * @return the port
     */
    int getPort() {
        return port;
    }

    /**
     * Returns the NATS server version.
     * 
     * @return the gnatsd server version
     */
    String getVersion() {
        return version;
    }

    /**
     * @return the authRequired
     */
    boolean isAuthRequired() {
        return authRequired;
    }

    /**
     * @return the tlsRequired
     */
    boolean isTlsRequired() {
        return tlsRequired;
    }

    /**
     * @return the maxPayload
     */
    long getMaxPayload() {
        return maxPayload;
    }

    String[] getConnectUrls() {
        return connectUrls;
    }

    public String toString() {
        String rv = null;
        if (jsonString == null) {
            jsonString = gson.toJson(this);
        }
        rv = String.format("INFO %s", jsonString);
        return rv;
    }
}
