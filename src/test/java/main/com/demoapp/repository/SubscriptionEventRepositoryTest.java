package main.com.demoapp.repository;

import com.demoapp.Application;
import com.demoapp.model.subscription.SubscriptionEvent;
import com.demoapp.repository.SubscriptionEventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SubscriptionEventRepositoryTest {

    @Autowired
    private SubscriptionEventRepository subscriptionEventRepository;

    @Test
    public void testSave() {
        SubscriptionEvent subscriptionEvent = new SubscriptionEvent();
        assertEquals("SubscriptionEvent should be saved", subscriptionEvent, subscriptionEventRepository.save(subscriptionEvent));
    }

    @Test
    public void testFindOne() {
        SubscriptionEvent savedSubscriptionEvent = subscriptionEventRepository.save(new SubscriptionEvent());
        SubscriptionEvent retrievedSubscriptionEvent = subscriptionEventRepository.findOne(savedSubscriptionEvent.getId());
        assertEquals("Should be able to retrieve subscription event by its id", retrievedSubscriptionEvent, savedSubscriptionEvent);
    }

    @Test
    public void testDelete() {
        SubscriptionEvent savedSubscriptionEvent = subscriptionEventRepository.save(new SubscriptionEvent());
        subscriptionEventRepository.delete(savedSubscriptionEvent.getId());
        assertTrue("Subscription should be deleted", subscriptionEventRepository.findOne(savedSubscriptionEvent.getId()) == null);
    }
}