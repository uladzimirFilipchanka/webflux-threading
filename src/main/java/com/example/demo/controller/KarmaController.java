package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

@RestController
public class KarmaController {

    @GetMapping("/karma")
    public Flux<Karma> karma() {
        return prepareKarma()
                .map(Karma::new)
                .log();
    }

    @GetMapping("/delayedKarma")
    public Flux<Karma> delayedKarma() {
        return karma()
                .delayElements(Duration.ofMillis(100));
    }

    @GetMapping("/fairKarma")
    public Flux<Karma> fairKarma() {
        return delayedKarma()
                .flatMap(this::makeFair);
    }

    private Flux<Karma> makeFair(Karma original) {
        return Flux.just(new Karma(original.balanceAdjustment() * 10),
                        new Karma(original.balanceAdjustment() * -10))
                .subscribeOn(Schedulers.boundedElastic())
                .log();
    }

    private Flux<Integer> prepareKarma() {
        Random random = new Random();

        return Flux.fromStream(
                Stream.generate(() -> random.nextInt(10))
                        .limit(5));
    }
}
