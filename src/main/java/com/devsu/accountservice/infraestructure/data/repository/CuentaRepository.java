package com.devsu.accountservice.infraestructure.data.repository;

import com.devsu.accountservice.infraestructure.data.entities.Cuenta;
import com.devsu.accountservice.infraestructure.data.entities.QCuenta;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "cuentas", collectionResourceRel = "cuentas")
public interface CuentaRepository extends JpaRepository<Cuenta, Long>,
        QuerydslPredicateExecutor<Cuenta>, QuerydslBinderCustomizer<QCuenta> {

    @Override
    default void customize(
            QuerydslBindings bindings, QCuenta root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

    @RestResource(rel = "search")
    Cuenta findByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);
}
