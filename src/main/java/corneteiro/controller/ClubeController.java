package corneteiro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final TimeService timeService;
}
