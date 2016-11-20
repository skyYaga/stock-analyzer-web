package eu.yaga.stockanalyzer.repository;

import eu.yaga.stockanalyzer.model.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by andreas on 19.11.16.
 */
public interface UserRepository extends MongoRepository<MongoUser, String> {
    MongoUser findByUsername(String username);
}
