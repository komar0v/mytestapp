package jobtes.kadua4.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobtes.kadua4.model.MemberMDL;
import jobtes.kadua4.repos.MemberRepository;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberMDL> getAllMembers() {
        return memberRepository.findAll();
    }

    public MemberMDL getMemberById(UUID id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    public MemberMDL createMember(MemberMDL member) {
        return memberRepository.save(member);
    }

    public void deleteMember(UUID id) {
        memberRepository.deleteById(id);
    }

    public MemberMDL updateMember(UUID id, MemberMDL memberDetails) {
        MemberMDL member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        member.setEmail(memberDetails.getEmail());
        member.setJenis_kelamin(memberDetails.getJenis_kelamin());
        member.setName(memberDetails.getName());
        member.setNo_hp(memberDetails.getNo_hp());
        member.setNo_ktp(memberDetails.getNo_ktp());
        member.setTanggal_lahir(memberDetails.getTanggal_lahir());
        member.setFoto_diri(memberDetails.getFoto_diri());
        member.setPassword(memberDetails.getPassword());

        return memberRepository.save(member);
    }
}
