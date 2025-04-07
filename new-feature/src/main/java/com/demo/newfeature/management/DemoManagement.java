package com.demo.newfeature.management;

import com.demo.newfeature.entity.OneRec;
import com.demo.newfeature.entity.StatusType;
import com.demo.newfeature.helper.DatetimeUtils;
import com.demo.newfeature.helper.RecordUtils;
import com.demo.newfeature.repo.OneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class DemoManagement {

    private final OneRepository oneRepo;


    public DemoManagement(OneRepository oneRepo) {
        this.oneRepo = oneRepo;
    }

    public OneRec addOneEntity(OneRec rec){
        return oneRepo.save(rec);
    }

    public Optional<OneRec> getOneById(Integer id){
        var result= oneRepo.findByIdAndDeletedIsFalse(id);
        if(result==null){
            return Optional.empty();
        }
        return Optional.of(result);
    }

    public Optional<OneRec> updateOne(Integer id, Map<String,Object> map){

        var oldRec=oneRepo.findByIdAndDeletedIsFalse(id);
        if(oldRec==null){
            return Optional.empty();
        }
        OneRec newRec= RecordUtils.copy(oldRec,map);
        oneRepo.save(newRec);
        return Optional.of(oneRepo.save(newRec));
    }

    public Integer  updateStatus(Integer id,StatusType status){
        return oneRepo.doUpdateStatus(id,status);
    }

    public Integer deleteOne(Integer id){
        return oneRepo.doDelete(id);
    }

    public List<OneRec> queryByNameWild(String query,Pager pager){
        return oneRepo.findByWildName(query,pager);
    }

    public record Pager(int limit, LocalDateTime lastLocal) {

        public Pager(int limit,long timestamp){
                var tag= DatetimeUtils.getLocalTime(timestamp);
                this(limit,tag);
        }

    }

}
