package com.monitoring.demo.endpoint;

import com.monitoring.demo.repository.BookRepository;
import com.monitoring.demo.entity.Book;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryChecker implements HealthIndicator {

    private final BookRepository bookRepository;
    private static final int MIN_AMOUNT = 10;

    public InventoryChecker(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Health health() {
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            if (book.getAmount() < MIN_AMOUNT) {
                return Health.down().withDetail("This book is running out of copies: " + book.getName() + ". The minimum amount is " + MIN_AMOUNT + ". Please supply more!", book.getAmount()).build();
            }
        }
        return Health.up().withDetail("All books have enough copies. The minimum required amount is:", MIN_AMOUNT).build();
    }
}
