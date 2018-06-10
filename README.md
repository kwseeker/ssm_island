# 个人网站（后端部分）

## 简介
网址：http://www.kwseeker.top

前端分为菜单、外链、内容模块（主页、工具、编程、设计、鉴赏、读书、随笔、计划、关于）
  
后端分为两大模块：用户鉴权和模块管理

此项目的前端代码全为测试用代码

## 技术栈
（后端开发完成后总结）

## 功能模块
#### 用户管理
支持三种用户：  
+ 管理员（拥有所有文件的修改查看权限，以及用户管理权限）
+ 普通用户（只拥有部分文件的修改查看权限）
+ 游客（只拥有部分文件的查看权限）

#### 模块管理
每个模块都支持上传、下载、浏览、删除等基本功能；  

+ 视频库
+ 音乐库
+ 图库
+ 书库
+ 日记库
+ 计划表库

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
    
+ REST API 设计  
    (下面暂不符合RESTful规范，基本功能开发完成后，第二阶段项目修饰阶段修改)
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


##### 视频库 (前端编程以及鉴赏模块视频数据)
视频上传，下载，搜索，删除，在线播放

##### 音乐库 （前端鉴赏模块）
音乐文件上传，下载，搜索，删除，在线播放

##### 图库 （前端编程以及鉴赏模块图片数据）
图片上传，下载，缩略图查看，原始图片在线查看

##### 书库 （前端读书模块书籍资源）
各种格式书籍上传、下载、搜索、删除、在线阅读

##### 随笔库 （前端随笔模块富文本数据）
富文本数据编辑（新建与修改）、保存到后台、查看、删除、搜索。

+ 功能：
    - 查看&搜索
    通过点击随笔logo,社区分享触发查看全部会员公开的随笔；  
    点击个人随笔，首先验证是否登录，已登录的话查看登录用户的个人随笔；未登录弹窗提示用户登录，弹窗附带登录按钮；  
    通过搜索查看，首先检测激活的页面，激活的是社区分享，就搜索全部会员公开随笔中的符合条件的随笔，
    若激活的是个人随笔且用户已登录，搜索用户个人的全部符合条件的随笔。  
    点击随笔条目，进入随笔内容页。
    
    - 随笔新建&保存
    打开个人随笔页面或登录状态在右侧点击"新随笔"，打开新随笔编辑页面，编辑完成点击保存，向后端请求保存。
    
    - 删除
    在个人随笔页面点击随笔条目右下角垃圾桶图标删除随笔。
    
    - 修改
    在个人随笔页面点击随笔条目右下角编辑图标修改随笔。

+ REST API设计：
    从功能分析，需要创建哪些请求：
    1）请求获取全部会员公开随笔列表（包括用户信息）；
    2）请求获取某用户全部随笔列表；
    3）请求获取随笔正文
    4）请求保存新随笔
    5）请求更新已修改随笔
    6）请求删除指定随笔

##### 计划表库 （前端工作计划模块图表数据）


