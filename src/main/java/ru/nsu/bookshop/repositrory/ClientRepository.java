package ru.nsu.bookshop.repositrory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.nsu.bookshop.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {}
