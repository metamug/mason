package com.metamug.mason.entity;

import com.metamug.entity.Request;

import javax.servlet.jsp.PageContext;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Immutable Map to encapsulate pageContext
 */
final public class ContextMap implements Map<String, Object> {

    private PageContext context;

    public ContextMap(PageContext context) {
        this.context = context;
    }

    /**
     * Get Mason Request object from page context
     *
     * @return Mason Request Object
     */
    public Request getRequest() {
        return (Request) this.context.getRequest().getAttribute("mtgReq");
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return context.getAttribute((String) key);
    }

    @Override
    public Object put(String key, Object value) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<Object> values() {
        return null;
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return null;
    }


}