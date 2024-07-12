package eventManager.experiments;
import java.util.List;


public interface TestService {

    List<TestEntity> findAll();
    TestEntity save(TestEntity testEntity);

    TestEntity update(TestEntity testEntity);

    void deleteById(String id);

    TestEntity findById(String id);

    TestDetailedEntity getDetailedById(String id);
}
