package com.nice.qin.fire.bus;

/**
 * Created by Qin on 2017-01-23-0023.
 */

public class TopEvent {
    private String name;
    private String value;

    public TopEvent(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return value;
    }

    public void setId(String value) {
        this.value = value;
    }
}
