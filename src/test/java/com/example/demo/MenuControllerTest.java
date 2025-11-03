package com.example.demo;

import com.example.demo.controller.MenuController;
import com.example.demo.model.MenuItem;
import com.example.demo.model.SaleRecord;
import com.example.demo.repo.InMemoryRepo;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuControllerTest {

    @Test
    void predictReturnsAverage() {
        InMemoryRepo repo = new InMemoryRepo();
        repo.init();
        MenuController c = new MenuController(repo);

        // add 3 records for a synthetic item
        repo.save("test", new MenuItem("test", "Test Item"));
        repo.addSale("test", new SaleRecord(LocalDate.now().minusDays(2), 10));
        repo.addSale("test", new SaleRecord(LocalDate.now().minusDays(1), 20));
        repo.addSale("test", new SaleRecord(LocalDate.now(), 30));

        ResponseEntity<Integer> r = c.predict("test", 3);
        assertThat(r.getBody()).isEqualTo(20);
    }
}
