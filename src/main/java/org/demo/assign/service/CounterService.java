package org.demo.assign.service;

import lombok.extern.log4j.Log4j2;
import org.demo.assign.model.CounterTracker;
import org.demo.assign.repository.CounterRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
@Service
public class CounterService {

  private final AtomicInteger counter = new AtomicInteger(50);
  Instant instant = Instant.now();


  CounterRepository counterRepository;

  public CounterService(CounterRepository counterRepository) {
    this.counterRepository = counterRepository;
  }

  public void  incrementCounter(ThreadPoolExecutor executorProducer){
    log.debug("start the producer");
    executorProducer.execute(() -> {
      while (counter.get() != 100){
        counter.set(counter.get() + 1);
        log.debug("===================================");
        log.debug("Thread Name  "+ Thread.currentThread().getName() + "" +
            "  currentValue of consumer "+ counter);
        log.debug("===================================");
      }

      if(counter.get() == 100){
        saveCounter(counter.get());
        counter.set(50);
        executorProducer.shutdown();
      }
    });
  }

  public void decrementCounter(ThreadPoolExecutor executorConsumer){
    log.debug("start the consumer");
    executorConsumer.execute(() -> {
      while (counter.get() != 0){
        counter.set(counter.get() - 1);
        log.debug("===================================");
        log.debug("Thread Name  "+ Thread.currentThread().getName() + "" +
            "  currentValue of consumer "+ counter);
        log.debug("===================================");
      }
      if(counter.get() == 0){
        saveCounter(counter.get());
        counter.set(50);
        executorConsumer.shutdown();
      }
    });

  }

  private void saveCounter(int counter){
    CounterTracker counterTracker = new CounterTracker();
    long timeStampMillis = instant.toEpochMilli();
    counterTracker.setCounter(counter);
    counterTracker.setTimeStamp(timeStampMillis );
    counterRepository.save(counterTracker);
  }

  public void resetCounter(int updatedCounterVal){
      counter.set(updatedCounterVal);
      log.debug("counter val reset to " + counter.get());
  }
}
