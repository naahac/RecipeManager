package com.naahac.tvaproject.controller.backend;
import com.naahac.tvaproject.controller.frontend.FrontEndBase;

public abstract class BackEndBase<T extends FrontEndBase>{
    protected T frontEnd;

    public BackEndBase(T frontEnd) {
        this.frontEnd = frontEnd;
    }

    public T getFrontEnd() {
        return frontEnd;
    }

    public void setFrontEnd(T frontEnd) {
        this.frontEnd = frontEnd;
    }

    protected String getTag(){
        return this.getClass().getSimpleName();
    }
}
