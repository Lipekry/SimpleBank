package br.com.simpleBank.dtos.accountEvents.Result;

import br.com.simpleBank.dtos.account.AccountDTO;

public class DepositResponseDTO {
    private AccountDTO destination;

    public DepositResponseDTO() {
        this.destination = new AccountDTO();
    }

    public AccountDTO getDestination() {
        return destination;
    }

    public void setDestination(AccountDTO destination) {
        this.destination = destination;
    }
}
