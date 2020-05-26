package fpt.capstone.bpcrs.service.impl;

import fpt.capstone.bpcrs.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static fpt.capstone.bpcrs.constant.AppConstant.LOGIN_CACHE;

@Service
public class RedisServiceImpl implements RedisService {

  @Autowired private RedisTemplate<String, Object> redisTemplate;

  @Override
  public boolean checkLoginToken(String accountId, String token) {
    String currentToken = (String) redisTemplate.opsForHash().get(LOGIN_CACHE, accountId);
    return currentToken != null && currentToken.equals(token);
  }

  @Override
  public void setLoginToken(String accountId, String token) {
    redisTemplate.opsForHash().put(LOGIN_CACHE, accountId, token);
  }

  @Override
  public void setOnlineSession(String rawId, String accountId) {
    redisTemplate.opsForSet().add(rawId, accountId);
    redisTemplate.expire(rawId, 1, TimeUnit.HOURS);
  }

  @Override
  public void setOfflineSession(String rawId, String accountId) {
    redisTemplate.opsForSet().remove(rawId, accountId);
  }

  @Override
  public Set<Object> getOnlineSessions(String rawId) {
    return redisTemplate.opsForSet().members(rawId);
  }
}
