package com.fernandospizzca.ContatosJavaWebMVC.Repositorios;

import com.fernandospizzca.ContatosJavaWebMVC.Entidades.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactRepository extends JpaRepository<Contato, Long> {
    List<Contato> findByNomeContainingIgnoreCase(String nome);

}