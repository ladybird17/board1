package board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TymeleafController {

	@RequestMapping("/test/thymeleafTest1/{num}")
	public String index(@PathVariable int num, Model model) {
		int rs = num;
		model.addAttribute("msg","Number is"+rs);
		return "/test/thymeleaftest1";
	}
}
