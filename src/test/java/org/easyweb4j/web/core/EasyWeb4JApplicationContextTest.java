package org.easyweb4j.web.core;

import java.util.Optional;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.easyweb4j.web.core.context.EasyWeb4JApplicationContext;
import org.easyweb4j.web.core.context.impl.GlobalChainedEasyWeb4JApplicationContext;
import org.easyweb4j.web.core.context.impl.LocalChainedEasyWeb4JApplicationContext;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EasyWeb4JApplicationContextTest {

  @Test
  public void global() throws InterruptedException {
    final String key = "hello";
    final Integer value = 1;
    int threads = 10;
    EasyWeb4JApplicationContext<String, Integer> context =
      new GlobalChainedEasyWeb4JApplicationContext<>();
    context.set(key, value);

    ExecutorService executorService = Executors.newFixedThreadPool(threads);
    CyclicBarrier cyclicBarrier = new CyclicBarrier(threads);
    AtomicInteger result = new AtomicInteger(0);

    for (int i = 0; i < threads; i++) {
      executorService.submit(() -> {
        try {
          cyclicBarrier.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
        Optional<Integer> integer = context.get(key);
        result.addAndGet(integer.orElse(10));
      });
    }

    executorService.shutdown();
    executorService.awaitTermination(1000, TimeUnit.DAYS);

    Assert.assertEquals(threads, result.get());

    Assert.assertEquals(context.get("nonexits-key"), Optional.empty());
  }

  @Test
  public void local() throws InterruptedException {
    final String key = "hello";
    final Integer value = 1;
    int threads = 10;
    EasyWeb4JApplicationContext<String, Integer> context =
      new LocalChainedEasyWeb4JApplicationContext<>();
    context.set(key, value);

    ExecutorService executorService = Executors.newFixedThreadPool(threads);
    CyclicBarrier cyclicBarrier = new CyclicBarrier(threads);
    AtomicInteger result = new AtomicInteger(0);

    for (int i = 0; i < threads; i++) {
      executorService.submit(() -> {
        try {
          cyclicBarrier.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
        Optional<Integer> integer = context.get(key);
        result.addAndGet(integer.orElse(10));
        context.set(key, 11);
      });
    }

    executorService.shutdown();
    executorService.awaitTermination(1000, TimeUnit.DAYS);

    Assert.assertEquals(result.get(), threads * 10);

  }

  @Test
  public void chained() {
    GlobalChainedEasyWeb4JApplicationContext<String, Object> top =
      new GlobalChainedEasyWeb4JApplicationContext<>();
    top.set("top", "1");

    GlobalChainedEasyWeb4JApplicationContext<String, Object> second =
      new GlobalChainedEasyWeb4JApplicationContext<>();
    second.setParent(top);
    second.set("sec", "2");

    LocalChainedEasyWeb4JApplicationContext<String, Object> bottom =
      new LocalChainedEasyWeb4JApplicationContext<>();
    bottom.setParent(second);
    bottom.set("bottom", "3");

    Assert.assertEquals(top.get("top"), bottom.get("top"));
    Assert.assertEquals(second.get("sec"), bottom.get("sec"));
    Assert.assertEquals(bottom.get("bottom"), bottom.get("bottom"));

    Assert.assertEquals(top.get("top"), second.get("top"));
    Assert.assertEquals(second.get("bottom"), Optional.empty());
    // as global
    Assert.assertEquals(top.get("sec"), second.get("sec"));

    Assert.assertEquals(top.get("bottom"), Optional.empty());
  }
}
