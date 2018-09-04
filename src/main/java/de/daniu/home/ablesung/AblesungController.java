package de.daniu.home.ablesung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(value = "/ablesungen")
public class AblesungController {
    private static final String DEFAULT_VON_DATE = "1999-01-01";
    private static final String DEFAULT_BIS_DATE = "3000-12-31";

    @Autowired
    private AblesungService service;

    @GetMapping
    public List<Ablesung> getAblesungen(@RequestParam(defaultValue = DEFAULT_VON_DATE) LocalDate von,
                                        @RequestParam(defaultValue = DEFAULT_BIS_DATE) LocalDate bis) {
        return service.getAblesungen(von, bis);
    }

    @GetMapping(value = "/meter/{meterId}")
    public List<Ablesung> getAblesungen(@PathVariable("meterId") String meterId,
                                        @RequestParam(defaultValue = DEFAULT_VON_DATE) LocalDate von,
                                        @RequestParam(defaultValue = DEFAULT_BIS_DATE) LocalDate bis) {
        return service.getAblesungen(meterId, von, bis);
    }

    @PostMapping(value = "/meter/{meterId}")
    public ResponseEntity<Ablesung> addAblesung(@PathVariable("meterId") String meterId, @RequestParam BigDecimal wert) {
        Ablesung ablesung = Ablesung.SimpleAblesung.builder()
                .wert(wert)
                .meterId(meterId)
                .datum(LocalDate.now())
                .build();
        service.addAblesung(ablesung);

        URI uri = ServletUriComponentsBuilder.fromHttpUrl("http://localhost:8080")
                .path("/ablesungen").build()
                .toUri();

        return ResponseEntity.created(uri).body(ablesung);
    }

    @PostMapping
    public void addAblesungen(@RequestParam Ablesung ablesung) {
        service.addAblesung(ablesung);
    }
}
