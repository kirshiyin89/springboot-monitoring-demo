package com.monitoring.demo.endpoint;

import com.monitoring.demo.entity.Book;
import com.monitoring.demo.repository.BookRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        Map<String, Integer> booksRunningOutCopies = books.stream()
                .filter(book -> book.getAmount() < MIN_AMOUNT)
                .collect(Collectors.toMap(Book::getName, Book::getAmount));

        if (!booksRunningOutCopies.isEmpty()) {
            return Health.down().withDetail("The following books are running out of copies: " + booksRunningOutCopies + ". The minimum amount is: ", MIN_AMOUNT).build();
        }
        return Health.up().withDetail("All books have enough copies. The minimum amount is:", MIN_AMOUNT).build();
    }
}
