package lt.asinica.psk.spring.services;

import lt.asinica.psk.spring.beans.Book;
import lt.asinica.psk.spring.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> list() {
        return bookRepository.findAll();
    }

    public Book get(Long id) {
        return bookRepository.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = OptimisticLockException.class)
    public void store(Book book) {
        bookRepository.save(book);
    }
}
