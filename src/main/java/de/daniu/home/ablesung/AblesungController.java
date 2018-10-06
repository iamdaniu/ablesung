package de.daniu.home.ablesung;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping(value = "/")
public class AblesungController {
    private static final LocalDate DEFAULT_VON_DATE = LocalDate.of(1999, 1, 1);
    private static final LocalDate DEFAULT_BIS_DATE = LocalDate.of(3000, 2, 1);

    @Autowired
    private AblesungService service;

    @GetMapping
    public List<Ablesung> getAblesungen(@RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate von,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate bis) {
        LocalDate vonDatum = von != null ? von : DEFAULT_VON_DATE;
        LocalDate bisDatum = bis != null ? bis : DEFAULT_BIS_DATE;
        return service.getAblesungen(vonDatum, bisDatum);
    }

    @GetMapping(value = "/meter/{meterId}")
    public List<Ablesung> getAblesungen(@PathVariable("meterId") String meterId,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate von,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate bis) {
        LocalDate vonDatum = von != null ? von : DEFAULT_VON_DATE;
        LocalDate bisDatum = bis != null ? bis : DEFAULT_BIS_DATE;
        return service.getAblesungen(meterId, vonDatum, bisDatum);
    }

    @GetMapping(value = "/readingsList")
    public String getAblesungen(Model model) {
        List<Ablesung> ablesungen = service.getAblesungen(DEFAULT_VON_DATE, DEFAULT_BIS_DATE);
        model.addAttribute("readings", ablesungen);
        return "readingsList";
    }

    @PostMapping(value = "/meter/{meterId}")
    public ResponseEntity<Ablesung> addAblesung(@PathVariable("meterId") String meterId,
            @RequestParam @DateTimeFormat(iso = DATE) LocalDate datum, @RequestParam BigDecimal wert) {
        Ablesung ablesung = Ablesung.SimpleAblesung.builder()
                .datum(datum)
                .meterId(meterId)
                .wert(wert)
                .build();
        service.addAblesung(ablesung);

        URI uri = ServletUriComponentsBuilder.fromHttpUrl("http://localhost:8100").path("/readingsList").build()
                .toUri();

        return ResponseEntity.created(uri).body(ablesung);
    }

    @PostMapping
    public void addAblesungen(@RequestParam Ablesung ablesung) {
        service.addAblesung(ablesung);
    }
}
