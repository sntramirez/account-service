package com.dev.accountservice.infraestructure.data.repository;

import com.dev.accountservice.domain.core.ports.MovimientoPort;
import com.dev.accountservice.infraestructure.data.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "movimientos-api", collectionResourceRel = "movimientos")
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> , MovimientoPort {


    @Override
    default Movimiento crearMovimento(Movimiento movimiento) {
        return save(movimiento);  // Guardar usando JPA
    }



    @RestResource(exported = false)
    @Query("select sum(a.valor) from Movimiento a where a.cuenta.id= :cuentaId and a.fecha = CURRENT_DATE   order by a.id desc ")
    List<Movimiento> findByCuenta(@Param("cuentaId") Long cuentaId);

    @Override
    @RestResource(exported = false)
    Optional<Movimiento> findByIdempotenciaClave(String idempotenciaClave);


    @RestResource(rel = "search")
    @Query(" select a from Movimiento a " +
            " where a.cuenta.id= :cuentaId " +
            "  and a.fecha>= :createDateFrom " +
            "  and a.fecha<= :createDateTo    " +
            "  order by a.fecha desc ")
    List<Movimiento> findByCuentaFecha(@Param("cuentaId") Long cuentaId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createDateFrom,
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createDateTo);



    @RestResource(rel = "search", path = "getKeyMovimiento")
    @Query(value = "SELECT nextval('movimiento_idempotencia_clave_seq')", nativeQuery = true)
    Long getMovimientoIdempotenciaClaveSequenceValue();

}
