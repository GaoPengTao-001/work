package com.example.mywork.controller;


import com.alibaba.fastjson.JSON;
import com.example.mywork.entity.User;
import com.example.mywork.service.RedisService;
import com.example.mywork.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    public static final Logger log = LoggerFactory.getLogger(RedisController.class);

    @Resource
    private UserService userService;

    @Resource
    RedisService redisService;

    @RequestMapping(value = "/hello/{id}")
    public String hello(@PathVariable(value = "id") String id) {
        try {
            //查询缓存中是否存在
            boolean hasKey = redisService.exists(id);
            String str = "";
            if (hasKey) {
                //获取缓存
                Object object = redisService.get(id);
                log.info("从缓存获取的数据" + object);
                str = object.toString();
            } else {
                //从数据库中获取信息
                log.info("从数据库中获取数据");
                User user = userService.Sel(id);
                //数据插入缓存（set中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
                str = JSON.toJSONString(user);
                redisService.set(id, str, 10L, TimeUnit.MINUTES);
                log.info("数据插入缓存" + str);
            }
            return str;
        } catch (Exception e) {
            log.error("RedisController.hello 异常", e);
        }
        return null;
    }


    @RequestMapping(value = "/hmSet/{id}")
    public String hmSet(@PathVariable(value = "id") String id) {
        try {
            String hmKey = "hm" + id;
            //从数据库中获取信息
            log.info("从数据库中获取数据");
            User user = userService.Sel(id);
            String str = JSON.toJSONString(user);
            //数据插入缓存（set中的参数含义：key值，user对象，缓存存在时间10（long类型），时间单位）
            Map<String, String> map = JSON.parseObject(str, HashMap.class);
            for (Map.Entry<String, String> entrySet : map.entrySet()) {
                redisService.hmSet(hmKey, entrySet.getKey(), String.valueOf(entrySet.getValue()));
            }
            log.info("数据插入缓存" + str);
            return "保存hash成功，key=" + hmKey;
        } catch (Exception e) {
            log.error("RedisController.hmSet 异常", e);
        }
        return null;
    }


    @RequestMapping(value = "/hmGet/{key}/{hashKey}")
    public String hmGet(@PathVariable(value = "key") String key, @PathVariable(value = "hashKey") String hashKey) {
        try {
            //查询缓存中是否存在
            boolean hasKey = redisService.exists(key);

            String str = "";
            if (hasKey) {
                //获取缓存
                Object object = redisService.hmGet(key, hashKey);
                log.info("从缓存获取的数据" + object);
                str = object.toString();
            } else {
                str = "没有找到hash，key=" + key;
            }
            return str;
        } catch (Exception e) {
            log.error("RedisController.hmGet 异常", e);
        }
        return null;
    }

    @RequestMapping(value = "/lSet/{key}/{value}")
    public String lSet(@PathVariable(value = "key") String key, @PathVariable(value = "value") String value) {
        try {
            String lKey = "l" + key;
            redisService.lPush(lKey, value);
            return "保存list成功，key=" + lKey;
        } catch (Exception e) {
            log.error("RedisController.lSet 异常", e);
        }
        return null;
    }

    @RequestMapping(value = "/lGet/{key}")
    public String lGet(@PathVariable(value = "key") String key) {
        try {
            //查询缓存中是否存在
            boolean hasKey = redisService.exists(key);

            String str = "";
            if (hasKey) {
                //获取缓存
                Object object = redisService.lRange(key, 0, 100);
                log.info("从缓存获取的数据" + object);
                str = object.toString();
            } else {
                str = "没有找到list，key=" + key;
            }
            return str;
        } catch (Exception e) {
            log.error("RedisController.lGet 异常", e);
        }
        return null;
    }

    @RequestMapping(value = "/sAdd/{key}/{value}")
    public String sAdd(@PathVariable(value = "key") String key, @PathVariable(value = "value") String value) {
        try {
            String lKey = "s" + key;
            redisService.add(lKey, value);
            return "保存set成功，key=" + lKey;
        } catch (Exception e) {
            log.error("RedisController.sAdd 异常", e);
        }
        return null;
    }

    @RequestMapping(value = "/sGet/{key}")
    public String sGet(@PathVariable(value = "key") String key) {
        try {
            //查询缓存中是否存在
            boolean hasKey = redisService.exists(key);

            String str = "";
            if (hasKey) {
                //获取缓存
                Object object = redisService.setMembers(key);
                log.info("从缓存获取的数据" + object);
                str = object.toString();
            } else {
                str = "没有找到set，key=" + key;
            }
            return str;
        } catch (Exception e) {
            log.error("RedisController.sGet 异常", e);
        }
        return null;
    }

    @RequestMapping(value = "/zsAdd/{key}/{value}/{score}")
    public String zsAdd(@PathVariable(value = "key") String key,
                        @PathVariable(value = "value") String value,
                        @PathVariable(value = "score") double score) {
        try {
            String lKey = "zs" + key;
            redisService.zAdd(lKey, value, score);
            return "保存zset成功，key=" + lKey;
        } catch (Exception e) {
            log.error("RedisController.zsAdd 异常", e);
        }
        return null;
    }

    @RequestMapping(value = "/zsGet/{key}/{score}/{score1}")
    public String zsGet(@PathVariable(value = "key") String key,
                        @PathVariable(value = "score") double score,
                        @PathVariable(value = "score1") double score1) {
        try {
            //查询缓存中是否存在
            boolean hasKey = redisService.exists(key);

            String str = "";
            if (hasKey) {
                //获取缓存
                Object object = redisService.rangeByScore(key, score, score1);
                log.info("从缓存获取的数据" + object);
                str = object.toString();
            } else {
                str = "没有找到zset，key=" + key;
            }
            return str;
        } catch (Exception e) {
            log.error("RedisController.zsGet 异常", e);
        }
        return null;
    }

    @RequestMapping(value = "/expire/{key}/{mill}")
    public String expire(@PathVariable(value = "key") String key,
                         @PathVariable(value = "mill") long mill) {
        try {
            //查询缓存中是否存在
            boolean hasKey = redisService.exists(key);
            if (hasKey) {
                boolean flag = redisService.expire(key, mill);
                if (flag) {
                    return "已设置过期时间";
                } else {
                    return "设置过期时间失败";
                }
            } else {
                return "没有找到key，key=" + key;
            }
        } catch (Exception e) {
            log.error("RedisController.expire 异常", e);
        }
        return null;
    }
}
