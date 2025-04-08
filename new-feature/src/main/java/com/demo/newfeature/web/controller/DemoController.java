package com.demo.newfeature.web.controller;

import com.demo.newfeature.helper.IdEncodeTool;
import com.demo.newfeature.helper.RecordUtils;
import com.demo.newfeature.management.DemoManagement;
import com.demo.newfeature.web.helper.AuthInfoStore;
import com.demo.newfeature.web.vo.AddOneReq;
import com.demo.newfeature.web.vo.OneVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        var rec = req.generRec(store.getUserInfo());
        var result = management.addOneEntity(rec);
        Map<String, Object> additions = Map.of("entityId", idEncodeTool.encode(result.id()));
        return RecordUtils.copy(rec, OneVO.class, additions);

    }

}
