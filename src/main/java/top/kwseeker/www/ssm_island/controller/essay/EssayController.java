package top.kwseeker.www.ssm_island.controller.essay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 随笔模块Controller
 * 功能：
 *      提交、读取、修改、删除、导出、
 *      评论、
 *      关键词搜索
 */
@Controller
@RequestMapping("/essay")
public class EssayController {

    /**
     * 请求：
     * Request Method: POST
     * Content-Type: application/x-www-form-urlencoded
     * 传输数据：Form Data (地点、随笔正文、访问权限、分类)
     * 其他数据：后端自动生成(创建更新时间、随笔编号、点击量) 作者ID（由Session获取）
     * 返回：Json, 前端提交后，跳转到预览页面并用Json数据更新预览内容
     */
    @PostMapping(value = "/new")
    public void store(String location, String content, String access_auth, HttpSession session) {

    }

    @GetMapping()
    public void read() {}

    public void modify() {}

    public void delete() {}

    public void export() {}

    public void comment() {}

    public void keywordSearch() {}

}
