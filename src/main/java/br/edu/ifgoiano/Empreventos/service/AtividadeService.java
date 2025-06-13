package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.dto.AtividadeDTO;
import br.edu.ifgoiano.Empreventos.dto.AtividadeResponseDTO;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.Atividade;
import br.edu.ifgoiano.Empreventos.repository.AtividadeRepository;
import br.edu.ifgoiano.Empreventos.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private EventRepository eventRepository;


    public AtividadeDTO create(Long eventoId, AtividadeDTO atividadeDTO) {
        var evento = eventRepository.findById(eventoId)
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado com o ID: " + eventoId));
        var atividade = DataMapper.parseObject(atividadeDTO, Atividade.class);
        atividade.setEvento(evento);
        var savedAtividade = atividadeRepository.save(atividade);
        return DataMapper.parseObject(savedAtividade, AtividadeDTO.class);
    }

    public AtividadeResponseDTO findById(Long atividadeId) {
        var atividade = atividadeRepository.findById(atividadeId)
                .orElseThrow(() -> new NoSuchElementException("Atividade não encontrada com o ID: " + atividadeId));
        return DataMapper.parseObject(atividade, AtividadeResponseDTO.class);
    }

    public List<AtividadeDTO> findByEventoId(Long eventoId) {
        var atividades = atividadeRepository.findByEventoId(eventoId);
        return DataMapper.parseListObjects(atividades, AtividadeDTO.class);
    }

}