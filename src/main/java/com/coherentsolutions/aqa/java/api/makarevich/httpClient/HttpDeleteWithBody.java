package com.coherentsolutions.aqa.java.api.makarevich.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpDelete;

import java.net.URI;

class HttpDeleteWithBody extends HttpDelete {
    private HttpEntity entity;

    public HttpDeleteWithBody() {
    }

    public HttpDeleteWithBody(URI uri) {
        this.setURI(uri);
    }

    public HttpDeleteWithBody(String uri) {
        this.setURI(URI.create(uri));
    }

    public HttpEntity getEntity() {
        return this.entity;
    }

    public void setEntity(HttpEntity entity) {
        this.entity = entity;
    }
}
