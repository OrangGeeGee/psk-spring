package lt.asinica.psk.spring;

import lt.asinica.psk.spring.beans.Book;
import lt.asinica.psk.spring.beans.Purchase;
import lt.asinica.psk.spring.beans.PurchaseRequest;
import lt.asinica.psk.spring.beans.Store;
import lt.asinica.psk.spring.repositories.BookRepository;
import lt.asinica.psk.spring.repositories.PurchaseRepository;
import lt.asinica.psk.spring.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
public class MyRestController {

    private final BookRepository bookRepository;
    private final StoreRepository storeRepository;
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public MyRestController(BookRepository bookRepository, StoreRepository storeRepository, PurchaseRepository purchaseRepository) {
        this.bookRepository = bookRepository;
        this.storeRepository = storeRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @GetMapping("books")
    public Iterable<Book> listBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("stores")
    public Iterable<Store> listStores() {
        return storeRepository.findAll();
    }

    @GetMapping("purchase")
    public Iterable<Purchase> listPurchases() {
        return purchaseRepository.findAll();
    }

    @PostMapping("purchase")
    public Purchase registerPurchase(@RequestBody PurchaseRequest purchaseRequest) {
        Book book = bookRepository.findOne(purchaseRequest.getBookId());
        Store store = storeRepository.findOne(purchaseRequest.getStoreId());
        Purchase purchase = new Purchase(book, store);
        purchaseRepository.save(purchase);
        return purchase;
    }

    @PutMapping("/{id}")
    public Book update(Long id, String title) {
        Book book = bookRepository.findOne(id);
        book.setTitle(title);
        bookRepository.save(book);
        return book;
    }

}
