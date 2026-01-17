package com.planner.workers.memo;

import com.planner.workers.DataNotFoundException;
import com.planner.workers.question.Question;
import com.planner.workers.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public List<Memo> getMemoList(SiteUser siteUser){
        return this.memoRepository.findAllByUserName(siteUser);
    }

    public Memo getMemo(Integer id){
        Optional<Memo> memo = this.memoRepository.findById(id);
        if(memo.isPresent()){
            return memo.get();
        } else {
            throw new DataNotFoundException("memo not found");
        }
    }

    public void create(String memoName, String memoContent, SiteUser userName){
        Memo memo = new Memo();
        memo.setMemoName(memoName);
        memo.setMemoContent(memoContent);
        memo.setPostDate(LocalDate.now());
        memo.setUserName(userName);
        memoRepository.save(memo);
    }

    public void modify(Integer id, Memo memo){
        Memo modifyMemo = getMemo(id);
        modifyMemo.setMemoName(memo.getMemoName());
        modifyMemo.setMemoContent(memo.getMemoContent());
        modifyMemo.setPostDate(memo.getPostDate());
        memoRepository.save(modifyMemo);
    }

    public void delete(Integer id){
        Optional<Memo> memo = memoRepository.findById(id);
        this.memoRepository.delete(memo.get());
    }

}