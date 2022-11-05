package com.lisovenko.offeraid.load;

import com.lisovenko.offeraid.entities.DTOs.AreaDTO;
import com.lisovenko.offeraid.services.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.lisovenko.offeraid.config.SessionConfig.CURRENT_AREA;

@Component
@RequiredArgsConstructor
public class DefaultAreaInterceptor implements HandlerInterceptor {
  private final AreaService areaService;

  @Value("${default.current-area}")
  private String currentArea;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    HttpSession session = request.getSession();

    if (session.getAttribute(CURRENT_AREA) == null) {
      AreaDTO area = areaService.findByUrl(currentArea);
      session.setAttribute(CURRENT_AREA, area);
    }

    return HandlerInterceptor.super.preHandle(request, response, handler);
  }
}
