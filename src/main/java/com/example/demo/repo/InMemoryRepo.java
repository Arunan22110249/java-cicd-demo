package com.example.demo.repo;

import com.example.demo.model.MenuItem;
import com.example.demo.model.SaleRecord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

@Component
public class InMemoryRepo {
    private final Map<String, MenuItem> items = new HashMap<>();

    @PostConstruct
    public void init() {
        // initial sample data
        MenuItem p = new MenuItem("pani-puri", "Panipuri");
        p.getHistory().add(new SaleRecord(LocalDate.now().minusDays(4), 120));
        p.getHistory().add(new SaleRecord(LocalDate.now().minusDays(3), 150));
        p.getHistory().add(new SaleRecord(LocalDate.now().minusDays(2), 130));
        p.getHistory().add(new SaleRecord(LocalDate.now().minusDays(1), 170));
        items.put(p.getId(), p);

        MenuItem d = new MenuItem("bhel", "Bhel Puri");
        d.getHistory().add(new SaleRecord(LocalDate.now().minusDays(3), 80));
        d.getHistory().add(new SaleRecord(LocalDate.now().minusDays(2), 95));
        d.getHistory().add(new SaleRecord(LocalDate.now().minusDays(1), 100));
        items.put(d.getId(), d);
    }

    public Collection<MenuItem> all() { return items.values(); }
    public Optional<MenuItem> get(String id) { return Optional.ofNullable(items.get(id)); }
    public void save(String id, MenuItem item) { items.put(id, item); }
    public void addSale(String id, SaleRecord r) {
        MenuItem it = items.computeIfAbsent(id, k -> new MenuItem(k, k));
        it.getHistory().add(r);
    }
}
