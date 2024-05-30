package jobtes.kadua4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jobtes.kadua4.repos.MemberRepository;

@Service
public class MemberService {
@Autowired
    private MemberRepository repository;
}
