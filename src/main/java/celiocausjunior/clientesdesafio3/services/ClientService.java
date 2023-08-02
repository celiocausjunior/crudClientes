package celiocausjunior.clientesdesafio3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import celiocausjunior.clientesdesafio3.models.ClientModel;
import celiocausjunior.clientesdesafio3.models.dtos.ClientModelDTO;
import celiocausjunior.clientesdesafio3.repositories.ClientRepository;
import celiocausjunior.clientesdesafio3.services.exceptions.DataIntegrityException;
import celiocausjunior.clientesdesafio3.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public ClientModelDTO findById(Long id) {
        return clientRepository.findById(id).map(client -> new ClientModelDTO(client))
                .orElseThrow(() -> new ResourceNotFoundException("Id não encontrado"));
    }

    @Transactional(readOnly = true)
    public Page<ClientModelDTO> findAll(Pageable pageable) {
        Page<ClientModelDTO> page = clientRepository.findAll(pageable).map(client -> new ClientModelDTO(client));
        return page;
    }

    @Transactional
    public ClientModelDTO insert(ClientModelDTO clientDTO) {
        ClientModel client = new ClientModel();
        copyDtoToEntity(clientDTO, client);
        client = clientRepository.save(client);
        return new ClientModelDTO(client);
    }

    @Transactional
    public ClientModelDTO update(Long id, ClientModelDTO clientDTO) {

        try {
            ClientModel client = clientRepository.getReferenceById(id);
            copyDtoToEntity(clientDTO, client);
            client = clientRepository.save(client);
            return new ClientModelDTO(client);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id não encontrado: " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Id não encontrado");
        }
        try {
            clientRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(ClientModelDTO clientDto, ClientModel client) {
        client.setName(clientDto.getName());
        client.setCpf(clientDto.getCpf());
        client.setIncome(clientDto.getIncome());
        client.setBirthDate(clientDto.getBirthDate());
        client.setChildren(clientDto.getChildren());
    }

}
