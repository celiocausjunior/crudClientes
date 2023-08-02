package celiocausjunior.clientesdesafio3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import celiocausjunior.clientesdesafio3.models.ClientModel;

public interface ClientRepository extends JpaRepository<ClientModel, Long>{
    
}
