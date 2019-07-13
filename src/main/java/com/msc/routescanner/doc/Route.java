package com.msc.routescanner.doc;


import java.util.List;

/**
 *
 * @author Michael
 */
public class Route {

    private String method;
    private String route;
    private String response;
    private List<String> inputs;
    private boolean isHead = false;
    private List<String> consumes;
    private List<String> produces;

    
    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the route
     */
    public String getRoute() {
        return route;
    }

    /**
     * @param route the route to set
     */
    public void setRoute(String route) {
        this.route = route;
    }

    /**
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return the inputs
     */
    public List<String> getInputs() {
        return inputs;
    }

    /**
     * @param inputs the inputs to set
     */
    public void setInputs(List<String> inputs) {
        this.inputs = inputs;
    }

    /**
     * @return the isHead
     */
    public boolean isIsHead() {
        return isHead;
    }

    /**
     * @param isHead the isHead to set
     */
    public void setIsHead(boolean isHead) {
        this.isHead = isHead;
    }

    /**
     * @param consumes the consumes to set
     */
    public void setConsumes(List<String> consumes) {
        this.consumes = consumes;
    }

    /**
     * @param produces the produces to set
     */
    public void setProduces(List<String> produces) {
        this.produces = produces;
    }

    /**
     * @return the consumes
     */
    public List<String> getConsumes() {
        return consumes;
    }

    /**
     * @return the produces
     */
    public List<String> getProduces() {
        return produces;
    }

    


}
