package com.win.guid.crud.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.win.guid.crud.service.repositories.GuidMasterRepository;
import java.util.UUID;

@Service
public class GuidRandomGeneratorService {

    @Autowired
    GuidMasterRepository mGuidMasterRepository;

    public String GuidRandomGeneratorV4() {

        UUID uuid = null;
        String uuidAsString = null;

        // generate a guid, if it exists in table try 10 times to get unique one
        for (int i = 1; i <= 10; i++) {
            uuid = UUID.randomUUID();
            if (uuid != null) {
                uuidAsString = uuid.toString();
                if (mGuidMasterRepository.existsGuidMasterByGuid(uuidAsString)) {
                    uuidAsString = null;
                } else {
                    break;
                }
            }
        }

        return uuidAsString;

    }
}
