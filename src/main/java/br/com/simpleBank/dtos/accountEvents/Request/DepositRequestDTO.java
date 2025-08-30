package br.com.simpleBank.dtos.accountEvents.Request;

public class DepositRequestDTO extends BaseEventDTO {
    private String destination;
    private Integer amount;

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
