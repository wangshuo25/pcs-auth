微服务前后端分离角色权限认证体系

建议配合以下阅读加深理解，第一二个尤为重要:
- SpringSide 3 中的安全框架
http://www.blogjava.net/youxia/archive/2008/12/07/244883.html
- Spring Security 架构
https://leer.moe/2019/03/26/spring-security-architecture/
- 使用 json 前后端分离
https://github.com/houzhenggangGit/spring-security-demos
- 社区 Spring Security 从入门到进阶系列教程
http://www.spring4all.com/article/428
- Spring Security 源码合集 https://blog.csdn.net/x5fnncxzq4/article/details/79333867
- 理解oauth2 https://www.kancloud.cn/kancloud/oauth_2_0/63331
# 认证

## 以下内容主要描述spring security **认证过程**
翻译自：https://springbootdev.com/2017/08/23/spring-security-authentication-architecture/

![Spring Security：身份验证架构](https://img-blog.csdnimg.cn/20190509223110568.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Fycm9uX2hvdQ==,size_16,color_FFFFFF,t_70)
### 1.接收了Http请求 
Spring security有一系列过滤器链。因此，当请求到来时，它将通过一系列过滤器进行身份验证和授权。当存在用户认证请求时，它也将像往常一样通过过滤器链，直到它根据认证机制/模型找到相关的认证过滤器。

例如： 
~ HTTP基本身份验证请求通过过滤器链，直到它到达BasicAuthenticationFilter 。

~ HTTP摘要式身份验证请求通过过滤器链直到它到达DigestAuthenticationFilter 。

~ 登录表单提交请求（登录表单身份验证请求）通过过滤器链直到它到达UsernamePasswordAuthenticationFilter 。       

~ x509身份验证请求通过过滤器链直到它到达X509AuthenticationFilter 等...

### 2.根据用户凭据（credentials）创建AuthenticationToken
一旦相关的AuthenticationFilter收到验证请求，它就会从收到的请求中提取用户名和密码（大多数验证机制都需要用户名和密码）。之后，它会根据提取的用户凭据创建一个Authentication对象。

如果提取的凭据是用户名和密码，则将使用提取/找到的用户名和密码创建UsernamePasswordAuthenticationToken 。
### 3.为AuthenticationManagager委派创建的AuthenticationToken
创建UsernamePasswordAuthenticationToken 对象后，它将用于调用AuthenticationManager 的authenticate 方法。

AuthenticationManager.java
```java
public interface AuthenticationManager
{
  Authentication authenticate(Authentication authentication)throws AuthenticationException;
}
```
AuthenticationManager只是一个接口，实际的实现是ProviderManager和AuthenticationProvider。ProviderManager通过委托者模式调用若干AuthenticationProvider的authenticate方法进行验证。
ProviderManager有一个配置的AuthenticationProvider 列表，用于验证用户请求。ProviderManager将遍历提供的每个AuthenticationProvider，并尝试根据传递的Authentication对象对用户进行身份验证（例如：UsernamePasswordAuthenticationToken ）

### 4.尝试以使用AuthenticationProvider列表进行身份验证
AuthenticationProvider 尝试使用提供的身份验证对象对用户进行身份验证。
```java
public interface AuthenticationProvider {
 
    Authentication authenticate(Authentication authentication) throws AuthenticationException;
 
    boolean supports(Class<?> authentication);
}
```
以下是框架附带的一些现有身份验证提供程序：

- CasAuthenticationProvider
- JaasAuthenticationProvider
- **DaoAuthenticationProvider**  通过数据库验证
- OpenIDAuthenticationProvider
- RememberMeAuthenticationProvider
- LdapAuthenticationProvider


### 5.UserDetailsS​​ervice是否必需？
一些AuthenticationProvider可以使用UserDetailsS​​ervice根据用户名检索用户详细信息。（例如： - DaoAuthenticationProvider）
```java
public interface UserDetailsService
{
  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
```
### 6. and 7. UserDetails或User对象
 UserDetailsS​​ervice将根据用户名检索UserDetails（实际实现的是User）。
 （例如User是数据库查到的信息）
 
### 8.身份验证或AuthenticationException
authenticate方法比较Authentication对象和User对象的密码。

根据AuthenticationProvider 接口，在成功身份验证时AuthenticationProvider将完全返回完全填充的身份验证对象，在身份验证失败时抛出AuthenticationException 异常，不能判断时，返回null。

如果抛出任何AuthenticationException ，那将由支持身份验证机制的已配置AuthenticationEntryPoint 处理。

### 9.验证完成！
AuthenticationManager将获取的完全填充的Authentication对象返回给相关的Authentication Filter。

### 10.在SecurityContext中设置验证对象
然后，相关的AuthenticationFilter将获取的身份验证对象存储在SecurityContext中，以供将来过滤器使用。（用于授权过滤器）
```java
SecurityContextHolder.getContext().setAuthentication(authentication);
```
希望这将有助于您在一定程度上深入了解Spring Security身份验证体系结构。
  
## 与oauth2结合配置认证服务器

密码模式（Resource Owner Password Credentials Grant）中，用户向客户端提供自己的用户名和密码。客户端使用这些信息，向"服务商提供商"索要授权。

在这种模式中，用户必须把自己的密码给客户端，但是客户端不得储存密码。
我们的客户端就是web服务器，是一个前台服务。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190529143701486.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0Fycm9uX2hvdQ==,size_16,color_FFFFFF,t_70)

它的步骤如下：

（A）用户向客户端提供用户名和密码。

（B）客户端将用户名和密码发给认证服务器，向后者请求令牌。

（C）认证服务器确认无误后，向客户端提供访问令牌。

B步骤中，客户端发出的HTTP请求，包含以下参数：

grant_type：表示授权类型，此处的值固定为"password"，必选项。
username：表示用户名，必选项。
password：表示用户的密码，必选项。
scope：表示权限范围，可选项。
下面是一个例子。

     POST /token HTTP/1.1
     Host: server.example.com
     Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW
     Content-Type: application/x-www-form-urlencoded

     grant_type=password&username=johndoe&password=A3ddj3w
C步骤中，认证服务器向客户端发送访问令牌，下面是一个例子。

     HTTP/1.1 200 OK
     Content-Type: application/json;charset=UTF-8
     Cache-Control: no-store
     Pragma: no-cache

     {
       "access_token":"2YotnFZFEjr1zCsicMWpAA",
       "token_type":"example",
       "expires_in":3600,
       "refresh_token":"tGzv3JOkF0XG5Qx2TlKWIA",
       "example_parameter":"example_value"
     }

access_token：表示访问令牌，必选项。
token_type：表示令牌类型，该值大小写不敏感，必选项，可以是bearer类型或mac类型。
expires_in：表示过期时间，单位为秒。如果省略该参数，必须其他方式设置过期时间。
refresh_token：表示更新令牌，用来获取下一次的访问令牌，可选项。
scope：表示权限范围，如果与客户端申请的范围一致，此项可省略。

整个过程中，客户端不得保存用户的密码。

## 使用说明

通过用户名查询用户密码，比较后成功返回token,失败返回错误信息。
 整个认证通过spring Security 完成。
 因为数据库中具有多个用户表，可以通过不同的 client_id 作为信息，分辨出哪一种用户。
 其中:
 
 sys----------------user 表
 
 org----------------org_user 表
 
 test-------------testee 表
 
 counselor-------------counselor 表

 
 
 * 请求token令牌的参数：
  http://localhost:8003/oauth/token?username=arron&password=123456&grant_type=password&scope=select&client_id=sys&client_secret=123456
 * 访问资源的请求设定： 
 Authorization参数，值是“[grant_type] [access_token]”，grant_type值与access_token值之间用空格分开。
 
 例如：Authorization  ：bearer 65d6f4f6-70d3-4bb4-b36b-c6e570a3027b 

# 授权与资源服务器
