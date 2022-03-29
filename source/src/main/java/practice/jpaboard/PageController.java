package practice.jpaboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

    @GetMapping("/board/upload")
    public String board() {
        return "/board/upload";
    }

    @GetMapping("/board/detail")
    public String board(@RequestParam Long no, Model model) {
        model.addAttribute("no", no);
        return "/board/detail";
    }
}
