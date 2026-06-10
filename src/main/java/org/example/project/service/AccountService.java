package org.example.project.service;

import org.example.project.entity.Account;
import org.example.project.entity.User;
import org.example.project.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account createAccountForUser(User user) {
        Account account = new Account();
        account.setUser(user);
        account.setAccountNumber("ACC" + System.currentTimeMillis());
        account.setBalance(BigDecimal.ZERO);
        return accountRepository.save(account);
    }
}