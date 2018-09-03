package de.daniu.home.ablesung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    @GetMapping(value = "/meter/{$meterId}")
    public List<Ablesung> getAblesungen(@PathParam("meterId") String meterId,
                                        @RequestParam(defaultValue = DEFAULT_VON_DATE) LocalDate von,
                                        @RequestParam(defaultValue = DEFAULT_BIS_DATE) LocalDate bis) {
        return service.getAblesungen(meterId, von, bis);
    }

    @PostMapping
    public void addMapping(@RequestParam Ablesung ablesung) {
        service.addAblesung(ablesung);
    }
}
