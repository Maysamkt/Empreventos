package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.dto.ComplementaryMaterialDTO;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.ComplementaryMaterial;
import br.edu.ifgoiano.Empreventos.repository.ActivityRepository;
import br.edu.ifgoiano.Empreventos.repository.ComplementaryMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ComplementaryMaterialService {

    @Autowired
    private ComplementaryMaterialRepository complementaryMaterialRepository;

    @Autowired
    private ActivityRepository activityRepository;


    public ComplementaryMaterialDTO create(ComplementaryMaterialDTO materialDTO) {
        var activity = activityRepository.findById(materialDTO.getActivityId())
                .orElseThrow(() -> new NoSuchElementException("Activity not found with ID: " + materialDTO.getActivityId()));
        var material = DataMapper.parseObject(materialDTO, ComplementaryMaterial.class);
        material.setActivity(activity);
        var savedMaterial = complementaryMaterialRepository.save(material);
        return DataMapper.parseObject(savedMaterial, ComplementaryMaterialDTO.class);
    }


    public List<ComplementaryMaterialDTO> findByAtividadeId(Integer ActivityId) {
        var materiais = complementaryMaterialRepository.findByActivityId(ActivityId);
        return DataMapper.parseListObjects(materiais, ComplementaryMaterialDTO.class);
    }


    public void delete(Integer materialId) {
        var material = complementaryMaterialRepository.findById(materialId)
                .orElseThrow(() -> new NoSuchElementException("Material não encontrado com o ID: " + materialId));
        material.setDeletedAt(LocalDateTime.now());
        complementaryMaterialRepository.save(material);
    }


    public ComplementaryMaterialDTO update(Integer materialId, ComplementaryMaterialDTO materialDTO) {
        var material = complementaryMaterialRepository.findById(materialId)
                .orElseThrow(() -> new NoSuchElementException("Material não encontrado com o ID: " + materialId));
        material.setTitle(materialDTO.getTitle());
        material.setDescription(materialDTO.getDescription());
        material.setUrl(materialDTO.getUrl());
        material.setType(materialDTO.getMaterialType());
        var updatedMaterial = complementaryMaterialRepository.save(material);
        return DataMapper.parseObject(updatedMaterial, ComplementaryMaterialDTO.class);
    }
}