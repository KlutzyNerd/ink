package xyz.itao.ink.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.itao.ink.common.Commons;
import xyz.itao.ink.constant.WebConstant;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hetao
 * @date 2018-12-07
 * @description
 */
@Controller
@RequestMapping("/admin/template")
@Slf4j
public class TemplateController {
    @GetMapping("")
    public String templateIndex(HttpServletRequest request) {
        String themePath = WebConstant.CLASSPATH + File.separatorChar + "templates" + File.separatorChar + "themes" + File.separatorChar + Commons.siteTheme();
        try {
            List<String> files = Files.list(Paths.get(themePath))
                    .map(path -> path.getFileName().toString())
                    .filter(path -> path.endsWith(".html"))
                    .collect(Collectors.toList());

            List<String> partial = Files.list(Paths.get(themePath + File.separatorChar + "partial"))
                    .map(path -> path.getFileName().toString())
                    .filter(path -> path.endsWith(".html"))
                    .map(fileName -> "partial/" + fileName)
                    .collect(Collectors.toList());

            List<String> statics = Files.list(Paths.get(themePath + File.separatorChar + "static"))
                    .map(path -> path.getFileName().toString())
                    .filter(path -> path.endsWith(".js") || path.endsWith(".css"))
                    .map(fileName -> "static/" + fileName)
                    .collect(Collectors.toList());

            files.addAll(partial);
            files.addAll(statics);

            request.setAttribute("tpls", files);
        } catch (IOException e) {
            log.error("找不到模板路径");
        }
        return "admin/tpl_list";
    }

    @GetMapping("/content")
    public String getContent(@RequestParam String fileName) {
        String content = null;
        try {
            String themePath = WebConstant.CLASSPATH + File.separatorChar + "templates" + File.separatorChar + "themes" + File.separatorChar + Commons.siteTheme();
            String filePath  = themePath + File.separatorChar + fileName;
            content  = Files.readAllLines(Paths.get(filePath)).stream().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            log.error("获取模板文件失败", e);
        }
        return content;
    }
}
