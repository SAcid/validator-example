package com.example.validatorexample.datetimerange.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DateTimeRageController.class)
class DateTimeRageControllerTest {

  @Autowired
  public MockMvc mockMvc;

  @DisplayName("기간체크 성공")
  @Test
  void period_success() throws Exception {
    this.mockMvc.perform(post("/api/period")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
            {
              "startAt": "2024-03-18 10:00:00",
              "endAt": "2024-03-18 12:00:00"
            }            
            """))
        .andExpect(status().isOk());
  }

  @DisplayName("기간체크 실패")
  @Test
  void period_failed() throws Exception {
    this.mockMvc.perform(post("/api/period")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
              "startAt": "2024-03-18 10:00:00",
              "endAt": "2024-03-18 10:00:00"
            }            
            """))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("The date range is not valid"));
  }

  @DisplayName("이벤트 성공")
  @Test
  void event_success() throws Exception {
    this.mockMvc.perform(post("/api/event")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
              "title": "Event !!!",
              "participation": {
                "startAt": "2024-03-18T10:00:00",
                "endAt": "2024-03-18T10:00:00"
              },
              "schedule": {
                "startAt": "2024-04-18T10:00:00",
                "endAt": "2024-04-18T10:00:01"
              }
            }            
            """))
        .andExpect(status().isOk());
  }

  @DisplayName("이벤트 실패 - 참가일정")
  @Test
  void event_fail_01() throws Exception {
    this.mockMvc.perform(post("/api/event")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
              "title": "Event !!!",
              "participation": {
                "startAt": "2024-03-18T10:00:00",
                "endAt": "2024-03-17T10:00:00"
              },
              "schedule": {
                "startAt": "2024-04-18T10:00:00",
                "endAt": "2024-04-18T10:00:01"
              }
            }            
            """))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("참가일정의 시작일자는 종료일자보다 같거나 빨랴아 햡니다."));
  }

  @DisplayName("이벤트 실패 - 행사일정")
  @Test
  void event_fail_02() throws Exception {
    this.mockMvc.perform(post("/api/event")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
            {
              "title": "Event !!!",
              "participation": {
                "startAt": "2024-03-18T10:00:00",
                "endAt": "2024-03-18T10:00:02"
              },
              "schedule": {
                "startAt": "2024-04-18T10:00:00",
                "endAt": "2024-04-18T10:00:00"
              }
            }            
            """))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("행사일정의 시작일자는 종료일자보다 빨랴아 햡니다."));
  }
}