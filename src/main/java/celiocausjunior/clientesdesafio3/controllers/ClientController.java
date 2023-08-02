package celiocausjunior.clientesdesafio3.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import celiocausjunior.clientesdesafio3.models.dtos.ClientModelDTO;
import celiocausjunior.clientesdesafio3.services.ClientService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clients")
public class ClientController {

     @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientModelDTO>> findAll(Pageable pageable) {
        Page<ClientModelDTO> dto = clientService.findAll(pageable);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientModelDTO> findById(@PathVariable Long id) {
        ClientModelDTO dto = clientService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ClientModelDTO> insert(@Valid @RequestBody ClientModelDTO clientDTO) {
        clientDTO = clientService.insert(clientDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clientDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(clientDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientModelDTO> update(@PathVariable Long id, @Valid @RequestBody ClientModelDTO clientDTO) {
        ClientModelDTO dto = clientService.update(id, clientDTO);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
