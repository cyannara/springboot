package com.example.demo.emp.web;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.Paging;
import com.example.demo.emp.EmpVO;
import com.example.demo.emp.SearchVO;
import com.example.demo.emp.service.EmpService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller // 컨테이너 빈 등록 + 사용자요청처리할 수 있는 커맨드 핸들러 변환
public class EmpController {

	final EmpService empService;   //의존성주입(DI dependency Injection)
	
	@RequestMapping("/empList")
	public String empList(Model model, 
			         EmpVO vo, 
			         SearchVO svo, 
			         Paging pvo){ 

		//페이징처리
		pvo.setPageUnit(5);  //데이터수
		pvo.setPageSize(3);  //페이지번호
		svo.setStart(pvo.getFirst());
		svo.setEnd(pvo.getLast());
		
		Map<String,Object> map = empService.getEmpList(vo, svo);
		
		pvo.setTotalRecord((Long)map.get("count"));
		model.addAttribute("paing", pvo);
		
		//목록조회
		model.addAttribute("empList", map.get("data"));
		return "empList"; 
	}
	
	@PostMapping("/insert3")
	public String insert3(EmpVO vo, RedirectAttributes rttr) {
		System.out.println("등록: " +  vo);
		rttr.addAttribute("insertResult", "성공");
		rttr.addFlashAttribute("flashResult", "한번만 사용가능");
		return "redirect:empResult";
	}
	
	@PostMapping("/insert2")
	public ResponseEntity<EmpVO> insert2(EmpVO vo) {
		return new ResponseEntity<>(vo, HttpStatus.OK);
	}
	
	@GetMapping("/insert") 
	public void insert() {}
	
	@PostMapping("/insert")
	public ModelAndView insert(@ModelAttribute("emp") EmpVO vo) {
		System.out.println(vo);
		empService.insertEmp(vo);
		//커맨드객체는 model에 추가
			
		ModelAndView mv = new ModelAndView();
		mv.setViewName("home");
		mv.addObject("insertResult", "success");
		//mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		return mv;
	}
	
	@GetMapping("/info/{empId}")
	public String info(@PathVariable int empId, Model model) {
		model.addAttribute("emp", empService.getEmpInfo(empId));
		return "empInfo";
	}  
	
	@GetMapping("/update/{empId}")
	public String update(@PathVariable int empId) {
		System.out.println(empId);
		return "index";
	}  
	
	@GetMapping("/delete")
	public String delete(int employeeId, String name) {
		empService.deleteEmp(employeeId);
		return "redirect:empList";
	}  
	
	@GetMapping("/")
	public String test() {
		return "index";
	}  

}
