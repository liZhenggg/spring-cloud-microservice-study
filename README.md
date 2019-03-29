# spring-cloud-study

组件介绍：
Spring Boot Actuator：是Spring Boot官方提供的监控组件
服务发现：服务消费者找到服务提供者的机制，又称服务注册。


Eureka：Netflix开源的服务发现组件，本身是一个基于REST的服务，包含Server和Client两部分，Spring Cloud将它集成在子项目Spring Cloud Netflix中。
Eureka Client：负责将这个服务的信息注册到Eureka Server中
Eureka Server：提供服务发现的能力，各个微服务启动时，会向Eureka Server注册自己的信息（例如IP、端口、微服务名称等），Eureka Server会存储这些信息；


Ribbon：Netflix发布的客户端负载均衡组件。当Ribbon与Eureka配合使用时，Ribbon可自动从Eureka Server获取服务提供者地址列表，并基于负载均衡算法，选择其中一个服务提供者实例。
负载均衡规则是Ribbon的核心，Ribbon内置的负载均衡规则：AvailabilityFilteringRule，BestAvailableRule，RandomRule，ResponseTimeWeightedRule，RetryRule，RoundRobinRule，WeightedResponseTimeRule，ZoneAvoidanceRule。
如需自定义负载均衡规则，只需实现IRule 接口或继承AbstractLoadBalancerRule 、PredicateBasedRule即可。


Feign是Netflix开发的声明式、模板化的HTTP客户端，可帮助我们更加便捷、优雅地调用HTTP API。
在Spring Cloud中，使用Feign只需创建接口，并在接口上添加注解@FeignClient，在2启动类上添加注解@EnableFeignClients 

Feign配置自定义:
方式一、代码配置方式
使用注解@FeignClient 的configuration 属性，指定一个类，即可实现Feign配置自定义。
Feign有四种日志级别：
NONE【性能最佳，适用于生产】：不记录任何日志（默认值）。
BASIC【适用于生产环境追踪问题】：仅记录请求方法、URL、响应状态代码以及执行时间。
HEADERS：记录BASIC级别的基础上，记录请求和响应的header。
FULL【比较适用于开发及测试环境定位问题】：记录请求和响应的header、body和元数据。
方法二、属性配置方式【Edgware开始提供】

配置优先级
若代码和属性同时配置，则使用配置属性的优先级更高，配置属性配置的方式将会覆盖Java代码配置。如果你想修改代码配置方式的优先级，可使用如下属性：feign.client.default-to-properties=false

压缩
对请求或响应进行压缩:
feign.compression.request.enabled=true
feign.compression.response.enabled=true

常见问题：
一、FeignClient接口如使用@PathVariable ，必须指定value属性
二、使用Feign构造多参数的请求：
1.GET请求多参数的
a):使用@RequestParam注解指定请求的参数，URL有几个参数，Feign接口中的方法就有几个参数。b):使用Map来构建。
2.POST请求包含多个参数
@RequestMapping(value = "/post", method = RequestMethod.POST)
public User post(@RequestBody User user);


应用容错机制:
a.超时机制
b.舱壁模式
c.断路器


Hystrix（含义是豪猪，防御的意思）
在大中型分布式系统中通常有很多依赖，当某些依赖出现高并发或阻塞时，可能导致整个系统异常，所以需要对依赖做隔离，Hystrix就是处理依赖隔离的框架,同时也是可以帮我们做依赖服务的治理和监控。

Hystrix是由Netflix开源的一个延迟和容错库，用于隔离访问远程系统、服务或者第三方库，防止级联失败，从而提升系统的可用性与容错性。
Hystrix主要通过以下几点实现延迟和容错:
包裹请求、跳闸机制、资源隔离、监控、回退机制、自我修复。
通用方式使用Hystrix:
a)Hystrix会有一个默认的降级方案--抛出异常；
b)服务降级:启动类加注解@EnableCircuitBreaker，controller方法添加注解@HystrixCommand。
使用@HystrixCommand 注解，就可保护该API。这里的“保护”，其实带有三层含义——“超时机制”、“仓壁模式”、“断路”。

