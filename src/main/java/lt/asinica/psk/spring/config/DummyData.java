package lt.asinica.psk.spring.config;

import lt.asinica.psk.spring.beans.Book;
import lt.asinica.psk.spring.beans.Purchase;
import lt.asinica.psk.spring.beans.Store;
import lt.asinica.psk.spring.repositories.BookRepository;
import lt.asinica.psk.spring.repositories.PurchaseRepository;
import lt.asinica.psk.spring.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

@Component
public class DummyData {

    private BookRepository bookRepository;
    private StoreRepository storeRepository;
    private PurchaseRepository purchaseRepository;

    @Autowired
    public DummyData(BookRepository bookRepository, StoreRepository storeRepository, PurchaseRepository purchaseRepository) {
        this.bookRepository = bookRepository;
        this.storeRepository = storeRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @PostConstruct
    @Transactional
    public void init() {
        Store storeA = new Store("Maxima");
        Store storeB = new Store("Rimi");
        Store storeC = new Store("Baltos lankos");

        Book bookA = new Book("Meistras ir Margarita", Arrays.asList(storeA, storeB));
        Book bookB = new Book("Alchemikas", Arrays.asList(storeA, storeC));
        Book bookC = new Book("Trys draugai", Collections.singletonList(storeC));

        Purchase purchase1 = new Purchase(bookA, storeA);
        Purchase purchase2 = new Purchase(bookB, storeC);
        Purchase purchase3 = new Purchase(bookC, storeC);

        storeRepository.save(Arrays.asList(storeA, storeB, storeC));
        bookRepository.save(Arrays.asList(bookA, bookB, bookC));
        purchaseRepository.save(Arrays.asList(purchase1, purchase2, purchase3));

    }
}
