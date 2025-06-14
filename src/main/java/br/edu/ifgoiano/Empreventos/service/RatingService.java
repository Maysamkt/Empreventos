package br.edu.ifgoiano.Empreventos.service;

import br.edu.ifgoiano.Empreventos.dto.AvaliacaoDTO;
import br.edu.ifgoiano.Empreventos.mapper.DataMapper;
import br.edu.ifgoiano.Empreventos.model.Avaliacao;
import br.edu.ifgoiano.Empreventos.repository.AvaliacaoRepository;
import br.edu.ifgoiano.Empreventos.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private EventRepository eventRepository;

    public AvaliacaoDTO create(Long eventoId, AvaliacaoDTO avaliacaoDTO) {
        var evento = eventRepository.findById(eventoId)
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado com ID: " + eventoId));
        var avaliacao = DataMapper.parseObject(avaliacaoDTO, Avaliacao.class);
        avaliacao.setEvento(evento);
        // Quando o usuário estiver implementado:
        // avaliacao.setParticipante(participanteLogado);

        var savedAvaliacao = avaliacaoRepository.save(avaliacao);
        return DataMapper.parseObject(savedAvaliacao, AvaliacaoDTO.class);
    }

    public List<AvaliacaoDTO> findByEventoId(Long eventoId) {
        var avaliacoes = avaliacaoRepository.findByEventoId(eventoId);
        return DataMapper.parseListObjects(avaliacoes, AvaliacaoDTO.class);
    }
}