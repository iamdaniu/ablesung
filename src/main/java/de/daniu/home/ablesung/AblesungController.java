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
    @Autowired
    private AblesungService service;

    @GetMapping
    public List<Ablesung> getAblesungen(@RequestParam(defaultValue = "1999-01-01") LocalDate von,
                                        @RequestParam(defaultValue = "3000-12-31") LocalDate bis) {
        return service.getAblesungen(von, bis)
                .collect(toList());
    }

    @GetMapping(value = "/art/${ablesungsArt}")
    public List<Ablesung> getAblesungen(@PathParam("ablesungsArt") AblesungsArt ablesungsArt,
                                        @RequestParam(defaultValue = "1999-01-01") LocalDate von,
                                        @RequestParam(defaultValue = "3000-12-31") LocalDate bis) {
        return service.getAblesungen(von, bis)
                .collect(toList());
    }

    @PostMapping
    public void addMapping(@RequestParam Ablesung ablesung) {
        service.addAblesung(ablesung);
    }
}
