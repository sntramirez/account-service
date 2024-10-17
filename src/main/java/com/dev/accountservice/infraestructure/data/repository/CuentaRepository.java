package com.dev.accountservice.infraestructure.data.repository;

import com.dev.accountservice.domain.core.ports.CuentaPort;
import com.dev.accountservice.infraestructure.data.entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(path = "cuentas-api", collectionResourceRel = "cuentas")
public interface CuentaRepository extends JpaRepository<Cuenta, Long>, CuentaPort {


    @Override
    default Cuenta crearCuenta(Cuenta cuenta){
        return this.save(cuenta);
    }

    @Override
    @RestResource(exported = false)
    Optional<Cuenta> findByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);

    @Query(value = "SELECT nextval('cuenta_numero_cuenta_seq')", nativeQuery = true)
    Long getNextNumeroCuentaSequenceValue();
}
