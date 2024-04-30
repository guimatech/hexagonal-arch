package io.github.guimatech.hexagonal.controllers;

import io.github.guimatech.hexagonal.dtos.PartnerDTO;
import io.github.guimatech.hexagonal.models.Partner;
import io.github.guimatech.hexagonal.services.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "partners")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PartnerDTO dto) {
        if (partnerService.findByCnpj(dto.getCnpj()).isPresent()) {
            return ResponseEntity.unprocessableEntity().body("Partner already exists");
        }
        if (partnerService.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.unprocessableEntity().body("Partner already exists");
        }

        var partner = new Partner();
        partner.setName(dto.getName());
        partner.setCnpj(dto.getCnpj());
        partner.setEmail(dto.getEmail());

        partner = partnerService.save(partner);

        return ResponseEntity.created(URI.create("/partners/" + partner.getId())).body(partner);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        var partner = partnerService.findById(id);
        if (partner.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(partner.get());
    }

}
