package jobtes.kadua4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jobtes.kadua4.model.MemberMDL;
import jobtes.kadua4.repos.MemberRepository;

@RestController
@RequestMapping("/api")
public class ApiController {
@Autowired
    private MemberRepository memberRepository;

    @GetMapping("/members")
    public List<MemberMDL> getMembersByName(@RequestParam String name) {
        return memberRepository.findByNameContainingIgnoreCase(name);
    }
}
