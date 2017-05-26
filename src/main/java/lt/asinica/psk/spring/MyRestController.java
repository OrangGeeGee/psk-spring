package lt.asinica.psk.spring;

import lt.asinica.psk.spring.beans.Book;
import lt.asinica.psk.spring.beans.Purchase;
import lt.asinica.psk.spring.beans.PurchaseRequest;
import lt.asinica.psk.spring.beans.Store;
import lt.asinica.psk.spring.repositories.BookRepository;
import lt.asinica.psk.spring.repositories.StoreRepository;
import lt.asinica.psk.spring.services.BookService;
import lt.asinica.psk.spring.services.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OptimisticLockException;
import java.util.concurrent.Future;

@RestController
public class MyRestController {
    private final Logger logger = LoggerFactory.getLogger(PurchaseService.class);

    private final BookService bookService;
    private final StoreRepository storeRepository;
    private final PurchaseService purchaseService;

    @Autowired
    public MyRestController(BookService bookService, StoreRepository storeRepository, PurchaseService purchaseService) {
        this.bookService = bookService;
        this.storeRepository = storeRepository;
        this.purchaseService = purchaseService;
    }

    @GetMapping("books")
    public Iterable<Book> listBooks() {
        return bookService.list();
    }

    @GetMapping("stores")
    public Iterable<Store> listStores() {
        return storeRepository.findAll();
    }

    @GetMapping("purchase")
    public Iterable<Purchase> listPurchases() {
        return purchaseService.list();
    }

    @PostMapping("purchase")
    public Purchase registerPurchase(@RequestBody PurchaseRequest purchaseRequest) {
        return purchaseService.register(purchaseRequest.getBookId(), purchaseRequest.getStoreId());
    }

    @PutMapping("books/{id}")
    public Book update(@PathVariable Long id, @RequestBody String title) throws InterruptedException {
        Book book = bookService.get(id);
        book.setTitle(title);
        Thread.sleep(5000);
        try {
            bookService.store(book);
        } catch(Throwable e) {
            logger.warn("Optimistic Lock exception encountered, retry!", e);
            book = bookService.get(id);
            book.setTitle(title);
            bookService.store(book);
        }
        return book;
    }

}
