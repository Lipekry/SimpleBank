package br.com.simpleBank.dtos.accountEvents.Request;

public abstract class BaseEventDTO {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
