package com.tradedoubler.xmlprocessorservice.service.resp;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.tradedoubler.xmlprocessorservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetUserResponse {
  private Boolean status;

  @JsonAlias("data")
  private User user;
}
