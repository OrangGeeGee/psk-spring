package lt.asinica.psk.spring.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Purchase {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Book book;
    @ManyToOne
    private Store store;

    public Purchase() {
    }

    public Purchase(Book book, Store store) {
        this.book = book;
        this.store = store;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
