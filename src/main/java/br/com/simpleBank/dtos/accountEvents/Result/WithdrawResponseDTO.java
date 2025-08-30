package br.com.simpleBank.dtos.accountEvents.Result;

import br.com.simpleBank.dtos.account.AccountDTO;

public class WithdrawResponseDTO {
    AccountDTO origin;

    public WithdrawResponseDTO() {
        this.origin = new AccountDTO();
    }

    public AccountDTO getOrigin() {
        return origin;
    }

    public void setOrigin(AccountDTO origin) {
        this.origin = origin;
    }
}
