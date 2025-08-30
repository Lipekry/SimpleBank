package br.com.simpleBank.dtos.accountEvents.Result;

import br.com.simpleBank.dtos.account.AccountDTO;

public class TransferResponseDTO {

    AccountDTO origin;
    AccountDTO destination;

    public TransferResponseDTO() {
        this.origin = new AccountDTO();
        this.destination = new AccountDTO();
    }

    public AccountDTO getOrigin() {
        return origin;
    }

    public void setOrigin(AccountDTO origin) {
        this.origin = origin;
    }

    public AccountDTO getDestination() {
        return destination;
    }

    public void setDestination(AccountDTO destination) {
        this.destination = destination;
    }
}
