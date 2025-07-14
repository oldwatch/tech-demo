package com.demo.newfeature.web.controller;

import com.demo.newfeature.entity.OneRec;
import com.demo.newfeature.entity.StatusType;
import com.demo.newfeature.helper.IdEncodeTool;
import com.demo.newfeature.helper.Optional2;
import com.demo.newfeature.helper.RecordUtils;
import com.demo.newfeature.management.DemoManagement;
import com.demo.newfeature.web.helper.AuthInfoStore;
import com.demo.newfeature.web.vo.AddOneReq;
import com.demo.newfeature.web.vo.OneVO;
import com.demo.newfeature.web.vo.RecList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RequestMapping("/demo")
@RestController
public class DemoController {


    private final DemoManagement management;

    private final IdEncodeTool idEncodeTool;

    private final AuthInfoStore store;


    public DemoController(DemoManagement management, IdEncodeTool idEncodeTool, AuthInfoStore store) {
        this.management = management;
        this.idEncodeTool = idEncodeTool;
        this.store = store;
    }

    @PostMapping("/addEntry")
    public OneVO addEntry(@RequestBody AddOneReq req) {

        var rec = new OneRec(req, store.getUserInfo());
        var result = management.addOneEntity(rec);

        return generVO(result);

    }

    @GetMapping("/{id}")
    public OneVO getById(@PathVariable("id") String idString) {

        var id = idEncodeTool.decode(idString);
        var result = management.getOneById(id);

        return switch (result) {
            case Optional2.Some<OneRec> some -> generVO(some.get());
            case Optional2.None<OneRec> _ -> throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        };

    }

    private OneVO generVO(OneRec entity) {
        return RecordUtils.copy(entity, OneVO.class);
    }

    @PutMapping("/{id}")
    public OneVO update(@PathVariable("id") String idStr, @RequestBody Map<String, Object> params) {

        Integer id = idEncodeTool.decode(idStr);

        var result = management.updateOne(id, params);
        return switch (result) {
            case Optional2.Some<OneRec> some -> generVO(some.get());
            case Optional2.None<OneRec> _ -> throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        };


    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable("id") String idStr) {

        Integer id = idEncodeTool.decode(idStr);

        var result = management.deleteOne(id);
        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return HttpStatus.OK;

    }

    @PutMapping("/{id}/status/{status}")
    public HttpStatus updateStatus(@PathVariable("id") String idStr, @PathVariable("status") StatusType status) {

        Integer id = idEncodeTool.decode(idStr);
        var result = management.updateStatus(id, status);
        if (result == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return HttpStatus.OK;
    }

    @GetMapping("/search/name/{name}")
    public RecList searchByName(@PathVariable("name") String name, @RequestParam(required = false) String token) {

        var recList = management.queryByNameWild(name, idEncodeTool.decodePageToken(token));

        int pageSize = 50;
        return new RecList(recList, pageSize, idEncodeTool);
    }

}
