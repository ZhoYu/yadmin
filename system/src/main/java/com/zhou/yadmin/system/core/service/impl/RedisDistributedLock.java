package com.zhou.yadmin.system.core.service.impl;// package com.zhou.yadmin.system.core.service.impl;
//
// import java.nio.charset.StandardCharsets;
// import java.util.Arrays;
// import java.util.List;
// import java.util.UUID;
//
// import AbstractDistributedLock;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.connection.RedisStringCommands;
// import org.springframework.data.redis.connection.ReturnType;
// import org.springframework.data.redis.core.RedisCallback;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.core.script.DefaultRedisScript;
// import org.springframework.data.redis.core.types.Expiration;
// import org.springframework.retry.annotation.Backoff;
// import org.springframework.retry.annotation.Retryable;
// import org.springframework.stereotype.Component;
//
// @Component
// public class RedisDistributedLock extends AbstractDistributedLock
// {
//     @Autowired
//     private RedisTemplate<String, Object> redisTemplate;
//
//     private final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);
//
//     private ThreadLocal<String> lockFlag = new ThreadLocal<>();
//
//     public static final String UNLOCK_LUA;
//
//     //定义获取锁的lua脚本
//     private final static DefaultRedisScript<Long> LOCK_LUA_SCRIPT = new DefaultRedisScript<>(
//       "if redis.call(\"setnx\", KEYS[1], KEYS[2]) == 1 then return redis.call(\"pexpire\", KEYS[1], KEYS[3]) else return 0 end"
//       , Long.class
//     );
//
//     //定义释放锁的lua脚本
//     private final static DefaultRedisScript<Long> UNLOCK_LUA_SCRIPT = new DefaultRedisScript<>(
//       "if redis.call(\"get\",KEYS[1]) == KEYS[2] then return redis.call(\"del\",KEYS[1]) else return -1 end"
//       , Long.class
//     );
//
//     static
//     {
//         UNLOCK_LUA =
//           "if redis.call(\"get\",KEYS[1]) == ARGV[1] " + "then " + "    return redis.call(\"del\",KEYS[1]) " + "else " + "    return 0 " + "end ";
//     }
//
//     @Override
//     public boolean lock(String key, long expire, int retryTimes, long sleepMillis)
//     {
//         boolean result = setRedis(key, expire);
//         // 如果获取锁失败，按照传入的重试次数进行重试
//         while ((!result) && retryTimes-- > 0)
//         {
//             try
//             {
//                 logger.debug("lock failed, retrying..." + retryTimes);
//                 Thread.sleep(sleepMillis);
//             }
//             catch (InterruptedException e)
//             {
//                 return false;
//             }
//             result = setRedis(key, expire);
//         }
//         return result;
//     }
//
//     /**
//      * value：抛出指定异常才会重试
//      * include：和value一样，默认为空，当exclude也为空时，默认所以异常
//      * exclude：指定不处理的异常
//      * maxAttempts：最大重试次数，默认3次
//      * backoff：重试等待策略，默认使用@Backoff，@Backoff的value默认为1000L，我们设置为2000L；multiplier（指定延迟倍数）默认为0，表示固定暂停1秒后进行重试，如果把multiplier设置为1.5，则第一次重试为2秒，第二次为3秒，第三次为4.5秒。
//      *
//      * @return
//      */
//     @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000L, multiplier = 1.5))
//     public boolean retryLock(String key, long expire, int retryTimes, long sleepMillis)
//     {
//         final String requestId = this.getRequestId();
//         logger.debug("lock :::: redisKey = {} requestid = {}", key, requestId);
//         //组装lua脚本参数
//         List<String> keys = Arrays.asList(key, requestId, String.valueOf(expire));
//         //执行脚本
//         Long result = redisTemplate.execute(LOCK_LUA_SCRIPT, keys);
//     }
//
//     private boolean setRedis(final String key, final long expire)
//     {
//         try
//         {
//             RedisCallback<Boolean> callback = (connection) -> {
//                 String uuid = UUID.randomUUID().toString();
//                 lockFlag.set(uuid);
//                 return connection.set(key.getBytes(StandardCharsets.UTF_8), uuid.getBytes(StandardCharsets.UTF_8), Expiration.milliseconds(expire),
//                   RedisStringCommands.SetOption.SET_IF_ABSENT);
//             };
//             return redisTemplate.execute(callback);
//         }
//         catch (Exception e)
//         {
//             logger.error("redis lock error.", e);
//         }
//         return false;
//     }
//
//     @Override
//     public boolean releaseLock(String key)
//     {
//         // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
//         try
//         {
//             RedisCallback<Boolean> callback = (connection) -> {
//                 String value = lockFlag.get();
//                 return connection
//                   .eval(UNLOCK_LUA.getBytes(), ReturnType.BOOLEAN, 1, key.getBytes(StandardCharsets.UTF_8), value.getBytes(StandardCharsets.UTF_8));
//             };
//             return redisTemplate.execute(callback);
//         }
//         catch (Exception e)
//         {
//             logger.error("release lock occured an exception", e);
//         }
//         finally
//         {
//             // 清除掉ThreadLocal中的数据，避免内存溢出
//             lockFlag.remove();
//         }
//         return false;
//     }
//
//
//     /**
//      * 加锁
//      * @param key Key
//      * @param timeout 过期时间
//      * @param retryTimes 重试次数
//      * @return
//      */
//     public boolean lock(String key, long timeout, int retryTimes) {
//         try {
//             final String redisKey = this.getRedisKey(key);
//             final String requestId = this.getRequestId();
//             logger.debug("lock :::: redisKey = " + redisKey + " requestid = " + requestId);
//             //组装lua脚本参数
//             List<String> keys = Arrays.asList(redisKey, requestId, String.valueOf(timeout));
//             //执行脚本
//             Long result = redisTemplate.execute(LOCK_LUA_SCRIPT, keys);
//             //存储本地变量
//             if(!StringUtils.isEmpty(result) && result == LOCK_SUCCESS) {
//                 localRequestIds.set(requestId);
//                 localKeys.set(redisKey);
//                 logger.info("success to acquire lock:" + Thread.currentThread().getName() + ", Status code reply:" + result);
//                 return true;
//             } else if (retryTimes == 0) {
//                 //重试次数为0直接返回失败
//                 return false;
//             } else {
//                 //重试获取锁
//                 logger.info("retry to acquire lock:" + Thread.currentThread().getName() + ", Status code reply:" + result);
//                 int count = 0;
//                 while(true) {
//                     try {
//                         //休眠一定时间后再获取锁，这里时间可以通过外部设置
//                         Thread.sleep(100);
//                         result = redisTemplate.execute(LOCK_LUA_SCRIPT, keys);
//                         if(!StringUtils.isEmpty(result) && result == LOCK_SUCCESS) {
//                             localRequestIds.set(requestId);
//                             localKeys.set(redisKey);
//                             logger.info("success to acquire lock:" + Thread.currentThread().getName() + ", Status code reply:" + result);
//                             return true;
//                         } else {
//                             count++;
//                             if (retryTimes == count) {
//                                 logger.info("fail to acquire lock for " + Thread.currentThread().getName() + ", Status code reply:" + result);
//                                 return false;
//                             } else {
//                                 logger.warn(count + " times try to acquire lock for " + Thread.currentThread().getName() + ", Status code reply:" + result);
//                                 continue;
//                             }
//                         }
//                     } catch (Exception e) {
//                         logger.error("acquire redis occured an exception:" + Thread.currentThread().getName(), e);
//                         break;
//                     }
//                 }
//             }
//         } catch (Exception e1) {
//             logger.error("acquire redis occured an exception:" + Thread.currentThread().getName(), e1);
//         }
//         return false;
//     }
//     /**
//      * 获取RedisKey
//      * @param key 原始KEY，如果为空，自动生成随机KEY
//      * @return
//      */
//     private String getRedisKey(String key) {
//         //如果Key为空且线程已经保存，直接用，异常保护
//         if (StringUtils.isEmpty(key) && !StringUtils.isEmpty(localKeys.get())) {
//             return localKeys.get();
//         }
//         //如果都是空那就抛出异常
//         if (StringUtils.isEmpty(key) && StringUtils.isEmpty(localKeys.get())) {
//             throw new RuntimeException("key is null");
//         }
//         return LOCK_PREFIX + key;
//     }
//
//     /**
//      * 获取随机请求ID
//      * @return
//      */
//     private String getRequestId() {
//         return UUID.randomUUID().toString();
//     }
//
//     /**
//      * 释放KEY
//      * @param key
//      * @return
//      */
//     public boolean unlock(String key) {
//         try {
//             String localKey = localKeys.get();
//             //如果本地线程没有KEY，说明还没加锁，不能释放
//             if(StringUtils.isEmpty(localKey)) {
//                 logger.error("release lock occured an error: lock key not found");
//                 return false;
//             }
//             String redisKey = getRedisKey(key);
//             //判断KEY是否正确，不能释放其他线程的KEY
//             if(!StringUtils.isEmpty(localKey) && !localKey.equals(redisKey)) {
//                 logger.error("release lock occured an error: illegal key:" + key);
//                 return false;
//             }
//             //组装lua脚本参数
//             List<String> keys = Arrays.asList(redisKey, localRequestIds.get());
//             logger.debug("unlock :::: redisKey = " + redisKey + " requestid = " + localRequestIds.get());
//             // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
//             Long result = redisTemplate.execute(UNLOCK_LUA_SCRIPT, keys);
//             //如果这里抛异常，后续锁无法释放
//             if (!StringUtils.isEmpty(result) && result == RELEASE_SUCCESS) {
//                 logger.info("release lock success:" + Thread.currentThread().getName() + ", Status code reply=" + result);
//                 return true;
//             } else if (!StringUtils.isEmpty(result) && result == LOCK_EXPIRED) {
//                 //返回-1说明获取到的KEY值与requestId不一致或者KEY不存在，可能已经过期或被其他线程加锁
//                 // 一般发生在key的过期时间短于业务处理时间，属于正常可接受情况
//                 logger.warn("release lock exception:" + Thread.currentThread().getName() + ", key has expired or released. Status code reply=" + result);
//             } else {
//                 //其他情况，一般是删除KEY失败，返回0
//                 logger.error("release lock failed:" + Thread.currentThread().getName() + ", del key failed. Status code reply=" + result);
//             }
//         } catch (Exception e) {
//             logger.error("release lock occured an exception", e);
//         } finally {
//             //清除本地变量
//             this.clean();
//         }
//         return false;
//     }
//
//     /**
//      * 清除本地线程变量，防止内存泄露
//      */
//     private void clean() {
//         localRequestIds.remove();
//         localKeys.remove();
//     }
// }
