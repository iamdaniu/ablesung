package de.daniu.home.ablesung;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    @Autowired
    private Environment environment;

    @GetMapping
    public List<Ablesung> getAblesungen(@RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate von,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate bis) {
        LocalDate vonDatum = von != null ? von : DEFAULT_VON_DATE;
        LocalDate bisDatum = bis != null ? bis : DEFAULT_BIS_DATE;
        return service.getAblesungen(vonDatum, bisDatum);
    }

    @GetMapping(value = "/meter/{meterId}")
    public String getAblesungen(Model model,
            @PathVariable("meterId") String meterId,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate von,
            @RequestParam(required = false) @DateTimeFormat(iso = DATE) LocalDate bis) {
        addListToModel(model, von, bis, (v, b) -> service.getAblesungen(meterId, v, b));
        return "readingsList";
    }

    @GetMapping(value = "/readingsList")
    public String getAblesungen(Model model) {
        addListToModel(model, DEFAULT_VON_DATE, DEFAULT_BIS_DATE, service::getAblesungen);
        return "readingsList";
    }

    @GetMapping(value="input")
    public String inputValues() {
        return "inputReadings";
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

        URI uri = ServletUriComponentsBuilder.fromHttpUrl("http://localhost")
                .port(environment.getProperty("server.port"))
                .path("/readingsList")
                .build()
                .toUri();

        return ResponseEntity.created(uri).body(ablesung);
    }

    @PostMapping
    public void addAblesungen(@RequestParam Ablesung ablesung) {
        service.addAblesung(ablesung);
    }

    private static void addListToModel(Model model, LocalDate von, LocalDate bis,
                                       BiFunction<LocalDate, LocalDate, List<Ablesung>> getList) {
        LocalDate vonDatum = von != null ? von : DEFAULT_VON_DATE;
        LocalDate bisDatum = bis != null ? bis : DEFAULT_BIS_DATE;
        List<Ablesung> ablesungen = getList.apply(vonDatum, bisDatum);
        model.addAttribute("readings", ablesungen);
    }
}
