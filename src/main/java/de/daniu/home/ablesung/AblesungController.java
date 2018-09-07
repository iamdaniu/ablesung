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
    private static final LocalDate DEFAULT_VON_DATE = LocalDate.of(1999, 1, 1);
    private static final LocalDate DEFAULT_BIS_DATE = LocalDate.of(3000, 2, 1);

    @Autowired
    private AblesungService service;

    @GetMapping
    public List<Ablesung> getAblesungen(@RequestParam LocalDate von,
                                        @RequestParam LocalDate bis) {
        von = von != null ? von : DEFAULT_VON_DATE;
        bis = bis != null ? bis : DEFAULT_BIS_DATE;
        return service.getAblesungen(von, bis);
    }

    @GetMapping(value = "/meter/{meterId}")
    public List<Ablesung> getAblesungen(@PathVariable("meterId") String meterId,
                                        @RequestParam LocalDate von,
                                        @RequestParam LocalDate bis) {
        von = von != null ? von : DEFAULT_VON_DATE;
        bis = bis != null ? bis : DEFAULT_BIS_DATE;
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
