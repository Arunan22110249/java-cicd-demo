package com.example.demo.controller;

import com.example.demo.model.MenuItem;
import com.example.demo.model.SaleRecord;
import com.example.demo.repo.InMemoryRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
public class MenuController {

    private final InMemoryRepo repo;

    public MenuController(InMemoryRepo repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<MenuItem> list() {
        return repo.all().stream()
                .sorted(Comparator.comparing(MenuItem::getName))
                .collect(Collectors.toList());
    }

    @PostMapping("/{id}/sales")
    public ResponseEntity<String> addSale(@PathVariable String id, @RequestBody SaleRecord body) {
        if (body.getDate() == null) {
            body.setDate(LocalDate.now());
        }
        repo.addSale(id, body);
        return ResponseEntity.ok("recorded");
    }

    @GetMapping("/{id}/predict")
    public ResponseEntity<Integer> predict(@PathVariable String id,
                                           @RequestParam(required = false, defaultValue = "3") int days) {
        return repo.get(id)
                .map(item -> {
                    List<SaleRecord> hist = item.getHistory();
                    if (hist.isEmpty()) return ResponseEntity.ok(0);
                    // take last N records by date
                    List<SaleRecord> recent = hist.stream()
                            .sorted(Comparator.comparing(SaleRecord::getDate).reversed())
                            .limit(days)
                            .collect(Collectors.toList());
                    double avg = recent.stream().mapToInt(SaleRecord::getQuantity).average().orElse(0);
                    int predicted = (int) Math.round(avg);
                    return ResponseEntity.ok(predicted);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
