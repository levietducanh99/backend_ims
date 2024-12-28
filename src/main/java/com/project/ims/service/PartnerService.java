package com.project.ims.service;

import com.project.ims.model.dto.PartnerDTO;
import com.project.ims.model.dto.PartnerDTOForShow;
import com.project.ims.model.entity.Partner;

import java.util.List;
import java.util.Optional;

public interface PartnerService {
    List<PartnerDTOForShow> findAllDTO();

    boolean addPartner(Partner partner);

    List<PartnerDTOForShow> findByNameContainingDTO(String name);

    List<PartnerDTO> findAllSimpleDTO();

    Optional<Partner> findByIdPartner(int partnerID);

	void deletePartner(int partnerID);

	Optional<Partner> findById(int partnerId);
}
