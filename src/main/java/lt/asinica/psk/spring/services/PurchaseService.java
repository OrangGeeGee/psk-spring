package lt.asinica.psk.spring.services;

import lt.asinica.psk.spring.beans.Book;
import lt.asinica.psk.spring.beans.Purchase;
import lt.asinica.psk.spring.beans.Store;
import lt.asinica.psk.spring.repositories.BookRepository;
import lt.asinica.psk.spring.repositories.PurchaseRepository;
import lt.asinica.psk.spring.repositories.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class PurchaseService {

    private final Logger logger = LoggerFactory.getLogger(PurchaseService.class);

    private final BookRepository bookRepository;
    private final StoreRepository storeRepository;
    private final PurchaseRepository purchaseRepository;
    private final SlowService slowService;

    @Autowired
    public PurchaseService(BookRepository bookRepository, StoreRepository storeRepository, PurchaseRepository purchaseRepository, SlowService slowService) {
        this.bookRepository = bookRepository;
        this.storeRepository = storeRepository;
        this.purchaseRepository = purchaseRepository;
        this.slowService = slowService;
    }


    public Iterable<Purchase> list() {
        return purchaseRepository.findAll();
    }

    @Transactional
    public Purchase register(Long bookId, Long storeId) {
        Book book = bookRepository.findOne(bookId);
        if(book == null)
            throw new RuntimeException("Not valid bookId specified.");
        Store store = storeRepository.findOne(storeId);
        if(storeId == null)
            throw new RuntimeException("Not valid storeId specified.");

        logger.debug("1. Calling slow service.");
        Future<Integer> slowResult = slowService.slowMethod(5000);

        if(!slowResult.isDone())
            logger.debug("2, After slow service call, slow method still executing..");


        Purchase purchase = new Purchase(book, store);
        purchaseRepository.save(purchase);
        logger.debug("3. Entity saved in database.");

        try {
            Integer result = slowResult.get();
            logger.debug("4. Slow service finished in " + result + " millis");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return purchase;
    }
}
