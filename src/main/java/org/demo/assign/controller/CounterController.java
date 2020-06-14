package org.demo.assign.controller;

import org.demo.assign.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/api")
public class CounterController {

  @Autowired
  CounterService counterService;


  @PostMapping("/")
  public ResponseEntity incrementProducerCounsumerThread(@RequestParam(name = "producer") int producer,
                                                         @RequestParam(name = "consumer") int consumer){

    final ThreadPoolExecutor executorProducer = (ThreadPoolExecutor) Executors.newFixedThreadPool(producer,
        new CustomizableThreadFactory("Producer-"));
    counterService.incrementCounter(executorProducer);
    final ThreadPoolExecutor executorConsumer = (ThreadPoolExecutor) Executors.newFixedThreadPool(consumer,
        new CustomizableThreadFactory("Consumer-"));
    counterService.decrementCounter(executorConsumer);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/{counterVal}")
  public ResponseEntity resetCounter(@PathVariable("counterVal") Integer counterVal){
    return Optional.ofNullable(counterVal)
        .map(p -> {
          counterService.resetCounter(p);
          return  ResponseEntity.status(HttpStatus.OK).body("Counter value updated successfully");
        })
        .orElse(ResponseEntity.status(HttpStatus.OK).body("Could not update the counter"));

  }
}
