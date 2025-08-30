package br.com.simpleBank.service;

import br.com.simpleBank.dtos.accountEvents.Request.DepositRequestDTO;
import br.com.simpleBank.dtos.accountEvents.Request.TransferRequestDTO;
import br.com.simpleBank.dtos.accountEvents.Request.WithdrawRequestDTO;
import br.com.simpleBank.dtos.accountEvents.Result.DepositResponseDTO;
import br.com.simpleBank.dtos.accountEvents.Result.TransferResponseDTO;
import br.com.simpleBank.dtos.accountEvents.Result.WithdrawResponseDTO;
import br.com.simpleBank.models.AccountModel;
import br.com.simpleBank.repository.Implementations.AccountRepositoryInMemory;
import br.com.simpleBank.repository.Interfaces.IAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Map;

public class AccountService {

    private final IAccountRepository repository;

    public AccountService(IAccountRepository repository) {
        this.repository = repository;
    }

    private AccountModel getAccount(String accountID) {
        return repository.getAccountByID(accountID);
    }

    public static Integer getAccountBalance(String accountID) throws Exception {
        IAccountRepository repository = new AccountRepositoryInMemory();
        AccountService service = new AccountService(repository);
        AccountModel model = service.getAccount(accountID);
        if (model != null) {
            return model.getBalance();
        } else {
            throw new AccountNotFoundException();
        }
    }

    private void reset() {
        this.repository.resetAccounts();
    }

    public static String resetAccounts() throws Exception {
        IAccountRepository repository = new AccountRepositoryInMemory();
        AccountService service = new AccountService(repository);
        try {
            service.reset();
            return "OK";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private Integer deposit(String accountID, Integer amount) {
        AccountModel accountModel = this.repository.getAccountByID(accountID);
        if (accountModel == null) {
            accountModel = this.repository.insertAccount(accountID, 0);
        }
        this.repository.deposit(accountID, amount);
        return accountModel.getBalance();
    }

    public static DepositResponseDTO deposit(DepositRequestDTO depositDTO) {
        IAccountRepository repository = new AccountRepositoryInMemory();
        AccountService service = new AccountService(repository);

        Integer balance = service.deposit(depositDTO.getDestination(), depositDTO.getAmount());
        DepositResponseDTO depositResponseDTO = new DepositResponseDTO();
        depositResponseDTO.getDestination().setId(depositDTO.getDestination());
        depositResponseDTO.getDestination().setBalance(balance);
        return depositResponseDTO;
    }

    private Integer withdraw(String accountID, Integer amount) throws Exception {
        AccountModel accountModel = this.repository.getAccountByID(accountID);
        if (accountModel == null) {
            throw new AccountNotFoundException();
        }
        return this.repository.withdraw(accountID, amount);
    }

    public static WithdrawResponseDTO withdraw(WithdrawRequestDTO withdrawDTO) throws Exception {
        IAccountRepository repository = new AccountRepositoryInMemory();
        AccountService service = new AccountService(repository);
        Integer balance = service.withdraw(withdrawDTO.getOrigin(), withdrawDTO.getAmount());
        WithdrawResponseDTO withdrawResponseDTO = new WithdrawResponseDTO();
        withdrawResponseDTO.getOrigin().setId(withdrawDTO.getOrigin());
        withdrawResponseDTO.getOrigin().setBalance(balance);
        return withdrawResponseDTO;
    }

    private void ValidTransfer(TransferRequestDTO transferDTO) throws Exception {
        AccountModel accountOrigin = this.repository.getAccountByID(transferDTO.getOrigin());
        if (accountOrigin == null) {
            throw new AccountNotFoundException();
        }

        AccountModel accountDestination = this.repository.getAccountByID(transferDTO.getDestination());
        if (accountDestination == null) {
            this.repository.insertAccount(transferDTO.getDestination(), 0);
        }

        if (accountOrigin.getBalance()<transferDTO.getAmount()) {
            throw new Exception();
        }
    }

    public static TransferResponseDTO transfer(TransferRequestDTO transferDTO) throws Exception {
        IAccountRepository repository = new AccountRepositoryInMemory();
        AccountService service = new AccountService(repository);

        service.ValidTransfer(transferDTO);
        Integer balanceWithdraw = service.withdraw(transferDTO.getOrigin(), transferDTO.getAmount());
        Integer balanceDeposit = service.deposit(transferDTO.getDestination(), transferDTO.getAmount());

        TransferResponseDTO transferResponseDTO = new TransferResponseDTO();

        transferResponseDTO.getOrigin().setId(transferDTO.getOrigin());
        transferResponseDTO.getOrigin().setBalance(balanceWithdraw);

        transferResponseDTO.getDestination().setId(transferDTO.getDestination());
        transferResponseDTO.getDestination().setBalance(balanceDeposit);

        return transferResponseDTO;
    }

    public static Object handleEvent(Map<String, Object> request) throws Exception {
        String type = request.get("type").toString();
        ObjectMapper mapper = new ObjectMapper();
        return switch (type) {
            case "deposit" -> {
                DepositRequestDTO dto = mapper.convertValue(request, DepositRequestDTO.class);
                yield AccountService.deposit(dto);
            }
            case "withdraw" -> {
                WithdrawRequestDTO dto = mapper.convertValue(request, WithdrawRequestDTO.class);
                yield AccountService.withdraw(dto);
            }
            case "transfer" -> {
                TransferRequestDTO dto = mapper.convertValue(request, TransferRequestDTO.class);
                yield AccountService.transfer(dto);
            }
            default -> throw new IllegalArgumentException("Tipo de evento inválido");
        };
    }

}
