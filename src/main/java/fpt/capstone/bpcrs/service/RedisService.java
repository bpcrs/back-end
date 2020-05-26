package fpt.capstone.bpcrs.service;

import java.util.Set;

public interface RedisService {

  boolean checkLoginToken(String accountId, String token);

  void setLoginToken(String accountId, String token);

  void setOnlineSession(String rawId, String accountId);

  void setOfflineSession(String rawId, String accountId);

  Set<Object> getOnlineSessions(String rawId);

}
