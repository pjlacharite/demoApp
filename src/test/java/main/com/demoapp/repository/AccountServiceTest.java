package main.com.demoapp.repository;

import com.demoapp.Application;
import com.demoapp.model.subscription.Account;
import com.demoapp.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;


    @Test
    public void testFindByAccountIdentifier() {
        Account account = new Account();
        account.setAccountIdentifier("123");
        accountService.save(account);
        Optional<Account> accountRetrieved = accountService.findByAccountIdentifier("123");
        assertTrue("Should find an account with this identifier", accountRetrieved.isPresent());
    }

    @Test
    public void testSave() {
        Account account = new Account();
        Account savedAccount = accountService.save(account);
        assertEquals("Account should be saved", account, savedAccount);
    }

    @Test
    public void testUpdate() {
        Account account = new Account();
        account.setAccountIdentifier("123");
        account.setAccountIdentifier("INACTIVE");
        Account savedAccount = accountService.save(account);
        assertEquals("Account should be saved", account, savedAccount);
        Account changedAccount = new Account();
        changedAccount.setAccountIdentifier("123");
        changedAccount.setStatus("ACTIVE");
        Account updatedAccount = accountService.update(changedAccount);
        assertTrue("Account should be update with status active", "ACTIVE".equals(updatedAccount.getStatus()));
    }

}
