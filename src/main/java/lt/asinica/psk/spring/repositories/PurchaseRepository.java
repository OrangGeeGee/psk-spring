package lt.asinica.psk.spring.repositories;

import lt.asinica.psk.spring.beans.Purchase;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
}
