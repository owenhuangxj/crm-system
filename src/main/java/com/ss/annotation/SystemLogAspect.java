package com.ss.annotation;



import com.ss.entity.Log;
import com.ss.entity.SysUser;
import com.ss.entity.User;
import com.ss.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;


@Aspect
@Component
public class SystemLogAspect {

    @Autowired
    private LogService ls;

    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);


    //Controller层切点
    @Pointcut("execution (* com.ss.annotation..*.*(..))")
    public void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint){
        /*用isDebugEnabled方法判断下是能提升性能的*/
        if (logger.isInfoEnabled()) {
            logger.info("before " + joinPoint);
        }
    }

    //配置controller环绕通知,使用在方法aspect()上注册的切入点
  /*  @Around("controllerAspect()")
    public void around(JoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        try {
            ((ProceedingJoinPoint) joinPoint).proceed();
            long end = System.currentTimeMillis();
            if (logger.isInfoEnabled()) {
                logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
            }
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            if (logger.isInfoEnabled()) {
                logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
            }
        }
    }*/

    /**
     * 后置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @After("controllerAspect()")
    public void after(JoinPoint joinPoint) {

        /* HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
          HttpSession session = request.getSession();  */
        //读取session中的用户
        // User user = (User) session.getAttribute("user");

        SysUser user = new SysUser();
        user.setUid(user.toString());
        System.out.println("userid会打印什么 : " + user.getUid());
        try {

            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String logType = "";
            String logContent = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        logType = method.getAnnotation(SystemControllerLog.class).actionType();
                        logContent = method.getAnnotation(SystemControllerLog.class).descrption();
                        break;
                    }
                }
            }
            //*========控制台输出=========*//
            System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            System.out.println("方法描述:" + logContent);
            System.out.println("请求人:" + user.getNick());
            System.out.println("请求账号:" + user.getEmail());
            //*========数据库日志=========*//
            Log log = new Log();
            log.setUId(user.toString());
            log.setLogType(logType);
            log.setLogContent(logContent);
            log.setLogErrorCode(HttpStatus.OK.toString());
            log.setLogDate(new Date());
            //保存数据库
            ls.getInsertLog();
        } catch (Exception e) {
            //记录本地异常日志
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    //配置后置返回通知,使用在方法aspect()上注册的切入点
    @AfterReturning("controllerAspect()")
    public void afterReturn(JoinPoint joinPoint) {
        if (logger.isInfoEnabled()) {
            logger.info("afterReturn " + joinPoint);
        }
    }

    /**
     * 异常通知 用于拦截记录异常日志
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
                 /*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
         HttpSession session = request.getSession();
         //读取session中的用户
         User user = (User) session.getAttribute(WebConstants.CURRENT_USER);
         //获取请求ip
         String ip = request.getRemoteAddr(); */

        SysUser user = new SysUser();
        user.setUid(user.toString());

        String logResult = "";
        try {

            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String logType = "";
            String logContent = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        logType = method.getAnnotation(SystemControllerLog.class).actionType();
                        logContent = method.getAnnotation(SystemControllerLog.class).descrption();
                        break;
                    }
                }
            }
            /*========控制台输出=========*/
            System.out.println("异常代码:" + e.getClass().getName());
            System.out.println("异常信息:" + e.getMessage());
            System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            System.out.println("方法描述:" + logContent);
            System.out.println("请求人:" + user.getNick());
            System.out.println("请求账号:" + user.getEmail());
            /*==========数据库日志=========*/
            Log log = new Log();
            log.setUId(user.toString());
            log.setLogType(logType);
            log.setLogContent(logContent);
            log.setLogErrorCode(e.getMessage());
            log.setLogDate(new Date());
            //保存数据库
            ls.getInsertLog();
        } catch (Exception ex) {
            //记录本地异常日志
            logger.error("异常信息:{}", ex.getMessage());
        }
        /*==========记录本地异常日志==========*/
        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage());

    }

}