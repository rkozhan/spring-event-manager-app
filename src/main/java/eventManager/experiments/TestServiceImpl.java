package eventManager.experiments;

import eventManager.model.Event;
import eventManager.model.Registration;
import eventManager.service.EventServiceImpl;
import eventManager.service.RegistrationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class TestServiceImpl  implements TestService {
    private final TestRepo repo;

    @Autowired
    private EventServiceImpl eventService;
    @Autowired
    private RegistrationServiceImpl registrationService;


    @Override
    public List<TestEntity> findAll() {
        return repo.findAllByOrderByDateAscTimeAsc();
    }

    @Override
    public TestEntity save(TestEntity testEntity) {

        if (repo.findByEmail(testEntity.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An entity with the same email already exists");
        } else {
            testEntity.setCreatedIn(LocalDateTime.now());
            testEntity.setUpdatedIn(testEntity.getCreatedIn());
        }
        return repo.save(testEntity);
    }

    @Override
    public TestEntity update(TestEntity testEntity) {

        Optional<TestEntity> testEntityToUpdate = repo.findById(testEntity.getId());
        if (testEntityToUpdate.isPresent()) {
            if (!testEntityToUpdate.get().getEmail().equals(testEntity.getEmail())) {
                Optional <TestEntity> withEmail = repo.findByEmail(testEntity.getEmail());
                if (!Objects.equals(withEmail, testEntityToUpdate)) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "An entity with the same email already exists");
                }
            }

            testEntityToUpdate.get().setUpdatedIn(LocalDateTime.now());
            testEntityToUpdate.get().setDate(testEntity.getDate());
            testEntityToUpdate.get().setTime(testEntity.getTime());
            testEntityToUpdate.get().setCompanyName(testEntity.getCompanyName());
            testEntityToUpdate.get().setEmail(testEntity.getEmail());
            testEntityToUpdate.get().setIsCancelled(testEntity.getIsCancelled());
            testEntityToUpdate.get().setImgUrl(testEntity.getImgUrl());
            testEntityToUpdate.get().setImgUrls(testEntity.getImgUrls());
            return repo.save(testEntityToUpdate.get());
        } else {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "TestEntity with id " + testEntity.getId() + " not found");
        }

    }

    @Override
    public TestEntity findById(String id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "TestEntity with id " + id + " not found"));
    }

    @Override
    public TestDetailedEntity getDetailedById(String id) {
        TestDetailedEntity testDetailedEntity = new TestDetailedEntity(this.findById(id));

        List<Registration> userRegistrations = registrationService.findByUserId(id);
        testDetailedEntity.setUserRegistrations(userRegistrations);

        List<Event> joinedEvents = new ArrayList<>();

        //userRegistrations.stream(reg -> {
            //String eventId = reg.getEventId(); //TODO
            //Optional<Event> event = eventService.findById(eventId);
            //if (event.isPresent()) joinedEvents.add(event.get());
        //})
        //createdEvents = eventService.findEventsByCreatedBy(id); //TODO
        return testDetailedEntity;
    }

}
