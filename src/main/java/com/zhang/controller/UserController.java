package com.zhang.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhang.Utils.ValidateCodeUtils;
import com.zhang.common.R;
import com.zhang.entity.User;
import com.zhang.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){

        //获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)){
            //生成4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
            //调用阿里云短信服务
            //SMSUtils.sendMessage("瑞吉外卖","",phone,code);
            //将验证码保存到Session中
            session.setAttribute(phone,code);

            //将验证码缓存到Redis中  并且设置有效期为5分钟
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

            return R.success("验证码发送成功");

        }

    return R.error("发送失败");

    }

    /**
     * 移动端登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){

        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从Session中获取验证码
        //Object codeInSession = session.getAttribute(phone);

        //从Redis中获取缓存的验证码
        Object codeInSession = redisTemplate.opsForValue().get(phone);

        //验证码比对
        if (codeInSession != null && codeInSession.equals(code)){
            //比对成功，登录

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                //判断是否新用户，是则自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());

            //如果用户登录成功 删除Redis中缓存的验证码
            redisTemplate.delete(phone);

            return R.success(user);

        }

        //登录失败
        return R.error("登录失败");


    }


}
