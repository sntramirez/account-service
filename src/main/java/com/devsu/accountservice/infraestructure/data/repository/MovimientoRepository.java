package com.devsu.accountservice.infraestructure.data.repository;

import com.devsu.accountservice.infraestructure.data.entities.Cuenta;
import com.devsu.accountservice.infraestructure.data.entities.Movimiento;
import com.devsu.accountservice.infraestructure.data.entities.QMovimiento;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource(path = "movimientos", collectionResourceRel = "movimientos")
public interface MovimientoRepository extends JpaRepository<Movimiento, Long>,
        QuerydslPredicateExecutor<Movimiento>, QuerydslBinderCustomizer<QMovimiento> {

    @Override
    default void customize(
            QuerydslBindings bindings, QMovimiento root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    @Query("select a from Movimiento a where a.cuenta.id= :cuentaId   order by a.fecha desc ")
    List<Movimiento> findByCuenta(Long cuentaId);
}
