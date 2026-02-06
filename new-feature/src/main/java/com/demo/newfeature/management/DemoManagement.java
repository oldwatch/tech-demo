package com.demo.newfeature.management;

import module id.encoder;
import com.demo.newfeature.entity.OneRec;
import com.demo.newfeature.entity.StatusType;
import com.demo.newfeature.repo.OneRepository;
import org.demo.utils.Optional2;
import org.demo.utils.RecordUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DemoManagement {

    private final OneRepository oneRepo;


    private final int pageSize = 50;

    public DemoManagement(OneRepository oneRepo) {
        this.oneRepo = oneRepo;
    }

    public OneRec addOneEntity(OneRec rec) {
        return oneRepo.save(rec);
    }

    public Optional2<OneRec> getOneById(Integer id) {
        var result = oneRepo.getEntityById(id);
        if (result == null) {
            return Optional2.empty();
        }
        return Optional2.of(result);
    }

    public Optional2<OneRec> updateOne(Integer id, Map<String, Object> map) {

        var oldRec = oneRepo.getEntityById(id);
        if (oldRec == null) {
            return Optional2.empty();
        }
        OneRec newRec = RecordUtils.duplicate(oldRec, map);
        return Optional2.of(oneRepo.save(newRec));
    }

    public Integer updateStatus(Integer id, StatusType status) {
        return oneRepo.doUpdateStatus(id, status);
    }

    public Integer deleteOne(Integer id) {
        return oneRepo.doDelete(id);
    }

    public List<OneRec> queryByNameWild(String query, Optional2<Pager> pagerOpt) {
        return switch (pagerOpt) {
            case Optional2.Some<Pager> some -> oneRepo.findByWildName(query, some.get());
            case Optional2.None<Pager> _ -> oneRepo.findByWildName(query, pageSize);
        };
    }

//    public record Pager(Integer limit, LocalDateTime lastLocal) {
//
//
//    }

}
