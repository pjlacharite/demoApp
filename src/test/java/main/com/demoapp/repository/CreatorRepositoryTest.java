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
        assertEquals("Creator should be saved", creator, creatorRepository.save(creator));
    }

    @Test
    public void testFindOne() {
        Creator creatorToSave = new Creator();
        Creator savedCreator = creatorRepository.save(creatorToSave);
        Creator retrievedCreator = creatorRepository.findOne(savedCreator.getId());
        assertEquals("Should be able to retrieve creator by its id", retrievedCreator, savedCreator);
    }

    @Test
    public void testDelete() {
        Creator creatorToSave = new Creator();
        Creator savedCreator = creatorRepository.save(creatorToSave);
        creatorRepository.delete(savedCreator.getId());
        assertTrue("Creator should be deleted", creatorRepository.findOne(savedCreator.getId()) == null);
    }
}