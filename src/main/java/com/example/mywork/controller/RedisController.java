package com.example.mywork.controller;


import com.alibaba.fastjson.JSON;
import com.example.mywork.service.RedisService;
import com.example.mywork.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/redis")
public class RedisController {

    public static final Logger log = LoggerFactory.getLogger(RedisController.class);

    @Resource
    RedisService redisService;

    @PostMapping(value = "/saveStr")
    public String saveStr(@RequestBody Map<String, String> map) {
        try {
            boolean isSave = redisService.set(map.get("key"), map.get("value"));
            if (isSave) {
                return map.toString() + ",保存成功";
            } else {
                return map.toString() + ",保存失败";
            }
        } catch (Exception e) {
            log.error("RedisController.saveStr 异常", e);
        }
        return null;
    }

    @PostMapping(value = "/getStr")
    public String getStr(@RequestBody Map<String, String> map) {
        try {
            String key = map.get("key");
            boolean hasKey = redisService.exists(key);
            if (hasKey) {
                return redisService.get(key).toString();
            } else {
                return key + "没有找到";
            }
        } catch (Exception e) {
            log.error("RedisController.getStr 异常", e);
        }
        return null;
    }

    @PostMapping(value = "/hmSet")
    public String hmSet(@RequestBody Map<String, Object> map) {
        try {
            String key = (String) map.get("key");
            Map<String, String> value = (Map<String, String>) map.get("value");
            for (Map.Entry<String, String> entrySet : value.entrySet()) {
                redisService.hmSet(key, entrySet.getKey(), String.valueOf(entrySet.getValue()));
            }
            log.info("数据插入缓存" + map.toString());
            return "保存hash成功，data:" + JSON.toJSONString(map);
        } catch (Exception e) {
            log.error("RedisController.hmSet 异常", e);
        }
        return null;
    }

    @PostMapping(value = "/hmGet")
    public String hmGet(@RequestBody Map<String, String> map) {
        try {
            String key = map.get("key");
            String colName = map.get("colName");
            //查询缓存中是否存在
            boolean hasKey = redisService.exists(key);
            String str = "";
            if (hasKey) {
                //获取缓存
                Object object = redisService.hmGet(key, colName);
                log.info("从缓存获取的数据" + object);
                str = JSON.toJSONString(object);
            } else {
                str = "没有找到hash，key=" + key;
            }
            return str;
        } catch (Exception e) {
            log.error("RedisController.hmGet 异常", e);
        }
        return null;
    }

    @PostMapping(value = "/lSet")
    public String lSet(@RequestBody Map<String, Object> map) {
        try {
            String key = (String) map.get("key");
            List<String> value = (List<String>) map.get("value");
            if (!CollectionUtils.isEmpty(value)) {
                for (String val : value) {
                    redisService.lPush(key, val);
                }
            }
            return "保存list成功，key=" + key;
        } catch (Exception e) {
            log.error("RedisController.lSet 异常", e);
        }
        return null;
    }

    @PostMapping(value = "/lGet")
    public String lGet(@RequestBody Map<String, String> map) {
        try {
            String key = map.get("key");
            //查询缓存中是否存在
            boolean hasKey = redisService.exists(key);
            String str = "";
            if (hasKey) {
                //获取缓存
                Object object = redisService.lRange(key, 0, 100);
                log.info("从缓存获取的数据" + object);
                str = JSON.toJSONString(object);
            } else {
                str = "没有找到list，key=" + key;
            }
            return str;
        } catch (Exception e) {
            log.error("RedisController.lGet 异常", e);
        }
        return null;
    }

    @PostMapping(value = "/sSet")
    public String sSet(@RequestBody Map<String, Object> map) {
        try {
            String key = (String) map.get("key");
            List<String> value = (List<String>) map.get("value");
            if (!CollectionUtils.isEmpty(value)) {
                value.stream().forEach(val -> redisService.add(key, val));
            }
            return "保存set成功，key=" + key;
        } catch (Exception e) {
            log.error("RedisController.sAdd 异常", e);
        }
        return null;
    }

    @PostMapping(value = "/sGet")
    public String sGet(@RequestBody Map<String, String> map) {
        try {
            String key = map.get("key");
            //查询缓存中是否存在
            boolean hasKey = redisService.exists(key);
            String str = "";
            if (hasKey) {
                //获取缓存
                Object object = redisService.setMembers(key);
                log.info("从缓存获取的数据" + object);
                str = JSON.toJSONString(object);
            } else {
                str = "没有找到set，key=" + key;
            }
            return str;
        } catch (Exception e) {
            log.error("RedisController.sGet 异常", e);
        }
        return null;
    }

    @PostMapping(value = "/zsAdd")
    public String zsAdd(@RequestBody Map<String, Object> map) {
        try {
            String key = (String) map.get("key");
            List<Map> val = (List<Map>) map.get("value");
            if (!CollectionUtils.isEmpty(val)) {
                val.stream().forEach(item -> {
                    String value = (String) item.get("value");
                    double score = Double.valueOf((String) item.get("score"));
                    redisService.zAdd(key, value, score);
                });
            }
            return "保存zset成功，key=" + key;
        } catch (Exception e) {
            log.error("RedisController.zsAdd 异常", e);
        }
        return null;
    }

    @PostMapping(value = "/zsGet")
    public String zsGet(@RequestBody Map<String, String> map) {
        try {
            String key = map.get("key");
            double score1 = Double.valueOf(map.get("score1"));
            double score2 = Double.valueOf(map.get("score2"));
            //查询缓存中是否存在
            boolean hasKey = redisService.exists(key);
            String str = "";
            if (hasKey) {
                //获取缓存
                Object object = redisService.rangeByScore(key, score1, score2);
                log.info("从缓存获取的数据" + object);
                str = JSON.toJSONString(object);
            } else {
                str = "没有找到zset，key=" + key;
            }
            return str;
        } catch (Exception e) {
            log.error("RedisController.zsGet 异常", e);
        }
        return null;
    }

    @PostMapping(value = "/expire")
    public String expire(@RequestBody Map<String, String> map) {
        try {
            String key = map.get("key");
            Integer mill = Integer.valueOf(map.get("mill"));
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
