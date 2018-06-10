package top.kwseeker.www.ssm_island.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.kwseeker.www.ssm_island.common.Const;
import top.kwseeker.www.ssm_island.common.ServerResponse;
import top.kwseeker.www.ssm_island.pojo.User;
import top.kwseeker.www.ssm_island.service.IUserService;
import top.kwseeker.www.ssm_island.util.DES3Util;
import top.kwseeker.www.ssm_island.util.MailSenderUtil;
import top.kwseeker.www.ssm_island.util.VerifyCodeImageUtil;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*预备知识：
* 1 Request请求，请求数据包各字段解读
* 2 MD5加解密
* 3 数据返回给前端的几种方法
* 4 Logback+slf4j日志系统的使用
* 5 数据返回格式控制（jackson使用）
*/

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    private final static String TAG = "<DevDbg> ";
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/login.do")
    @ResponseBody                   // 将返回值自动序列化为Json格式
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        // 1 检查用户名在数据库是否存在且password是否正确
        // 1.1 UserMapper.xml（UsrMapper.java User相当于其代理人）中添加查找对应用户名和密码的数据的接口(这里只做增删改查等基本操作)
        // 1.2 UserServiceImpl通过查找结果判断是否成功
        // 2 返回结果
        // 2.1 成功返回 代码和用户信息的json数据
        // 2.2 失败返回 代码和错误信息的json数据
        LOGGER.info(TAG + "Request: /user/login.do -> username=" + username + " password=" + password);

        ServerResponse<User> serverResponse = iUserService.userLogin(username,password);
        if(serverResponse.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,serverResponse.getRetData());
        }
        return serverResponse;  //TODO: ServerResponse里面只有三个字段，为什么json返回值多出一个success字段？
    }

    /**
     * 用户名检查
     */
    @GetMapping(value = "/checkName.do")
    @ResponseBody
    public ServerResponse<String> checkName(String username) {
        LOGGER.info(TAG + "Request: /user/checkName.do -> username=" + username);
        return iUserService.userCheck(username);
    }

    /**
     * 用户注册
     * 用户名、密码、手机号、邮箱必填
     */
    @PostMapping(value = "/register.do")
    @ResponseBody
    public ServerResponse<String> register(String username,
                                           String password,
                                           @RequestParam(required = false, defaultValue = "") String introduction,
                                           String phone,
                                           String email){
        LOGGER.info(TAG + "Request: /user/register.do -> username=" + username);
        return iUserService.userRegister(username, password, introduction, phone, email);
    }

    /**
     * 忘记密码(邮箱找回)
     * 0.（前端）用户点击“忘记密码”，跳转到邮箱和验证码界面
     * 1.（前端）表单输入注册时的邮箱；
     * 2.（后端）验证用户邮箱是否正确，如果用户邮箱不存在网站的用户表中，则提示用户邮箱未注册；
     * 3.（后端）发送邮件，如果用户邮箱确实存在用户表中，则组合用于验证用户信息的字符串，并构造URL发送到用户邮箱中,并限定URL有效时间;
     * 4.（前端）用户登录邮箱收取邮件，点击URL链接到网站验证程序；
     * 5.（后端）网站程序通过用户请求的字符串查询本地用户表，比对用户信息是否正确；
     * 6.（后端）如果正确则转到重置密码页面重新设置新密码，反之则提示用户验证无效。
     */
    @GetMapping(value = "/getVerifyCode.do")
    public void getVerifyCode(HttpServletResponse response, HttpSession session) {
        //返回image/png
        response.setContentType("image/png");
        //利用图片工具生成图片
        Object[] objs = VerifyCodeImageUtil.createImage();  //第一个参数是生成的验证码，第二个参数是生成的图片
        session.setAttribute("imageCode",objs[0]);      //将验证码存入Session
        BufferedImage image = (BufferedImage)objs[1];       //将图片输出给浏览器
        try {
            OutputStream os = response.getOutputStream();   //通过response ServletOutputStream 返回图片
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //两种方式返回结果
    //1 通过json返回数据,打开新的页面提示用户查看邮件或报告校验码或邮箱异常
    //TODO: 2 返回html界面提示去查看邮件或者报告校验码或邮箱异常（Thymeleaf模板？）
    @PostMapping(value = "/forgetPasswd.do")
    @ResponseBody
    public ServerResponse<String> forgetPassword(String email, String verifyCode, HttpSession session) {
//    public String forgetPassword(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

//        Enumeration<String> paramNames = request.getParameterNames();
//        while(paramNames.hasMoreElements()){
//            String paramName = paramNames.nextElement();
//            System.out.println(paramName + ":" + request.getParameter(paramName));
//        }

//        String email= request.getParameter("email");
//        String verifyCode = request.getParameter("verifyCode");
        String originVerifyCode = (String) session.getAttribute("imageCode");
        System.out.println("email:" + email + " verifyCode:" + verifyCode + " originVerifyCode:" + originVerifyCode);
        // 检查验证码是否正确，及邮箱是否存在
        if(!verifyCode.equals(originVerifyCode)){
            return ServerResponse.createByErrorMessage("验证码错误");
        }
        if (iUserService.emailCheck(email) == 0) {
            return ServerResponse.createByErrorMessage("邮箱尚未注册");
        }
        // 发送邮件 TODO: （大概要4秒左右）要不要放到单独的线程中跑？
        // htmlUrl中应该包含用户名及邮件发送时间（用户辨认及邮件超时判断）等信息
//        String urlString = "http://localhost:8080/EVM/forgetPasswordAction?method=resetPassword&key=";    // TODO: method用法
        String mailUrl = "http://localhost:8080/user/resetPasswordForm.do?key=";
        String emailAndTime = email + "&" + (new Date()).getTime();               //分隔符选择
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String mailContent = mailUrl + DES3Util.encryptCode(emailAndTime);    // 使用3DES加密
            stringBuilder.append("<html><head>");
            stringBuilder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
            stringBuilder.append("</head><body>");
            stringBuilder.append(email);
            stringBuilder.append(",您好：");
            stringBuilder.append("<br /><br />");
            stringBuilder.append("\t您收到这封电子邮件是因为您（也可能是某人冒充你的名义）申请了一个找回密码的请求。");
            stringBuilder.append("\t假如这不是您本人申请，请您尽快联络管理员。");
            stringBuilder.append("\t你可以点击下面链接重新设置您的密码，如果点击无效，请将链接粘贴到浏览器地址栏重试：");
            stringBuilder.append("\t<a href=\"");
            stringBuilder.append(mailContent);
            stringBuilder.append("\">重置密码</a>");
            stringBuilder.append("请不要直接回复本邮件。");
            stringBuilder.append("</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        MailSenderUtil.MailContentInfo mailInfo = new MailSenderUtil.MailContentInfo();
        mailInfo.setMailServerHost("smtp.163.com");
        mailInfo.setValidate(true);
        mailInfo.setUserName("xiaohuileee@163.com");
        mailInfo.setPassword("112358csbn9t");     // 授权码
        mailInfo.setFromAddress("xiaohuileee@163.com");
        mailInfo.setToAddress(email);
        mailInfo.setSubject("重新设置密码验证链接");
        mailInfo.setContent(stringBuilder.toString());
        MailSenderUtil mailSender = new MailSenderUtil();
        mailSender.sendHtmlMail(mailInfo);

        // 返回邮件发送成功提醒
        // 返回消息在新窗口打开，可以通过sendRedirect() 也可以在前端处理 window.open或window.location等
        /* try {
            response.sendRedirect("user/emailVerification.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:emailVerification.html";*/
        // 由于错误消息要在原页面显示，正常消息要打开新标签页显示；后端实现较为麻烦，改为在前端处理，后端只返回JSON数据
        return ServerResponse.createBySuccessMessage("验证邮件已发送请查阅!");
    }

    /*
     * 成功：前端加载重置密码表单
     * 失败：前端显示错误信息
     */
    @GetMapping(value = "/resetPasswordForm.do")
    @ResponseBody
    public void resetPasswordForm(HttpServletRequest request, HttpServletResponse response, String key) {
        long timeout = 1800000;   //超时时间 30*60*1000ms (30分钟)
        List<String> checkResultList = new ArrayList<>();
        // 解密后获取邮箱和邮件发送时间信息
        try {
            key = key.replace(" ", "+");    // 字符编码时可能转换出来特殊字符串，而传递给后端时有个URL编解码过程需要注意
            String keySrc = DES3Util.decryptCode(key);
            String[] keyStrs = keySrc.split("&");
            if(keyStrs.length != 2) {
//                return ServerResponse.createByErrorMessage("key值传递有误");
                checkResultList.add("Failed");
                checkResultList.add("key值传递有误");
            } else {
                System.out.println(TAG + "key: " + key + " email=" + keyStrs[0] + " time=" + keyStrs[1]);

                long emailSendTime = Long.parseLong(keyStrs[1]);
                if ((new Date().getTime() - emailSendTime) > timeout) {
//                    return ServerResponse.createByErrorMessage("链接超时，请重新申请重置密码");
                    checkResultList.add("Failed");
                    checkResultList.add("链接超时，请重新申请重置密码");
                } else {
                    checkResultList.add(keyStrs[0]);
                    checkResultList.add("验证成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("resetPassword.jsp");
        request.setAttribute("checkResult", checkResultList);   // TODO
        try {
            requestDispatcher.forward(request, response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
//        return ServerResponse.createBySuccessMessage("验证成功");
//        return iUserService.updatePassword(username, newPassword);
    }

    @PostMapping(value = "resetPasswdByEmail.do")
    @ResponseBody
    public ServerResponse<String> resetPasswordByEmail(String email, String newPassword) {
        System.out.println(TAG + "Request:" + " email=" + email + " time=" + newPassword);
        return iUserService.updatePasswordByEmail(email, newPassword);
    }

    /**
     * 忘记密码(手机验证码重置)
     * 发送忘记密码请求，服务端发送动态验证码，用户填写动态验证码校验，成功返回重置密码界面
     */
    // TODO：手机验证码重置密码

    /**
     * 登录状态密码重置
     * 用户请求修改密码,前端跳转到密码修改界面，新密码填写后请求发给后端，服务端获取当前登录用户，然后重置密码
     */
    @PostMapping(value = "/resetPasswd.do")
    @ResponseBody
    public ServerResponse<String> resetPassword(String newPassword, HttpSession session) {
        String username = (String)session.getAttribute(Const.CURRENT_USER);
        if(username == null) {
            return ServerResponse.createByErrorMessage("您尚未登录");
        }
        // 更新登录用户的密码
        return iUserService.updatePassword(username, newPassword);
    }

    /**
     * 退出登录
     * 即抹除当前会话信息（目前只有current_user）
     */
    @PostMapping(value = "/logout.do")
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccessMessage("成功退出登录");
    }
}
