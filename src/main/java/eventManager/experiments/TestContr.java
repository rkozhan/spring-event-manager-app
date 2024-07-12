package eventManager.experiments;
import eventManager.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/t")
public class TestContr {
    @Autowired
    private TestService service;

    @GetMapping()
    public ResponseEntity<List<TestEntity>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping()
    public ResponseEntity<TestEntity> save(@RequestBody TestEntity testEntity) {
        return ResponseEntity.ok(service.save(testEntity));
    }

    @PutMapping()
    public ResponseEntity<TestEntity> update(@RequestBody TestEntity testEntity) {
        return ResponseEntity.ok(service.update(testEntity));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
            service.deleteById(id);
    }

    @GetMapping("/{id}")
    public TestEntity getById(@PathVariable String id) {
        return service.findById(id);
    }

    @GetMapping("/det/{id}")
    public TestDetailedEntity getDetailedById(@PathVariable String id) {
        return service.getDetailedById(id);
    }
}

