package com.project.ims.service.impl;

import com.project.ims.model.dto.PartnerDTO;
import com.project.ims.model.dto.PartnerDTOForShow;
import com.project.ims.model.entity.Partner;
import com.project.ims.repository.PartnerRepository;
import com.project.ims.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public List<PartnerDTOForShow> findAllDTO() {
        return partnerRepository.findAll().stream()
                .map(PartnerDTOForShow::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean addPartner(Partner partner) {
        try {
            partnerRepository.save(partner); // Lưu đối tác vào cơ sở dữ liệu
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<PartnerDTOForShow> findByNameContainingDTO(String name) {
        return partnerRepository.findByNameContaining(name).stream()
                .map(PartnerDTOForShow::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<PartnerDTO> findAllSimpleDTO() {
        return partnerRepository.findAll().stream()
                .map(partner -> {
                    PartnerDTO dto = new PartnerDTO();
                    dto.setPartnerID(partner.getPartnerID());
                    dto.setName(partner.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Partner> findByIdPartner(int partnerID) {
        return partnerRepository.findById(partnerID);
    }
    @Override
    public void deletePartner(int partnerID) {
        Optional<Partner> partner = partnerRepository.findById(partnerID);

        if (partner.isPresent()) {
            partnerRepository.delete(partner.get());
        } else {
            throw new RuntimeException("Partner not found with ID: " + partnerID);
        }
    }

}
