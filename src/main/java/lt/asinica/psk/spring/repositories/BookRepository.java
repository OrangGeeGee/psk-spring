package lt.asinica.psk.spring.repositories;

import lt.asinica.psk.spring.beans.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
