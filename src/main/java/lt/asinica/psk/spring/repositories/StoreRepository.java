package lt.asinica.psk.spring.repositories;

import lt.asinica.psk.spring.beans.Store;
import org.springframework.data.repository.CrudRepository;

public interface StoreRepository extends CrudRepository<Store, Long> {
}
