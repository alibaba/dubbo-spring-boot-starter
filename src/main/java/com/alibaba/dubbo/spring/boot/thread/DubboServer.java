package com.alibaba.dubbo.spring.boot.thread;

/**
 * Hold the dubbo server
 *
 * @author xionghui
 * @version 1.0.0
 * @since 1.0.0
 */
public final class DubboServer {
  private volatile boolean stopAwait = false;

  public void await() {
    while (!this.stopAwait) {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException ex) {
        // continue and check the flag
      }
    }
  }

  public void stopAwait() {
    this.stopAwait = true;
  }
}
