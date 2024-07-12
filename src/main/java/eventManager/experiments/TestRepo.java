package eventManager.experiments;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TestRepo  extends MongoRepository<TestEntity, String> {
    List<TestEntity> findAllByOrderByDateAscTimeAsc();

    Optional<TestEntity> findById(String id);

    Optional<TestEntity> findByEmail(String email);
}
