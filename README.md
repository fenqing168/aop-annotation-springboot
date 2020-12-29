# spring boot 注解开发aop的工具
## 使用方法 
### 1. 将aop-annotation-spring-boot-starter拷贝到项目里或者用maven的package，复制target里的jar包到项目进行依赖
### 2. 在你想代理的方法上使用@PointcutFlag注解，并且给flag命名
### 3. 代理的方法，要执行的代码方法上使用@PointcutHandler注解，并且给flag与代理的一致即可，并且支持标识是前置还是后置，并且同时可以是同步或者异步
### 4. 可以一个PointcutFlag注解对应多个@PointcutHandler注解，也可以多个PointcutFlag注解对应一个或者多个@PointcutHandler注解