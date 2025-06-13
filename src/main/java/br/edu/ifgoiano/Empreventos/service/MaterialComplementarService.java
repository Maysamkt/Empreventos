package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.dto.MaterialComplementarDTO;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.MaterialComplementar;
import br.edu.ifgoiano.Empreventos.repository.AtividadeRepository;
import br.edu.ifgoiano.Empreventos.repository.MaterialComplementarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MaterialComplementarService {

    @Autowired
    private MaterialComplementarRepository materialRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    public MaterialComplementarDTO create(Long atividadeId, MaterialComplementarDTO materialDTO) {
        var atividade = atividadeRepository.findById(atividadeId)
                .orElseThrow(() -> new NoSuchElementException("Atividade não encontrada com o ID: " + atividadeId));
        var material = DataMapper.parseObject(materialDTO, MaterialComplementar.class);
        material.setAtividade(atividade);
        var savedMaterial = materialRepository.save(material);
        return DataMapper.parseObject(savedMaterial, MaterialComplementarDTO.class);
    }

    public List<MaterialComplementarDTO> findByAtividadeId(Long atividadeId) {
        var materiais = materialRepository.findByAtividadeId(atividadeId);
        return DataMapper.parseListObjects(materiais, MaterialComplementarDTO.class);
    }

    public void delete(Long materialId) {
        var material = materialRepository.findById(materialId)
                .orElseThrow(() -> new NoSuchElementException("Material não encontrado com o ID: " + materialId));
        materialRepository.delete(material);
    }
}