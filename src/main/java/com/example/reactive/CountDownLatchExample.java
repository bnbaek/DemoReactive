package com.example.reactive;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
  static final int max = 10;
  /**
   * 단일 쓰레드 테스트
   */
  public static void testSingle() throws Exception {
    long start = System.currentTimeMillis();
    for (long i = 0; i < max; i++) {
      Thread.sleep(1000);
    }

    long elapsedTime = System.currentTimeMillis() - start;
    System.out.println("testSingle elapsed time -> " + elapsedTime);
  }

  /**
   * CountDownLatch 테스트
   */
  public static void testCountDownLatch() throws Exception {
    final CountDownLatch latch = new CountDownLatch(max);

    long start = System.currentTimeMillis();
    for (long i = 0; i < max; i++) {
      new Thread(new Worker(latch)).start();
    }

    latch.await();
    long elapsedTime = System.currentTimeMillis() - start;
    System.out.println("testCountDownLatch elapsed time -> " + elapsedTime);
  }

  /**
   * Job 쓰레드
   */
  static class Worker implements Runnable {
    private CountDownLatch latch;

    public Worker(CountDownLatch latch) {
      this.latch = latch;
    }

    @Override
    public void run() {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      } finally {
        if (this.latch == null)
          return;

        latch.countDown();
      }
    }
  }

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    testSingle();
    testCountDownLatch();
  }
}