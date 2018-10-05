package com.lfz;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lfz.shiro.MyAuthcFilter;
import com.lfz.shiro.MyRealm;
import com.lfz.shiro.MyRedisSessionDao;
import com.lfz.shiro.MyRolesFilter;
import com.lfz.shiro.MySessionManager;
import com.lfz.shiro.MyUserFilter;
import com.lfz.websocket.ChatWebSocketHandler;
import com.lfz.websocket.MyHandshakeInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import javax.jms.ConnectionFactory;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Author: fuzhi.lai
 * Date: 2018/9/8 下午12:29
 * Create by Intellij idea
 */
/*
                       _oo0oo_
                      o8888888o
                      88" . "88
                      (| -_- |)
                      0\  =  /0
                    ___/`---'\___
                  .' \\|     |// '.
                 / \\|||  :  |||// \
                / _||||| -:- |||||- \
               |   | \\\  -  /// |   |
               | \_|  ''\---/''  |_/ |
               \  .-\__  '-'  ___/-. /
             ___'. .'  /--.--\  `. .'___
          ."" '<  `.___\_<|>_/___.' >' "".
         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
         \  \ `_.   \_ __\ /__ _/   .-` /  /
     =====`-.____`.___ \_____/___.-`___.-'=====
                       `=---='
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

               佛祖保佑         永无BUG
*/
@Slf4j
@SpringBootApplication
//springboot默认开启事务，配了数据源默认会有一个DataSourceTransactionManager，TransactionAutoConfiguration中就会开启事务
//@EnableTransactionManagement
//设置mybatis扫描dao接口所在的包名
@MapperScan("com.lfz.dao")
// 使能@WebServlet，@WebFilter和@WebListener，使用外置容器不需要配，使用容器自身的发现机制
@ServletComponentScan
//全面接管mvc配置，不使用boot的自动配置，一般不需要加这个注解
//@EnableWebMvc
//使能spring的定时任务
@EnableScheduling
//使能spring的websocket，不使用tomcat的websocket实现，依赖容器的做法不好
@EnableWebSocket
//使能spring的cache
//@EnableCaching
//开启异步
@EnableAsync
//开启dubbo，会将Service标注的类都交给spring管理，也就是不需要再加Component注解了，默认开启
//@EnableDubbo(scanBasePackages = "com.lfz.service")
public class BootStrap extends SpringBootServletInitializer
        implements WebMvcConfigurer, SchedulingConfigurer, AsyncConfigurer, WebSocketConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(BootStrap.class, args);
    }

    /*********************************************************************************************************/
    //WebSocketConfigurer配置
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(1024 * 1024 * 10);
        container.setMaxBinaryMessageBufferSize(1024 * 1024 * 10);
        return container;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(echoWebSocketHandler(), "/api/ws/chat")
                .addInterceptors(new MyHandshakeInterceptor())
                .setAllowedOrigins("*");

//        registry.addHandler(echoWebSocketHandler(), "/api/ws/chat");
    }

    //@Bean
    //每个连接独享一个EchoWebSocketHandler实例
    public WebSocketHandler perEchoWebSocketHandler() {
        return new PerConnectionWebSocketHandler(ChatWebSocketHandler.class);
    }

    @Bean
    public ChatWebSocketHandler echoWebSocketHandler() {
        return new ChatWebSocketHandler();
    }

    /*********************************************************************************************************/
    //SpringBootServletInitializer 使用外置tomcat时的必须配置，不用外置tomcat也可以先配置上
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BootStrap.class);
    }

    /*********************************************************************************************************/
    //WebMvcConfigurer 配置视图和spring拦截器
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/static/html/index.html");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        新加拦截器，指定拦截路径和不拦截路径，boot做好了静态资源映射，不用管静态资源
//        registry.addInterceptor().addPathPatterns().excludePathPatterns();
    }

    /*********************************************************************************************************/
    //SchedulingConfigurer 设置定时任务线程池
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(scheduledExecutor());
    }

    @Bean
    public ThreadPoolTaskScheduler scheduledExecutor() {
        ThreadPoolTaskScheduler scheduledTaskThreadPool = new ThreadPoolTaskScheduler();
        scheduledTaskThreadPool.setPoolSize(10);
        scheduledTaskThreadPool.setThreadNamePrefix("scheduledTask_");
        return scheduledTaskThreadPool;
    }

    /*********************************************************************************************************/
    //AsyncConfigurer 设置异步任务线程池
    //EnableAsync注释中有写，使用@bean让spring管理taskExecutor就不用initial了
    @Bean
    @Override
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor asyncTaskThreadPool = new ThreadPoolTaskExecutor();
        asyncTaskThreadPool.setCorePoolSize(10);
        asyncTaskThreadPool.setMaxPoolSize(10);
        asyncTaskThreadPool.setQueueCapacity(100);
        asyncTaskThreadPool.setThreadNamePrefix("asyncTask_");
        asyncTaskThreadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        return asyncTaskThreadPool;
    }

    //配置异常处理类，异步方法里面直接try住，自己处理异常，一般不指定这个
//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//
//    }

    /*********************************************************************************************************/
    //redisCluster设置
    @Bean(destroyMethod = "close")
    public JedisCluster getJedisCluster() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲数
        jedisPoolConfig.setMaxIdle(10);
        // 连接池的最大数据库连接数
        jedisPoolConfig.setMaxTotal(30);
        // 最大建立连接等待时间
        jedisPoolConfig.setMaxWaitMillis(1500);
        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(1800000);
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(3);
        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
        // 连接空闲多久后释放，当空闲时间大于该值且空闲连接大于最大空闲连接数时释放
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(10000);
        // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(true);
        // 在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(true);
        // 连接耗尽时是否阻塞，false报异常，true阻塞直到超时，默认true
        jedisPoolConfig.setBlockWhenExhausted(false);
        Set<HostAndPort> nodeSet = Sets.newHashSet();
        nodeSet.add(new HostAndPort("127.0.0.1", 6381));
        nodeSet.add(new HostAndPort("127.0.0.1", 6382));
        nodeSet.add(new HostAndPort("127.0.0.1", 6383));
        nodeSet.add(new HostAndPort("127.0.0.1", 6384));
        nodeSet.add(new HostAndPort("127.0.0.1", 6385));
        nodeSet.add(new HostAndPort("127.0.0.1", 6386));
        return new JedisCluster(nodeSet, 2000, 100, jedisPoolConfig);
    }

    /*********************************************************************************************************/
    //shiro设置
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean<DelegatingFilterProxy> registration = new FilterRegistrationBean<>();
        registration.setFilter(new DelegatingFilterProxy());
        registration.addUrlPatterns("/api/*");
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setName("shiroFilter");//名字必须和ShiroFilterFactoryBean一样
        return registration;
    }

    @Bean//@Bean的默认name是方法名也就是shiroFilter
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        //设置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/api/addBlog", "myRoles[admin]");
//        filterChainDefinitionMap.put("/api/delBlogs", "myRoles[admin]");
        filterChainDefinitionMap.put("/api/userList", "myRoles[admin]");
        filterChainDefinitionMap.put("/api/delUsers", "myAuthc[admin],myRoles[admin]");
        filterChainDefinitionMap.put("/api/file", "myRoles[special]");
        filterChainDefinitionMap.put("/api/logout", "logout");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //设置自定义filter
        Map<String, Filter> filters = Maps.newHashMap();
        filters.put("myRoles", new MyRolesFilter());
        filters.put("myUser", new MyUserFilter());
        filters.put("myAuthc", new MyAuthcFilter());
        bean.setFilters(filters);
        return bean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置authenticator
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);
        //设置realm
        List<Realm> realms = Lists.newArrayList(myRealm());
        securityManager.setRealms(realms);
        //设置sessionManager
        MySessionManager sessionManager = new MySessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        sessionManager.setSessionDAO(myRedisSessionDao());
        sessionManager.setSessionIdCookie(new SimpleCookie("shiroCookie"));
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        securityManager.setSessionManager(sessionManager);
        //设置rememberMeManager
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        SimpleCookie remeberMeCookie = new SimpleCookie("rememberMe");
        remeberMeCookie.setMaxAge(86400);
        remeberMeCookie.setHttpOnly(true);
        rememberMeManager.setCookie(remeberMeCookie);
        securityManager.setRememberMeManager(rememberMeManager);
        //设置缓存，默认缓存用户授权信息，认证信息不缓存
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        return securityManager;
    }

    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myRealm;
    }

    @Bean
    public MyRedisSessionDao myRedisSessionDao() {
        return new MyRedisSessionDao();
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(5);
        return hashedCredentialsMatcher;
    }

    /*********************************************************************************************************/
    //activemq的queue和topic要使用不同的factory，JmsAnnotationDrivenConfiguration会根据
    //@ConditionalOnMissingBean(name = "jmsListenerContainerFactory")创建默认的factory
    //所以自己创建的factory指定名字为jmsListenerContainerFactory，不用默认的
    @Bean({"jmsListenerContainerFactory", "queueFactory"})
    public DefaultJmsListenerContainerFactory queueFactory(
            DefaultJmsListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean("topicFactory")
    public DefaultJmsListenerContainerFactory topicFactory(
            DefaultJmsListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }

    /*********************************************************************************************************/
    // 设置内嵌tomcat，我还是习惯在yml配置，下面那种方式设置的不全，是内嵌服务器的通用配置
    // implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
//    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setPort(9000);
        return factory;
    }
    /*********************************************************************************************************/
    // 使用内嵌tomcat的websocket实现时检测endpoint注解
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
}


