package com.project.ims.controller;

import com.project.ims.model.dto.PartnerDTO;
import com.project.ims.model.dto.PartnerDTOForCreate;
import com.project.ims.model.dto.PartnerDTOForShow;
import com.project.ims.model.entity.Partner;
import com.project.ims.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @GetMapping("/get-all")
    public List<PartnerDTOForShow> getAllPartners() {
        return partnerService.findAllDTO();
    }

    @GetMapping("/search")
    public List<PartnerDTOForShow> searchPartnersByName(@RequestParam String name) {
        return partnerService.findByNameContainingDTO(name);
    }

    @GetMapping("/search/{partnerId}")
    public Optional<Partner> searchPartnerById(@PathVariable int partnerId) {
        return partnerService.findById(partnerId);
    }

    // Phương thức để thêm đối tác
    @PostMapping
    public ResponseEntity<String> addPartner(@RequestBody PartnerDTOForCreate partnerDTO) {
        // Chuyển đổi PartnerDTO thành đối tượng Partner
        Partner newPartner = new Partner();
        newPartner.setName(partnerDTO.getName());
        newPartner.setContactNumber(partnerDTO.getContactNumber());
        newPartner.setAddress(partnerDTO.getAddress());

        // Gọi Service để lưu đối tác mới vào cơ sở dữ liệu
        boolean isAdded = partnerService.addPartner(newPartner);

        if (isAdded) {
            return ResponseEntity.ok("Đối tác đã được thêm thành công");
        } else {
            return ResponseEntity.status(400).body("Lỗi khi thêm đối tác");
        }
    }

    @GetMapping()
    public List<PartnerDTO> getPartners() {
        return partnerService.findAllSimpleDTO();
    }

    @DeleteMapping("/{partnerId}")
    public ResponseEntity<?> deletePartner(@PathVariable int partnerId) {
        try {
            partnerService.deletePartner(partnerId);
            return ResponseEntity.ok(Map.of("message", "Đối tác đã được xóa thành công."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Partner> updatePartner(@PathVariable int id, @RequestBody Partner partnerDetails) {
        Partner updatedPartner = partnerService.updatePartner(id, partnerDetails);
        return ResponseEntity.ok(updatedPartner);
    }
}
