package br.com.simpleBank.dtos.accountEvents.Request;

public class WithdrawRequestDTO extends BaseEventDTO {
    private String origin;
    private Integer amount;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amountInCents) {
        this.amount = amountInCents;
    }
}
