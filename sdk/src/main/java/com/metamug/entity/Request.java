package com.metamug.entity;


import java.util.Map;

/**
 *
 * @author deepak
 */
public class Request {

    private String uri, id, pid, uid, method;
    private Resource parent;
    private Object body;
    private int statusCode;
    private String acceptHeader;
    protected Map<String, String> params;
    private Resource resource;

    public Request() {
    }

    /**
     * Copy Constructor
     *
     * @param request
     */
    public Request(Request request) {
        this.uri = request.uri;
        this.id = request.id;
        this.pid = request.pid;
        this.uid = request.uid;
        this.method = request.method;
        this.parent = request.parent;
        this.resource = request.resource;
        this.statusCode = request.statusCode;
        this.params = request.params;
    }

    public Request(String uri, String id, String method, Map<String, String> map) {
        this.uri = uri;
        this.id = id;
        this.method = method;
        this.params = map;
    }
    
    public Object getBody(){
        return body;
    }
    
    public void setBody(Object payload){
        this.body = payload;
    }

    public void setDefault(String parameter, String defaultValue) {
        String param = params.get(parameter);
        if (param != null && !"".equals(param.trim())) {
            params.put(parameter, defaultValue);
        }
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    /**
     * Set Item Request Id for this resource
     *
     * @param id
     */
    public void setId(String id) {
        if(id != null && !id.trim().isEmpty()){
            this.id = id;    
        }
    }

    /**
     * Get Parent Resource Item Request Id
     *
     * @return
     */
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * Get authenticated user id for current request.
     *
     * @return
     */
    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMethod() {
        return method;
    }
    
    
    @Deprecated
    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Deprecated
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getParameter(String param) {
        return params.get(param);
    }
    
    
}
