package main.com.demoapp.repository;

import com.demoapp.Application;
import com.demoapp.model.subscription.Creator;
import com.demoapp.repository.CreatorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CreatorRepositoryTest {

    @Autowired
    private CreatorRepository creatorRepository;

    @Test
    public void testSave() {
        Creator creator = new Creator();
        creator.setUuid("30d1ce50-3cc1-46f5-89f3-aa0f19fec18c");
        assertEquals("Creator should be saved", creator, creatorRepository.save(creator));
    }

    @Test
    public void testFindOne() {
        Creator creatorToSave = new Creator();
        creatorToSave.setUuid("30d1ce50-3cc1-46f5-89f3-aa0f19fec18c");
        Creator savedCreator = creatorRepository.save(creatorToSave);
        Creator retrievedCreator = creatorRepository.findOne(savedCreator.getUuid());
        assertEquals("Should be able to retrieve creator by its id", retrievedCreator, savedCreator);
    }

    @Test
    public void testDelete() {
        Creator creatorToSave = new Creator();
        creatorToSave.setUuid("30d1ce50-3cc1-46f5-89f3-aa0f19fec18c");
        Creator savedCreator = creatorRepository.save(creatorToSave);
        creatorRepository.delete(savedCreator.getUuid());
        assertTrue("Creator should be deleted", creatorRepository.findOne(savedCreator.getUuid()) == null);
    }
}