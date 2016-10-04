package main.com.demoapp.repository;

import com.demoapp.Application;
import com.demoapp.model.subscription.Account;
import com.demoapp.model.subscription.Order;
import com.demoapp.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        Account accountRetrieved = accountService.findByAccountIdentifier("123");
        assertTrue("Should find an account with this identifier", accountRetrieved != null);
    }

    @Test
    public void testSave() {
        Account account = new Account();
        Account savedAccount = accountService.save(account);
        assertEquals("Account should be saved", account, savedAccount);
    }

    @Test
    public void testUpdate() {
        Order order = new Order();
        order.setPricingDuration("MONTHLY");
        order.setEditionCode("FREE");

        Account account = new Account();
        account.setAccountIdentifier("123");
        account.setAccountIdentifier("FREE_TRIAL");
        account.setAccountOrder(order);

        Account savedAccount = accountService.save(account);
        assertEquals("Account should be saved", account, savedAccount);

        Order changedOrder = new Order();
        changedOrder.setEditionCode("ULTIMATE");
        changedOrder.setPricingDuration("ANNUALLY");

        Account changedAccount = new Account();
        changedAccount.setAccountIdentifier("123");
        changedAccount.setStatus("ACTIVE");
        changedAccount.setAccountOrder(changedOrder);
        Account updatedAccount = accountService.update(changedAccount);
        assertTrue("Account should be update with status ACTIVE", Account.ACCOUNT_ACTIVE.equals(updatedAccount.getStatus()));
        assertTrue("Order should be update with edition code ULTIMATE", "ULTIMATE".equals(updatedAccount.getAccountOrder().getEditionCode()));
        assertTrue("Order should be update with pricing duration ANNUALLY", "ANNUALLY".equals(updatedAccount.getAccountOrder().getPricingDuration()));
    }

}