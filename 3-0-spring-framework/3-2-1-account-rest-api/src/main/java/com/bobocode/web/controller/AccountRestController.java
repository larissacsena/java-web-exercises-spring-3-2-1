package com.bobocode.web.controller;

import com.bobocode.dao.AccountDao;
import com.bobocode.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * todo: 1. Configure rest controller that handles requests with url "/accounts" - ok
 * todo: 2. Inject {@link AccountDao} implementation - ok
 * todo: 3. Implement method that handles GET request and returns a list of accounts - ok
 * todo: 4. Implement method that handles GET request with id as path variable and returns account by id - ok
 * todo: 5. Implement method that handles POST request, receives account as request body, saves account and returns it
 * todo:    Configure HTTP response status code 201 - CREATED - ok
 * todo: 6. Implement method that handles PUT request with id as path variable and receives account as request body. - ok
 * todo:    It check if account id and path variable are the same and throws {@link IllegalStateException} otherwise. - ok
 * todo:    Then it saves received account. Configure HTTP response status code 204 - NO CONTENT - ok
 * todo: 7. Implement method that handles DELETE request with id as path variable removes an account by id - ok
 * todo:    Configure HTTP response status code 204 - NO CONTENT - ok
 */
@RestController
@RequestMapping("/accounts")
public class AccountRestController {
    private final AccountDao accountDao;

    @Autowired
    public AccountRestController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountDao.findAll();
    }

    @GetMapping({"/{id}"})
    public Account getAccountsById(@PathVariable long id) {
        return accountDao.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody Account account) {
        return accountDao.save(account);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable long id) {
        Account account = accountDao.findById(id);
        accountDao.remove(account);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccount(@PathVariable long id, @RequestBody Account account) {
        if (id != (account.getId())) {
            throw new IllegalStateException("Account ID in the path and the account object must match.");
        }
        Account existingAccount = accountDao.findById(id);
        existingAccount.setFirstName(account.getFirstName());
        existingAccount.setLastName(account.getLastName());
        existingAccount.setEmail(account.getEmail());

    }
}
