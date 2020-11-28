package com.metamug.entity;

import java.util.Objects;

public class Resource {

    private String name, url;
    private Resource parent;
    private float version;

    public Resource(String name, float version, String url, Resource parent) {
        this.name = name;
        this.version = version;
        this.url = url;
        this.parent = parent;
    }

    public Resource(String name, float version) {
        this.name = name;
        this.version = version;
    }
    
    /**
     * @return resource URL
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * @return parent of this resource
     */
    public Resource getParent() {
        return this.parent;
    }

    /**
     * @return name of the resource.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return version number of the resource
     */
    public float getVersion() {
        return this.version;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Float.floatToIntBits(this.version);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Resource other = (Resource) obj;
        if (Float.floatToIntBits(this.version) != Float.floatToIntBits(other.version)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
