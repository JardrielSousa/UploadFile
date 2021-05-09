package br.com.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.model.Doc;

public interface UploadFileRepository extends JpaRepository<Doc, Integer>{

}
