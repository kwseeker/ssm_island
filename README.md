# 个人网站（后端部分）

## 简介
网址：http://www.kwseeker.top

前端分为菜单、外链、内容模块（主页、工具、编程、设计、鉴赏、读书、杂感、关于）
  
后端分为两大模块：用户鉴权和模块管理

## 技术栈

## 功能模块
#### 用户管理
支持三种用户：  
+ 管理员（拥有所有文件的修改查看权限，以及用户管理权限）
+ 普通用户（只拥有部分文件的修改查看权限）
+ 游客（只拥有部分文件的查看权限）

#### 分类管理

每个模块都支持上传、下载、浏览、删除等基本功能；  

+ 视频库
+ 音乐库
+ 图库
+ 书库
+ 日记库

## 具体实现

#### 用户管理
+ 功能  
    - 注册
    用户注册需要管理员同意。
    
    - 登录与退出
    http://www.kwseeker.top 请求默认以游客身份登录；  
    某些需要特权操作的地方，前端操作后，弹出提示登录框，验证身份；
    也可以主动点击登录，以普通用户或者管理员身份登录。
    
    - 忘记密码与重置
    
    - 获取用户信息
    
+ restapi 接口设计  
    - 登录  
    ```
    Url：/user/login Method: post  
    request：  
        Param: username, password  
    response:  
        Fail:  
        {
            "status": 1,
            "msg": "密码错误"
        }
        Success:
        {
            "status": 0,
            "data": {
                "uid": 12,
                "username": "...",
                "introduction": "...",
                "phone": "...",
                "role": 1,
                "creat_time": "...",
                "update_time": "..."
            }
        }
    ```
    - 检查用户名是否有效
    ```
    Url：/user/checkName Method: get
        /checkName?str=admin&type=username
        /checkName?str=13712345678&type=phone
    request：  
        Param: str, type
    response:  
        Fail:  
        {
            "status": 1,
            "msg": "用户已存在"
        }
        Success:
        {
            "status": 0,
            "msg": "用户名可用"
        }
    ```
    - 注册
    ```
    Url：/user/register Method: get
    request：  
        Param: username, password, introduction, phone  
    response:  
        Fail:  
        {
            "status": 1,
            "msg": "用户已存在"
        }
        Success:
        {
            "status": 0,
            "msg": "注册成功"
        }
    ```
    - 获取用户信息
    ```
    
    ```
    - 忘记密码与密码重置
    忘记密码与重置必须确认是本人操作，否则会有横向越权漏洞  
    可以通过邮箱发送验证码的方式进行验证，有效期5min，后台生成验证码
    发送给用户邮箱，用户填写验证码和重置的密码，用于后台重置验证。
    TODO: 后面可以搭建一个短信验证服务器与此系统配合。
    ```
    Url：/user/forgetPasswd Method: get
        /forgetPasswd?username=Arvin
    request：  
        Param: username
    response:  
        Fail:  
        {
            "status": 1,
            "msg": "用户不存在"
        }
        Success:
        {
            "status": 0,
            "msg": "请重置密码"
        }
    
    Url：/user/resetPasswd Method: get
        /resetPasswd?username=Arvin&newPasswd=xxx&verifyCode=xxx
    request：  
        Param: username newPasswd verifyCode
    response:  
        Fail:  
        {
            "status": 1,
            "msg": "<错误原因>"
        }
        Success:
        {
            "status": 0,
            "msg": "密码修改成功"
        }
    ```
    - 登录状态重置密码
    
    - 登录状态更新个人信息
    
    - 退出登录
    ```
    Url：/user/logout Method: get
    request：  
        Param: 
    response:  
        Fail:  
        {
            "status": 1,
            "msg": "..."
        }
        Success:
        {
            "status": 0,
            "msg": "用户已退出"
        }
    ```
    
    - TODO: 单独设置后台管理员登录
    
+ 技术
    - MD5明文加密
    - 横向越权与纵向越权安全漏洞解决
    - Guava缓存的使用
    - 高复用服务器响应对象的设计思想及抽象封装
    - Mybatis-plugin使用技巧
    - Session使用
    
#### 后台功能模块
