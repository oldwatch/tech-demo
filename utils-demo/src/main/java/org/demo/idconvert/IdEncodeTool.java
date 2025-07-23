package org.demo.idconvert;

import org.demo.utils.DatetimeUtils;
import org.demo.utils.Optional2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sqids.Sqids;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;


public class IdEncodeTool {

    private final static Logger log = LoggerFactory.getLogger(IdEncodeTool.class);

    private final Sqids sqIds;
    private final Random random;

    public IdEncodeTool(String sqIdMask) {
        sqIds = Sqids.builder()
                .minLength(8)
                .alphabet(sqIdMask)
                .build();
        random = Random.from(RandomGenerator.getDefault());
    }

    public String encode(Integer id) {
        log.info(" entity id:{}", id);
        return sqIds.encode(List.of(id.longValue()));
    }

    public Integer decode(String id) {
        List<Long> result = sqIds.decode(id);

        if (result.isEmpty()) {
            return -1;
        }
        return result.getFirst().intValue();
    }


    public String encodePager(Pager pager) {

        return sqIds.encode(List.of(
                pager.limit().longValue(),
                DatetimeUtils.getTimestamp(pager.lastLocal())
        ));
    }

    public Optional2<Pager> decodePageToken(String token) {
        if (token == null) {
            return Optional2.empty();
        }
        List<Long> result = sqIds.decode(token);

        if (result.isEmpty()) {
            return Optional2.empty();
        }
        var date = DatetimeUtils.getLocalTime(result.get(1));
        var page = new Pager(result.get(0).intValue(), date);
        return Optional2.of(page);
    }
}
