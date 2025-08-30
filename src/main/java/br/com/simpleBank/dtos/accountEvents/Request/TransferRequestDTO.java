package br.com.simpleBank.dtos.accountEvents.Request;

public class TransferRequestDTO extends BaseEventDTO {
    private String origin;
    private String destination;
    private Integer amount;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amountInCents) {
        this.amount = amountInCents;
    }
}
