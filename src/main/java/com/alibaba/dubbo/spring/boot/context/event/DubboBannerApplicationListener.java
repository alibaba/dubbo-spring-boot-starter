package com.alibaba.dubbo.spring.boot.context.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;

import com.alibaba.dubbo.common.Version;
import com.alibaba.dubbo.qos.server.DubboLogo;
import com.alibaba.dubbo.spring.boot.bean.DubboSpringBootStarterConstants;

/**
 * Dubbo banner
 * 
 * @author xionghui
 * @version 2.0.0
 * @since 2.0.0
 */
@Order(LoggingApplicationListener.DEFAULT_ORDER)
public class DubboBannerApplicationListener
    implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
  private static final Log logger = LogFactory.getLog(DubboBannerApplicationListener.class);

  private static Banner.Mode BANNER_MODE = Banner.Mode.CONSOLE;

  public static void setBANNER_MODE(Banner.Mode bANNER_MODE) {
    BANNER_MODE = bANNER_MODE;
  }

  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    if (BANNER_MODE == Banner.Mode.OFF) {
      return;
    }
    String bannerText = this.buildBannerText();
    if (BANNER_MODE == Mode.CONSOLE) {
      System.out.print(bannerText);
    } else if (BANNER_MODE == Mode.LOG) {
      logger.info(bannerText);
    }
  }

  private String buildBannerText() {
    StringBuilder bannerTextBuilder = new StringBuilder();
    bannerTextBuilder.append(DubboSpringBootStarterConstants.LINE_SEPARATOR).append(DubboLogo.dubbo)
        .append(" :: Dubbo ::        (v").append(Version.getVersion()).append(")")
        .append(DubboSpringBootStarterConstants.LINE_SEPARATOR);
    return bannerTextBuilder.toString();
  }
}
